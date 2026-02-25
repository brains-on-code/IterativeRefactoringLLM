package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for palindrome partitioning operations.
 */
public final class PalindromePartitioning {

    private PalindromePartitioning() {
        // Prevent instantiation
    }

    /**
     * Computes the minimum number of cuts needed to partition the input string
     * such that every substring is a palindrome.
     *
     * @param input the string to partition
     * @return the minimum number of cuts required
     */
    public static int minPalindromeCuts(String input) {
        int n = input.length();

        int[] minCuts = new int[n];
        boolean[][] isPalindrome = new boolean[n][n];

        // Every single character is a palindrome
        for (int i = 0; i < n; i++) {
            isPalindrome[i][i] = true;
        }

        // Fill palindrome table for substrings of length >= 2
        for (int currentLength = 2; currentLength <= n; currentLength++) {
            for (int start = 0; start <= n - currentLength; start++) {
                int end = start + currentLength - 1;

                if (currentLength == 2) {
                    isPalindrome[start][end] = input.charAt(start) == input.charAt(end);
                } else {
                    isPalindrome[start][end] =
                        input.charAt(start) == input.charAt(end)
                            && isPalindrome[start + 1][end - 1];
                }
            }
        }

        // Compute minimum cuts using the palindrome table
        for (int end = 0; end < n; end++) {
            if (isPalindrome[0][end]) {
                minCuts[end] = 0;
            } else {
                int bestCutCount = Integer.MAX_VALUE;
                for (int partitionEnd = 0; partitionEnd < end; partitionEnd++) {
                    if (isPalindrome[partitionEnd + 1][end]
                        && minCuts[partitionEnd] + 1 < bestCutCount) {
                        bestCutCount = minCuts[partitionEnd] + 1;
                    }
                }
                minCuts[end] = bestCutCount;
            }
        }

        return minCuts[n - 1];
    }
}