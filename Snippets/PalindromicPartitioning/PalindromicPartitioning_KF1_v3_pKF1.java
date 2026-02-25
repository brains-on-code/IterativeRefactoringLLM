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
        int length = input.length();

        int[] minCutsUpToIndex = new int[length];
        boolean[][] isPalindromeSubstring = new boolean[length][length];

        // Every single character is a palindrome
        for (int index = 0; index < length; index++) {
            isPalindromeSubstring[index][index] = true;
        }

        // Fill palindrome table for substrings of length >= 2
        for (int substringLength = 2; substringLength <= length; substringLength++) {
            for (int startIndex = 0; startIndex <= length - substringLength; startIndex++) {
                int endIndex = startIndex + substringLength - 1;

                if (substringLength == 2) {
                    isPalindromeSubstring[startIndex][endIndex] =
                        input.charAt(startIndex) == input.charAt(endIndex);
                } else {
                    isPalindromeSubstring[startIndex][endIndex] =
                        input.charAt(startIndex) == input.charAt(endIndex)
                            && isPalindromeSubstring[startIndex + 1][endIndex - 1];
                }
            }
        }

        // Compute minimum cuts using the palindrome table
        for (int endIndex = 0; endIndex < length; endIndex++) {
            if (isPalindromeSubstring[0][endIndex]) {
                minCutsUpToIndex[endIndex] = 0;
            } else {
                int minCutsForEnd = Integer.MAX_VALUE;
                for (int partitionIndex = 0; partitionIndex < endIndex; partitionIndex++) {
                    if (isPalindromeSubstring[partitionIndex + 1][endIndex]
                        && minCutsUpToIndex[partitionIndex] + 1 < minCutsForEnd) {
                        minCutsForEnd = minCutsUpToIndex[partitionIndex] + 1;
                    }
                }
                minCutsUpToIndex[endIndex] = minCutsForEnd;
            }
        }

        return minCutsUpToIndex[length - 1];
    }
}