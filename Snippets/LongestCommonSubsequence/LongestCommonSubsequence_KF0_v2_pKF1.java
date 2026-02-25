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

        String[] firstCharacters = firstString.split("");
        String[] secondCharacters = secondString.split("");

        int firstStringLength = firstCharacters.length;
        int secondStringLength = secondCharacters.length;

        // lcsLengths[i][j] = LCS(first i characters of firstString,
        //                        first j characters of secondString)
        int[][] lcsLengths = new int[firstStringLength + 1][secondStringLength + 1];

        // Base Case: 0th row & 0th column are already 0 by default

        // Build the LCS length matrix
        for (int rowIndex = 1; rowIndex <= firstStringLength; rowIndex++) {
            for (int columnIndex = 1; columnIndex <= secondStringLength; columnIndex++) {
                if (firstCharacters[rowIndex - 1].equals(secondCharacters[columnIndex - 1])) {
                    lcsLengths[rowIndex][columnIndex] = lcsLengths[rowIndex - 1][columnIndex - 1] + 1;
                } else {
                    lcsLengths[rowIndex][columnIndex] =
                        Math.max(lcsLengths[rowIndex - 1][columnIndex], lcsLengths[rowIndex][columnIndex - 1]);
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
        int firstStringIndex = firstString.length();
        int secondStringIndex = secondString.length();

        while (firstStringIndex > 0 && secondStringIndex > 0) {
            char firstCurrentChar = firstString.charAt(firstStringIndex - 1);
            char secondCurrentChar = secondString.charAt(secondStringIndex - 1);

            if (firstCurrentChar == secondCurrentChar) {
                lcsBuilder.append(firstCurrentChar);
                firstStringIndex--;
                secondStringIndex--;
            } else if (lcsLengths[firstStringIndex - 1][secondStringIndex] > lcsLengths[firstStringIndex][secondStringIndex - 1]) {
                firstStringIndex--;
            } else {
                secondStringIndex--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}