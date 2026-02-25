package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for palindrome partitioning operations.
 */
public final class PalindromePartitioner {

    private PalindromePartitioner() {
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
    public static int minCutPalindromePartition(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string must not be null");
        }

        int length = input.length();
        if (length <= 1) {
            return 0;
        }

        boolean[][] palindromeTable = buildPalindromeTable(input);
        int[] minCuts = computeMinCuts(palindromeTable);

        return minCuts[length - 1];
    }

    /**
     * Builds a table where palindromeTable[i][j] is true if the substring
     * input[i..j] (inclusive) is a palindrome.
     */
    private static boolean[][] buildPalindromeTable(String input) {
        int length = input.length();
        boolean[][] palindromeTable = new boolean[length][length];

        // Single characters are palindromes
        for (int i = 0; i < length; i++) {
            palindromeTable[i][i] = true;
        }

        // Substrings of length >= 2
        for (int subLen = 2; subLen <= length; subLen++) {
            for (int start = 0; start <= length - subLen; start++) {
                int end = start + subLen - 1;
                char startChar = input.charAt(start);
                char endChar = input.charAt(end);

                if (startChar != endChar) {
                    palindromeTable[start][end] = false;
                } else if (subLen == 2) {
                    palindromeTable[start][end] = true;
                } else {
                    palindromeTable[start][end] = palindromeTable[start + 1][end - 1];
                }
            }
        }

        return palindromeTable;
    }

    /**
     * Computes the minimum cuts needed for each prefix of the string,
     * using the precomputed palindrome table.
     */
    private static int[] computeMinCuts(boolean[][] palindromeTable) {
        int length = palindromeTable.length;
        int[] minCuts = new int[length];

        for (int end = 0; end < length; end++) {
            if (palindromeTable[0][end]) {
                minCuts[end] = 0;
                continue;
            }

            int currentMin = Integer.MAX_VALUE;
            for (int start = 0; start < end; start++) {
                if (palindromeTable[start + 1][end]) {
                    currentMin = Math.min(currentMin, minCuts[start] + 1);
                }
            }
            minCuts[end] = currentMin;
        }

        return minCuts;
    }
}