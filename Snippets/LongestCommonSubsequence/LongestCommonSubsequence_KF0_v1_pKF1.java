package com.thealgorithms.dynamicprogramming;

/**
 * This class implements the Longest Common Subsequence (LCS) problem.
 * The LCS of two sequences is the longest sequence that appears in both
 * sequences in the same order, but not necessarily consecutively.
 *
 * This implementation uses dynamic programming to find the LCS of two strings.
 */
final class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
    }

    /**
     * Returns the Longest Common Subsequence (LCS) of two given strings.
     *
     * @param firstString  The first string.
     * @param secondString The second string.
     * @return The LCS of the two strings, or null if one of the strings is null.
     */
    public static String getLCS(String firstString, String secondString) {
        if (firstString == null || secondString == null) {
            return null;
        }
        if (firstString.isEmpty() || secondString.isEmpty()) {
            return "";
        }

        String[] firstChars = firstString.split("");
        String[] secondChars = secondString.split("");

        int firstLength = firstChars.length;
        int secondLength = secondChars.length;

        // lcsLengths[i][j] = LCS(first i characters of firstString,
        //                        first j characters of secondString)
        int[][] lcsLengths = new int[firstLength + 1][secondLength + 1];

        // Base Case: 0th row & 0th column are already 0 by default

        // Build the LCS length matrix
        for (int row = 1; row <= firstLength; row++) {
            for (int col = 1; col <= secondLength; col++) {
                if (firstChars[row - 1].equals(secondChars[col - 1])) {
                    lcsLengths[row][col] = lcsLengths[row - 1][col - 1] + 1;
                } else {
                    lcsLengths[row][col] = Math.max(lcsLengths[row - 1][col], lcsLengths[row][col - 1]);
                }
            }
        }

        return buildLcsString(firstString, secondString, lcsLengths);
    }

    /**
     * Reconstructs the LCS string from the LCS length matrix.
     *
     * @param firstString  The first string.
     * @param secondString The second string.
     * @param lcsLengths   The matrix storing the lengths of LCSs
     *                     of substrings of firstString and secondString.
     * @return The LCS string.
     */
    public static String buildLcsString(String firstString, String secondString, int[][] lcsLengths) {
        StringBuilder lcsBuilder = new StringBuilder();
        int firstIndex = firstString.length();
        int secondIndex = secondString.length();

        while (firstIndex > 0 && secondIndex > 0) {
            char firstChar = firstString.charAt(firstIndex - 1);
            char secondChar = secondString.charAt(secondIndex - 1);

            if (firstChar == secondChar) {
                lcsBuilder.append(firstChar);
                firstIndex--;
                secondIndex--;
            } else if (lcsLengths[firstIndex - 1][secondIndex] > lcsLengths[firstIndex][secondIndex - 1]) {
                firstIndex--;
            } else {
                secondIndex--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}