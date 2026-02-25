package com.thealgorithms.dynamicprogramming;

public final class EditDistance {

    private EditDistance() {
        // Utility class; prevent instantiation
    }

    public static int minDistance(String word1, String word2) {
        validateNotNull(word1, word2);

        final int len1 = word1.length();
        final int len2 = word2.length();
        final int[][] dp = new int[len1 + 1][len2 + 1];

        initializeBaseCases(dp, len1, len2);

        for (int i = 1; i <= len1; i++) {
            final char c1 = word1.charAt(i - 1);
            for (int j = 1; j <= len2; j++) {
                final char c2 = word2.charAt(j - 1);

                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1];
                    continue;
                }

                final int replaceCost = dp[i - 1][j - 1] + 1;
                final int insertCost = dp[i][j - 1] + 1;
                final int deleteCost = dp[i - 1][j] + 1;

                dp[i][j] = Math.min(replaceCost, Math.min(insertCost, deleteCost));
            }
        }

        return dp[len1][len2];
    }

    private static void initializeBaseCases(int[][] dp, int len1, int len2) {
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i; // deletions
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j; // insertions
        }
    }

    public static int editDistance(String s1, String s2) {
        validateNotNull(s1, s2);
        final int[][] memo = new int[s1.length() + 1][s2.length() + 1];
        return editDistance(s1, s2, memo);
    }

    public static int editDistance(String s1, String s2, int[][] memo) {
        return editDistanceHelper(s1, s2, s1.length(), s2.length(), memo);
    }

    private static int editDistanceHelper(String s1, String s2, int m, int n, int[][] memo) {
        if (memo[m][n] > 0) {
            return memo[m][n];
        }

        if (m == 0) {
            memo[m][n] = n;
            return n;
        }

        if (n == 0) {
            memo[m][n] = m;
            return m;
        }

        final int index1 = s1.length() - m;
        final int index2 = s2.length() - n;

        if (s1.charAt(index1) == s2.charAt(index2)) {
            memo[m][n] = editDistanceHelper(s1, s2, m - 1, n - 1, memo);
            return memo[m][n];
        }

        final int insertCost = editDistanceHelper(s1, s2, m, n - 1, memo);
        final int deleteCost = editDistanceHelper(s1, s2, m - 1, n, memo);
        final int replaceCost = editDistanceHelper(s1, s2, m - 1, n - 1, memo);

        memo[m][n] = 1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        return memo[m][n];
    }

    private static void validateNotNull(String s1, String s2) {
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException("Input strings must not be null");
        }
    }
}