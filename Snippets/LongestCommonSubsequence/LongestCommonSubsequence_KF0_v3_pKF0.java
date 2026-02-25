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
        // Utility class; prevent instantiation.
    }

    /**
     * Returns the Longest Common Subsequence (LCS) of two given strings.
     *
     * @param first  The first string.
     * @param second The second string.
     * @return The LCS of the two strings, or null if one of the strings is null.
     */
    public static String getLCS(String first, String second) {
        if (first == null || second == null) {
            return null;
        }
        if (first.isEmpty() || second.isEmpty()) {
            return "";
        }

        int firstLength = first.length();
        int secondLength = second.length();

        int[][] lcsLengths = new int[firstLength + 1][secondLength + 1];

        fillLcsTable(first, second, lcsLengths);

        return buildLcsString(first, second, lcsLengths);
    }

    private static void fillLcsTable(String first, String second, int[][] lcsLengths) {
        int firstLength = first.length();
        int secondLength = second.length();

        for (int i = 1; i <= firstLength; i++) {
            char firstChar = first.charAt(i - 1);
            for (int j = 1; j <= secondLength; j++) {
                char secondChar = second.charAt(j - 1);
                if (firstChar == secondChar) {
                    lcsLengths[i][j] = lcsLengths[i - 1][j - 1] + 1;
                } else {
                    lcsLengths[i][j] = Math.max(lcsLengths[i - 1][j], lcsLengths[i][j - 1]);
                }
            }
        }
    }

    /**
     * Reconstructs the LCS string from the LCS matrix.
     *
     * @param first      The first string.
     * @param second     The second string.
     * @param lcsLengths The matrix storing the lengths of LCSs
     *                   of substrings of first and second.
     * @return The LCS string.
     */
    private static String buildLcsString(String first, String second, int[][] lcsLengths) {
        StringBuilder lcsBuilder = new StringBuilder();
        int i = first.length();
        int j = second.length();

        while (i > 0 && j > 0) {
            char firstChar = first.charAt(i - 1);
            char secondChar = second.charAt(j - 1);

            if (firstChar == secondChar) {
                lcsBuilder.append(firstChar);
                i--;
                j--;
            } else if (lcsLengths[i - 1][j] >= lcsLengths[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}