package com.thealgorithms.dynamicprogramming;

public final class EditDistance {

    private EditDistance() {
        // Utility class; prevent instantiation
    }

    public static int minDistance(String word1, String word2) {
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

        for (int i = 0; i < len1; i++) {
            char c1 = word1.charAt(i);
            for (int j = 0; j < len2; j++) {
                char c2 = word2.charAt(j);

                if (c1 == c2) {
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int replaceCost = dp[i][j] + 1;
                    int insertCost = dp[i][j + 1] + 1;
                    int deleteCost = dp[i + 1][j] + 1;

                    dp[i + 1][j + 1] = Math.min(replaceCost, Math.min(insertCost, deleteCost));
                }
            }
        }

        return dp[len1][len2];
    }

    public static int editDistance(String s1, String s2) {
        int[][] memo = new int[s1.length() + 1][s2.length() + 1];
        return editDistance(s1, s2, memo);
    }

    public static int editDistance(String s1, String s2, int[][] memo) {
        int m = s1.length();
        int n = s2.length();

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

        if (s1.charAt(0) == s2.charAt(0)) {
            memo[m][n] = editDistance(s1.substring(1), s2.substring(1), memo);
        } else {
            int insertCost = editDistance(s1, s2.substring(1), memo);
            int deleteCost = editDistance(s1.substring(1), s2, memo);
            int replaceCost = editDistance(s1.substring(1), s2.substring(1), memo);

            memo[m][n] = 1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        }

        return memo[m][n];
    }
}