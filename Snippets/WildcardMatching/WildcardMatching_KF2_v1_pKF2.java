package com.thealgorithms.dynamicprogramming;

public final class WildcardMatching {

    private WildcardMatching() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if the given text matches the pattern.
     * Pattern supports:
     * - '?' : matches any single character
     * - '*' : matches any sequence of characters (including empty)
     *
     * Uses dynamic programming where dp[i][j] indicates whether
     * text[0..i-1] matches pattern[0..j-1].
     */
    public static boolean isMatch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        // Empty pattern matches empty text
        dp[0][0] = true;

        // Handle patterns with leading '*' that can match empty text
        for (int j = 1; j <= patternLength; j++) {
            if (pattern.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1];
            }
        }

        for (int i = 1; i <= textLength; i++) {
            for (int j = 1; j <= patternLength; j++) {
                char textChar = text.charAt(i - 1);
                char patternChar = pattern.charAt(j - 1);

                if (patternChar == textChar || patternChar == '?') {
                    // Current characters match; inherit result from previous characters
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (patternChar == '*') {
                    // '*' can match:
                    // - empty sequence: dp[i][j - 1]
                    // - one or more characters: dp[i - 1][j]
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else {
                    // Characters do not match and patternChar is not a wildcard
                    dp[i][j] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }
}