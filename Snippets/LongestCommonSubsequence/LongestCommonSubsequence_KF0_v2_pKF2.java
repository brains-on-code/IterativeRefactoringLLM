package com.thealgorithms.dynamicprogramming;

/**
 * Dynamic programming solution for the Longest Common Subsequence (LCS) problem.
 *
 * The LCS of two sequences is the longest sequence that appears in both
 * sequences in the same order (not necessarily contiguously).
 */
final class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
        // Prevent instantiation
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

        int len1 = str1.length();
        int len2 = str2.length();

        /*
         * lcsMatrix[i][j] stores the length of the LCS of:
         * - the first i characters of str1, and
         * - the first j characters of str2.
         *
         * Dimensions are (len1 + 1) x (len2 + 1) to include the empty-prefix base case.
         */
        int[][] lcsMatrix = new int[len1 + 1][len2 + 1];

        // Fill DP table
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
     * Reconstructs the LCS string from the DP matrix by backtracking from the bottom-right corner.
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

        // Backtrack to build the LCS characters in reverse order
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