package com.thealgorithms.dynamicprogramming;

/**
 * Implements the Longest Common Subsequence (LCS) problem using dynamic programming.
 *
 * The LCS of two sequences is the longest sequence that appears in both
 * sequences in the same order, but not necessarily consecutively.
 */
final class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the Longest Common Subsequence (LCS) of two strings.
     *
     * @param str1 the first string
     * @param str2 the second string
     * @return the LCS of the two strings, or {@code null} if either string is {@code null}
     */
    public static String getLCS(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return null;
        }
        if (str1.isEmpty() || str2.isEmpty()) {
            return "";
        }

        String[] arr1 = str1.split("");
        String[] arr2 = str2.split("");

        // lcsMatrix[i][j] = length of LCS of first i chars of str1 and first j chars of str2
        int[][] lcsMatrix = new int[arr1.length + 1][arr2.length + 1];

        // First row and first column are already 0 by default (LCS with empty string)

        for (int i = 1; i <= arr1.length; i++) {
            for (int j = 1; j <= arr2.length; j++) {
                if (arr1[i - 1].equals(arr2[j - 1])) {
                    lcsMatrix[i][j] = lcsMatrix[i - 1][j - 1] + 1;
                } else {
                    lcsMatrix[i][j] = Math.max(lcsMatrix[i - 1][j], lcsMatrix[i][j - 1]);
                }
            }
        }

        return buildLcsString(str1, str2, lcsMatrix);
    }

    /**
     * Reconstructs the LCS string from the DP matrix.
     *
     * @param str1      the first string
     * @param str2      the second string
     * @param lcsMatrix the DP matrix with LCS lengths
     * @return the LCS string
     */
    public static String buildLcsString(String str1, String str2, int[][] lcsMatrix) {
        StringBuilder lcs = new StringBuilder();
        int i = str1.length();
        int j = str2.length();

        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                lcs.append(str1.charAt(i - 1));
                i--;
                j--;
            } else if (lcsMatrix[i - 1][j] > lcsMatrix[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.reverse().toString();
    }
}