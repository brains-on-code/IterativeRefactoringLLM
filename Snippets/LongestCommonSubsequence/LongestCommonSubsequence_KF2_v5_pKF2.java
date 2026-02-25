package com.thealgorithms.dynamicprogramming;

final class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the Longest Common Subsequence (LCS) of two strings.
     *
     * @param str1 first input string
     * @param str2 second input string
     * @return the LCS string, or null if either input is null
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
         * dp[i][j] = length of LCS of:
         *   str1[0..i-1] and str2[0..j-1]
         */
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 1; i <= len1; i++) {
            char c1 = str1.charAt(i - 1);
            for (int j = 1; j <= len2; j++) {
                char c2 = str2.charAt(j - 1);
                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return buildLcsString(str1, str2, dp);
    }

    /**
     * Reconstructs the LCS string from the DP table.
     *
     * @param str1 first input string
     * @param str2 second input string
     * @param dp   DP table with LCS lengths
     * @return the reconstructed LCS string
     */
    private static String buildLcsString(String str1, String str2, int[][] dp) {
        StringBuilder lcs = new StringBuilder();
        int i = str1.length();
        int j = str2.length();

        // Walk backwards through the table to reconstruct the LCS
        while (i > 0 && j > 0) {
            char c1 = str1.charAt(i - 1);
            char c2 = str2.charAt(j - 1);

            if (c1 == c2) {
                lcs.append(c1);
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.reverse().toString();
    }
}