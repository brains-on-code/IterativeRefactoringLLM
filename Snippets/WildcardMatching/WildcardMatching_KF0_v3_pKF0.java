package com.thealgorithms.dynamicprogramming;

public final class WildcardMatching {

    private WildcardMatching() {
        // Utility class; prevent instantiation
    }

    public static boolean isMatch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        // Empty pattern matches empty text
        dp[0][0] = true;

        // Handle leading '*' in pattern that can match empty text
        for (int p = 1; p <= patternLength; p++) {
            if (pattern.charAt(p - 1) == '*') {
                dp[0][p] = dp[0][p - 1];
            } else {
                // Once a non-'*' is found, no further positions can match empty text
                break;
            }
        }

        for (int t = 1; t <= textLength; t++) {
            char textChar = text.charAt(t - 1);

            for (int p = 1; p <= patternLength; p++) {
                char patternChar = pattern.charAt(p - 1);

                if (patternChar == textChar || patternChar == '?') {
                    dp[t][p] = dp[t - 1][p - 1];
                } else if (patternChar == '*') {
                    // '*' matches zero characters (dp[t][p - 1])
                    // or one/more characters (dp[t - 1][p])
                    dp[t][p] = dp[t][p - 1] || dp[t - 1][p];
                }
            }
        }

        return dp[textLength][patternLength];
    }
}