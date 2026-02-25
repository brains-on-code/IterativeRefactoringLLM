package com.thealgorithms.dynamicprogramming;

public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the minimum number of cuts needed to partition {@code word}
     * into substrings that are all palindromes.
     *
     * Example:
     *   word = "aab" -> "aa" | "b" -> 1 cut
     *
     * @param word the input string
     * @return the minimum number of cuts required
     */
    public static int minimalPartitions(String word) {
        int n = word.length();
        if (n == 0) {
            return 0;
        }

        int[] minCuts = new int[n];
        boolean[][] isPalindrome = new boolean[n][n];

        fillPalindromeTable(word, isPalindrome);
        computeMinCuts(isPalindrome, minCuts);

        return minCuts[n - 1];
    }

    /**
     * Populates {@code isPalindrome} such that:
     * {@code isPalindrome[i][j]} is {@code true} iff
     * {@code word.substring(i, j + 1)} is a palindrome.
     */
    private static void fillPalindromeTable(String word, boolean[][] isPalindrome) {
        int n = word.length();

        // All single-character substrings are palindromes
        for (int i = 0; i < n; i++) {
            isPalindrome[i][i] = true;
        }

        // Check substrings of length >= 2
        for (int length = 2; length <= n; length++) {
            for (int start = 0; start <= n - length; start++) {
                int end = start + length - 1;

                if (word.charAt(start) != word.charAt(end)) {
                    isPalindrome[start][end] = false;
                } else if (length == 2) {
                    isPalindrome[start][end] = true;
                } else {
                    isPalindrome[start][end] = isPalindrome[start + 1][end - 1];
                }
            }
        }
    }

    /**
     * Populates {@code minCuts} such that:
     * {@code minCuts[i]} is the minimum number of cuts needed
     * for the prefix {@code word[0..i]}.
     */
    private static void computeMinCuts(boolean[][] isPalindrome, int[] minCuts) {
        int n = minCuts.length;

        for (int end = 0; end < n; end++) {
            // If the whole prefix [0..end] is a palindrome, no cuts are needed
            if (isPalindrome[0][end]) {
                minCuts[end] = 0;
                continue;
            }

            int best = Integer.MAX_VALUE;

            // Try all possible cut positions before 'end'
            for (int cut = 0; cut < end; cut++) {
                if (isPalindrome[cut + 1][end]) {
                    best = Math.min(best, 1 + minCuts[cut]);
                }
            }

            minCuts[end] = best;
        }
    }
}