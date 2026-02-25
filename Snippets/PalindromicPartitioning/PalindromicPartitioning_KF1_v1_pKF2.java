package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for palindrome partitioning.
 *
 * Computes the minimum number of cuts needed to partition a string such that
 * every substring is a palindrome.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
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

        // minCuts[i] = minimum cuts needed for substring s[0..i]
        int[] minCuts = new int[n];

        // isPalindrome[i][j] = true if substring s[i..j] is a palindrome
        boolean[][] isPalindrome = new boolean[n][n];

        // Every single character is a palindrome
        for (int i = 0; i < n; i++) {
            isPalindrome[i][i] = true;
        }

        // Fill palindrome table for all substring lengths >= 2
        for (int length = 2; length <= n; length++) {
            for (int start = 0; start <= n - length; start++) {
                int end = start + length - 1;

                if (length == 2) {
                    isPalindrome[start][end] = (s.charAt(start) == s.charAt(end));
                } else {
                    isPalindrome[start][end] =
                        (s.charAt(start) == s.charAt(end)) && isPalindrome[start + 1][end - 1];
                }
            }
        }

        // Compute minimum cuts using the palindrome table
        for (int i = 0; i < n; i++) {
            if (isPalindrome[0][i]) {
                minCuts[i] = 0;
            } else {
                minCuts[i] = Integer.MAX_VALUE;
                for (int j = 0; j < i; j++) {
                    if (isPalindrome[j + 1][i] && 1 + minCuts[j] < minCuts[i]) {
                        minCuts[i] = 1 + minCuts[j];
                    }
                }
            }
        }

        return minCuts[n - 1];
    }
}