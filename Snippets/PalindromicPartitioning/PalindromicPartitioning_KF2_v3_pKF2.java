package com.thealgorithms.dynamicprogramming;

public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the minimum number of cuts needed to partition the given string
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

        // minCuts[i] = minimum cuts needed for substring word[0..i]
        int[] minCuts = new int[n];

        // isPalindrome[i][j] = true if substring word[i..j] is a palindrome
        boolean[][] isPalindrome = new boolean[n][n];

        fillPalindromeTable(word, isPalindrome);
        computeMinCuts(isPalindrome, minCuts);

        return minCuts[n - 1];
    }

    /**
     * Precomputes which substrings of {@code word} are palindromes.
     * After this method:
     *   isPalindrome[i][j] is true iff word.substring(i, j + 1) is a palindrome.
     *
     * @param word         the input string
     * @param isPalindrome table to be filled with palindrome information
     */
    private static void fillPalindromeTable(String word, boolean[][] isPalindrome) {
        int n = word.length();

        // Every single character is a palindrome
        for (int i = 0; i < n; i++) {
            isPalindrome[i][i] = true;
        }

        // Check palindromes of length >= 2
        for (int length = 2; length <= n; length++) {
            for (int start = 0; start <= n - length; start++) {
                int end = start + length - 1;

                if (word.charAt(start) != word.charAt(end)) {
                    isPalindrome[start][end] = false;
                } else if (length == 2) {
                    // Two equal characters form a palindrome
                    isPalindrome[start][end] = true;
                } else {
                    // For length > 2, rely on the inner substring
                    isPalindrome[start][end] = isPalindrome[start + 1][end - 1];
                }
            }
        }
    }

    /**
     * Computes the minimum number of cuts needed for each prefix of the string,
     * using the precomputed palindrome table.
     *
     * After this method:
     *   minCuts[i] = minimum cuts needed for word[0..i].
     *
     * @param isPalindrome precomputed palindrome table
     * @param minCuts      array to be filled with minimum cut counts
     */
    private static void computeMinCuts(boolean[][] isPalindrome, int[] minCuts) {
        int n = minCuts.length;

        for (int end = 0; end < n; end++) {
            // If the whole prefix word[0..end] is a palindrome, no cuts are needed
            if (isPalindrome[0][end]) {
                minCuts[end] = 0;
                continue;
            }

            int best = Integer.MAX_VALUE;

            // Try all possible cut positions before 'end'
            for (int cut = 0; cut < end; cut++) {
                // If word[cut + 1..end] is a palindrome, update best
                if (isPalindrome[cut + 1][end]) {
                    best = Math.min(best, 1 + minCuts[cut]);
                }
            }

            minCuts[end] = best;
        }
    }
}