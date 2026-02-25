package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the Longest Common Subsequence (LCS) between two strings.
 */
final class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the Longest Common Subsequence (LCS) of two strings.
     *
     * @param first  the first input string
     * @param second the second input string
     * @return the LCS of {@code first} and {@code second};
     *         {@code null} if either input is {@code null};
     *         empty string if either input is empty
     */
    public static String computeLCS(String first, String second) {
        if (first == null || second == null) {
            return null;
        }
        if (first.isEmpty() || second.isEmpty()) {
            return "";
        }

        int m = first.length();
        int n = second.length();

        /*
         * dp[i][j] stores the length of the LCS of:
         *   first[0..i-1] and second[0..j-1]
         */
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            char c1 = first.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char c2 = second.charAt(j - 1);
                if (c1 == c2) {
                    // Characters match: extend the previous LCS by one.
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // Characters do not match: take the best of skipping one from either string.
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return reconstructLCS(first, second, dp);
    }

    /**
     * Reconstructs the Longest Common Subsequence (LCS) from the DP table.
     *
     * @param first  the first input string
     * @param second the second input string
     * @param dp     the DP table containing LCS lengths
     * @return the reconstructed LCS string
     */
    public static String reconstructLCS(String first, String second, int[][] dp) {
        StringBuilder lcsBuilder = new StringBuilder();
        int i = first.length();
        int j = second.length();

        while (i > 0 && j > 0) {
            char c1 = first.charAt(i - 1);
            char c2 = second.charAt(j - 1);

            if (c1 == c2) {
                // Current characters are part of the LCS.
                lcsBuilder.append(c1);
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // Move towards the direction of the larger LCS value (up).
                i--;
            } else {
                // Move towards the direction of the larger LCS value (left).
                j--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}