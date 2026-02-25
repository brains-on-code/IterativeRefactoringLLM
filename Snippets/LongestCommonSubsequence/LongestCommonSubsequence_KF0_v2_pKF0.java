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

        // dp[i][j] = LCS length of first i chars of first and first j chars of second
        int[][] dp = new int[firstLength + 1][secondLength + 1];

        fillLcsTable(first, second, dp);

        return buildLcsString(first, second, dp);
    }

    private static void fillLcsTable(String first, String second, int[][] dp) {
        int firstLength = first.length();
        int secondLength = second.length();

        for (int i = 1; i <= firstLength; i++) {
            char firstChar = first.charAt(i - 1);
            for (int j = 1; j <= secondLength; j++) {
                char secondChar = second.charAt(j - 1);
                if (firstChar == secondChar) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
    }

    /**
     * Reconstructs the LCS string from the LCS matrix.
     *
     * @param first The first string.
     * @param second The second string.
     * @param dp The matrix storing the lengths of LCSs
     *           of substrings of first and second.
     * @return The LCS string.
     */
    private static String buildLcsString(String first, String second, int[][] dp) {
        StringBuilder lcs = new StringBuilder();
        int i = first.length();
        int j = second.length();

        while (i > 0 && j > 0) {
            char firstChar = first.charAt(i - 1);
            char secondChar = second.charAt(j - 1);

            if (firstChar == secondChar) {
                lcs.append(firstChar);
                i--;
                j--;
            } else if (dp[i - 1][j] >= dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.reverse().toString();
    }
}