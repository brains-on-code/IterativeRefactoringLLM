package com.thealgorithms.dynamicprogramming;

public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
        // Prevent instantiation
    }

    /**
     * Computes the minimum number of cuts needed to partition the given string
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
     * Fills {@code isPalindrome} so that:
     *   isPalindrome[i][j] == true  iff  word.substring(i, j + 1) is a palindrome.
     */
    private static void fillPalindromeTable(String word, boolean[][] isPalindrome) {
        int n = word.length();

        // Single-character substrings
        for (int i = 0; i < n; i++) {
            isPalindrome[i][i] = true;
        }

        // Substrings of length >= 2
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
     * Fills {@code minCuts} so that:
     *   minCuts[i] = minimum cuts needed for the prefix word[0..i].
     */
    private static void computeMinCuts(boolean[][] isPalindrome, int[] minCuts) {
        int n = minCuts.length;

        for (int end = 0; end < n; end++) {
            if (isPalindrome[0][end]) {
                minCuts[end] = 0;
                continue;
            }

            int best = Integer.MAX_VALUE;

            for (int cut = 0; cut < end; cut++) {
                if (isPalindrome[cut + 1][end]) {
                    best = Math.min(best, 1 + minCuts[cut]);
                }
            }

            minCuts[end] = best;
        }
    }
}