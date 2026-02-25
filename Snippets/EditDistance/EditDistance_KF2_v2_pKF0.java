package com.thealgorithms.dynamicprogramming;

public final class EditDistance {

    private EditDistance() {
        // Utility class; prevent instantiation
    }

    public static int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) {
            throw new IllegalArgumentException("Input strings must not be null");
        }

        int len1 = word1.length();
        int len2 = word2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];

        // Base cases: distance from empty string
        for (int i = 0; i <= len1; i++) {
            dp[i][0] = i; // deletions
        }
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j; // insertions
        }

        for (int i = 1; i <= len1; i++) {
            char c1 = word1.charAt(i - 1);
            for (int j = 1; j <= len2; j++) {
                char c2 = word2.charAt(j - 1);

                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1];
                    continue;
                }

                int replaceCost = dp[i - 1][j - 1] + 1;
                int insertCost = dp[i][j - 1] + 1;
                int deleteCost = dp[i - 1][j] + 1;

                dp[i][j] = Math.min(replaceCost, Math.min(insertCost, deleteCost));
            }
        }

        return dp[len1][len2];
    }

    public static int editDistance(String s1, String s2) {
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException("Input strings must not be null");
        }

        int[][] memo = new int[s1.length() + 1][s2.length() + 1];
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

        if (s1.charAt(s1.length() - m) == s2.charAt(s2.length() - n)) {
            memo[m][n] = editDistanceHelper(s1, s2, m - 1, n - 1, memo);
            return memo[m][n];
        }

        int insertCost = editDistanceHelper(s1, s2, m, n - 1, memo);
        int deleteCost = editDistanceHelper(s1, s2, m - 1, n, memo);
        int replaceCost = editDistanceHelper(s1, s2, m - 1, n - 1, memo);

        memo[m][n] = 1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        return memo[m][n];
    }
}