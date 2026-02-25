package com.thealgorithms.dynamicprogramming;

public final class WildcardMatching {

    private WildcardMatching() {
        // Utility class; prevent instantiation
    }

    public static boolean isMatch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        dp[0][0] = true;

        for (int p = 1; p <= patternLength; p++) {
            if (pattern.charAt(p - 1) == '*') {
                dp[0][p] = dp[0][p - 1];
            }
        }

        for (int t = 1; t <= textLength; t++) {
            for (int p = 1; p <= patternLength; p++) {
                char textChar = text.charAt(t - 1);
                char patternChar = pattern.charAt(p - 1);

                if (patternChar == textChar || patternChar == '?') {
                    dp[t][p] = dp[t - 1][p - 1];
                } else if (patternChar == '*') {
                    dp[t][p] = dp[t - 1][p] || dp[t][p - 1];
                } else {
                    dp[t][p] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }
}