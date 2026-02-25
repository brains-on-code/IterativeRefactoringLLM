package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for palindrome partitioning operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Computes the minimum number of cuts needed to partition the given string
     * into substrings that are all palindromes.
     *
     * @param input the string to partition
     * @return the minimum number of cuts required
     */
    public static int method1(String input) {
        int length = input.length();
        int[] minCuts = new int[length];
        boolean[][] isPalindrome = new boolean[length][length];

        // Every single character is a palindrome
        for (int i = 0; i < length; i++) {
            isPalindrome[i][i] = true;
        }

        // Fill palindrome table for all substring lengths
        for (int subLen = 2; subLen <= length; subLen++) {
            for (int start = 0; start <= length - subLen; start++) {
                int end = start + subLen - 1;

                if (subLen == 2) {
                    isPalindrome[start][end] = (input.charAt(start) == input.charAt(end));
                } else {
                    isPalindrome[start][end] =
                        (input.charAt(start) == input.charAt(end)) && isPalindrome[start + 1][end - 1];
                }
            }
        }

        // Compute minimum cuts using the palindrome table
        for (int end = 0; end < length; end++) {
            if (isPalindrome[0][end]) {
                minCuts[end] = 0;
            } else {
                int currentMin = Integer.MAX_VALUE;
                for (int start = 0; start < end; start++) {
                    if (isPalindrome[start + 1][end] && minCuts[start] + 1 < currentMin) {
                        currentMin = minCuts[start] + 1;
                    }
                }
                minCuts[end] = currentMin;
            }
        }

        return minCuts[length - 1];
    }
}