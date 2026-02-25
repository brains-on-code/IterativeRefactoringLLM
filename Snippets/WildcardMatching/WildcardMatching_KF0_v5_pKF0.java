package com.thealgorithms.dynamicprogramming;

public final class WildcardMatching {

    private WildcardMatching() {
        // Utility class; prevent instantiation
    }

    public static boolean isMatch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        initializeForEmptyText(pattern, dp);
        fillDpTable(text, pattern, dp);

        return dp[textLength][patternLength];
    }

    private static void initializeForEmptyText(String pattern, boolean[][] dp) {
        int patternLength = pattern.length();
        dp[0][0] = true;

        for (int p = 1; p <= patternLength; p++) {
            if (pattern.charAt(p - 1) == '*') {
                dp[0][p] = dp[0][p - 1];
            } else {
                break;
            }
        }
    }

    private static void fillDpTable(String text, String pattern, boolean[][] dp) {
        int textLength = text.length();
        int patternLength = pattern.length();

        for (int t = 1; t <= textLength; t++) {
            char textChar = text.charAt(t - 1);

            for (int p = 1; p <= patternLength; p++) {
                char patternChar = pattern.charAt(p - 1);

                if (isDirectMatch(textChar, patternChar)) {
                    dp[t][p] = dp[t - 1][p - 1];
                } else if (patternChar == '*') {
                    dp[t][p] = dp[t][p - 1] || dp[t - 1][p];
                } else {
                    dp[t][p] = false;
                }
            }
        }
    }

    private static boolean isDirectMatch(char textChar, char patternChar) {
        return patternChar == textChar || patternChar == '?';
    }
}