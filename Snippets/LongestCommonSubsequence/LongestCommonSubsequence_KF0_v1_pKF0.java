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
     * @param str1 The first string.
     * @param str2 The second string.
     * @return The LCS of the two strings, or null if one of the strings is null.
     */
    public static String getLCS(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return null;
        }
        if (str1.isEmpty() || str2.isEmpty()) {
            return "";
        }

        int len1 = str1.length();
        int len2 = str2.length();

        // lcsMatrix[i][j] = LCS length of first i chars of str1 and first j chars of str2
        int[][] lcsMatrix = new int[len1 + 1][len2 + 1];

        // Build the LCS matrix by comparing characters of str1 & str2
        for (int i = 1; i <= len1; i++) {
            char c1 = str1.charAt(i - 1);
            for (int j = 1; j <= len2; j++) {
                char c2 = str2.charAt(j - 1);
                if (c1 == c2) {
                    lcsMatrix[i][j] = lcsMatrix[i - 1][j - 1] + 1;
                } else {
                    lcsMatrix[i][j] = Math.max(lcsMatrix[i - 1][j], lcsMatrix[i][j - 1]);
                }
            }
        }

        return buildLcsString(str1, str2, lcsMatrix);
    }

    /**
     * Reconstructs the LCS string from the LCS matrix.
     *
     * @param str1      The first string.
     * @param str2      The second string.
     * @param lcsMatrix The matrix storing the lengths of LCSs
     *                  of substrings of str1 and str2.
     * @return The LCS string.
     */
    private static String buildLcsString(String str1, String str2, int[][] lcsMatrix) {
        StringBuilder lcs = new StringBuilder();
        int i = str1.length();
        int j = str2.length();

        // Trace back through the LCS matrix to reconstruct the LCS
        while (i > 0 && j > 0) {
            char c1 = str1.charAt(i - 1);
            char c2 = str2.charAt(j - 1);

            if (c1 == c2) {
                lcs.append(c1);
                i--;
                j--;
            } else if (lcsMatrix[i - 1][j] >= lcsMatrix[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.reverse().toString();
    }
}