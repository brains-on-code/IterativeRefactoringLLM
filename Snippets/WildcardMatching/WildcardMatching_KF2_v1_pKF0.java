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

        // Handle patterns with leading '*' that can match empty text
        for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
            if (pattern.charAt(patternIndex - 1) == '*') {
                dp[0][patternIndex] = dp[0][patternIndex - 1];
            }
        }

        for (int textIndex = 1; textIndex <= textLength; textIndex++) {
            char currentTextChar = text.charAt(textIndex - 1);

            for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
                char currentPatternChar = pattern.charAt(patternIndex - 1);

                if (currentPatternChar == currentTextChar || currentPatternChar == '?') {
                    dp[textIndex][patternIndex] = dp[textIndex - 1][patternIndex - 1];
                } else if (currentPatternChar == '*') {
                    dp[textIndex][patternIndex] =
                        dp[textIndex - 1][patternIndex] || // '*' matches current text char
                        dp[textIndex][patternIndex - 1];   // '*' matches empty sequence
                } else {
                    dp[textIndex][patternIndex] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }
}