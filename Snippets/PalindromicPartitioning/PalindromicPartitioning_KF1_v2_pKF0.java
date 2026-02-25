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
     * @throws IllegalArgumentException if input is null
     */
    public static int method1(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string must not be null");
        }

        int length = input.length();
        if (length <= 1) {
            return 0;
        }

        boolean[][] isPalindrome = buildPalindromeTable(input);
        int[] minCuts = computeMinCuts(isPalindrome);

        return minCuts[length - 1];
    }

    /**
     * Builds a table where isPalindrome[i][j] is true if the substring
     * input[i..j] (inclusive) is a palindrome.
     */
    private static boolean[][] buildPalindromeTable(String input) {
        int length = input.length();
        boolean[][] isPalindrome = new boolean[length][length];

        // Single characters are palindromes
        for (int i = 0; i < length; i++) {
            isPalindrome[i][i] = true;
        }

        // Substrings of length >= 2
        for (int subLen = 2; subLen <= length; subLen++) {
            for (int start = 0; start <= length - subLen; start++) {
                int end = start + subLen - 1;
                char startChar = input.charAt(start);
                char endChar = input.charAt(end);

                if (startChar != endChar) {
                    isPalindrome[start][end] = false;
                } else if (subLen == 2) {
                    isPalindrome[start][end] = true;
                } else {
                    isPalindrome[start][end] = isPalindrome[start + 1][end - 1];
                }
            }
        }

        return isPalindrome;
    }

    /**
     * Computes the minimum cuts needed for each prefix of the string,
     * using the precomputed palindrome table.
     */
    private static int[] computeMinCuts(boolean[][] isPalindrome) {
        int length = isPalindrome.length;
        int[] minCuts = new int[length];

        for (int end = 0; end < length; end++) {
            if (isPalindrome[0][end]) {
                minCuts[end] = 0;
                continue;
            }

            int currentMin = Integer.MAX_VALUE;
            for (int start = 0; start < end; start++) {
                if (isPalindrome[start + 1][end]) {
                    currentMin = Math.min(currentMin, minCuts[start] + 1);
                }
            }
            minCuts[end] = currentMin;
        }

        return minCuts;
    }
}