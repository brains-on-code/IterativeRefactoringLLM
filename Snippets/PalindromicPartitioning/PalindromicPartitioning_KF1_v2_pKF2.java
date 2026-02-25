package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for palindrome partitioning.
 *
 * Computes the minimum number of cuts needed to partition a string such that
 * every substring is a palindrome.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Returns the minimum number of cuts needed to partition the given string
     * into palindromic substrings.
     *
     * Example:
     *   "aab" -> 1 ("aa" | "b")
     *
     * @param s the input string
     * @return minimum number of cuts for palindromic partitioning
     */
    public static int method1(String s) {
        int n = s.length();

        // minCuts[i] = minimum cuts needed for s[0..i]
        int[] minCuts = new int[n];

        // isPalindrome[i][j] = true if s[i..j] is a palindrome
        boolean[][] isPalindrome = new boolean[n][n];

        // Initialize single-character palindromes
        for (int i = 0; i < n; i++) {
            isPalindrome[i][i] = true;
        }

        // Precompute palindromes for all substring lengths >= 2
        for (int length = 2; length <= n; length++) {
            for (int start = 0; start <= n - length; start++) {
                int end = start + length - 1;
                char startChar = s.charAt(start);
                char endChar = s.charAt(end);

                if (length == 2) {
                    isPalindrome[start][end] = (startChar == endChar);
                } else {
                    isPalindrome[start][end] =
                        (startChar == endChar) && isPalindrome[start + 1][end - 1];
                }
            }
        }

        // Compute minimum cuts using the precomputed palindrome table
        for (int end = 0; end < n; end++) {
            if (isPalindrome[0][end]) {
                minCuts[end] = 0;
                continue;
            }

            int min = Integer.MAX_VALUE;
            for (int cut = 0; cut < end; cut++) {
                if (isPalindrome[cut + 1][end] && minCuts[cut] + 1 < min) {
                    min = minCuts[cut] + 1;
                }
            }
            minCuts[end] = min;
        }

        return minCuts[n - 1];
    }
}