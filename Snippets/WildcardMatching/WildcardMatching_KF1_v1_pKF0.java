package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for wildcard pattern matching.
 *
 * Supported wildcards:
 * - '?' matches exactly one character.
 * - '*' matches zero or more characters.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Returns true if the given text matches the pattern.
     *
     * @param text    the input string to test
     * @param pattern the pattern that may contain '?' and '*'
     * @return true if text matches pattern, false otherwise
     */
    public static boolean method1(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        // dp[i][j] is true if first i chars of text match first j chars of pattern
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
                    // - zero characters: dp[i][j - 1]
                    // - one or more characters: dp[i - 1][j]
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }
}