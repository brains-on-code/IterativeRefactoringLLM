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

        for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
            if (pattern.charAt(patternIndex - 1) == '*') {
                dp[0][patternIndex] = dp[0][patternIndex - 1];
            } else {
                dp[0][patternIndex] = false;
            }
        }
    }

    private static void fillDpTable(String text, String pattern, boolean[][] dp) {
        int textLength = text.length();
        int patternLength = pattern.length();

        for (int textIndex = 1; textIndex <= textLength; textIndex++) {
            char textChar = text.charAt(textIndex - 1);

            for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
                char patternChar = pattern.charAt(patternIndex - 1);

                if (isDirectMatch(textChar, patternChar)) {
                    dp[textIndex][patternIndex] = dp[textIndex - 1][patternIndex - 1];
                } else if (patternChar == '*') {
                    boolean matchWithChar = dp[textIndex - 1][patternIndex];
                    boolean matchEmpty = dp[textIndex][patternIndex - 1];
                    dp[textIndex][patternIndex] = matchWithChar || matchEmpty;
                } else {
                    dp[textIndex][patternIndex] = false;
                }
            }
        }
    }

    private static boolean isDirectMatch(char textChar, char patternChar) {
        return patternChar == textChar || patternChar == '?';
    }
}