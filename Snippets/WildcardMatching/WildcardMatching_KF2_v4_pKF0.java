package com.thealgorithms.dynamicprogramming;

public final class WildcardMatching {

    private WildcardMatching() {
        // Utility class; prevent instantiation
    }

    public static boolean isMatch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        initializeFirstRow(pattern, dp);
        fillDpTable(text, pattern, dp);

        return dp[textLength][patternLength];
    }

    private static void initializeFirstRow(String pattern, boolean[][] dp) {
        int patternLength = pattern.length();
        dp[0][0] = true;

        for (int j = 1; j <= patternLength; j++) {
            dp[0][j] = pattern.charAt(j - 1) == '*' && dp[0][j - 1];
        }
    }

    private static void fillDpTable(String text, String pattern, boolean[][] dp) {
        int textLength = text.length();
        int patternLength = pattern.length();

        for (int i = 1; i <= textLength; i++) {
            char textChar = text.charAt(i - 1);

            for (int j = 1; j <= patternLength; j++) {
                char patternChar = pattern.charAt(j - 1);

                if (isDirectMatch(textChar, patternChar)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (patternChar == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                } else {
                    dp[i][j] = false;
                }
            }
        }
    }

    private static boolean isDirectMatch(char textChar, char patternChar) {
        return patternChar == textChar || patternChar == '?';
    }
}