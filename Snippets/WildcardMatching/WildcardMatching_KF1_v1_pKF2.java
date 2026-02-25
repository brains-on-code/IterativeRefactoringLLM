package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for wildcard pattern matching.
 *
 * <p>Supports two wildcard characters in the pattern:
 * <ul>
 *   <li>'?' matches exactly one character</li>
 *   <li>'*' matches zero or more characters</li>
 * </ul>
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns {@code true} if the input string matches the given pattern.
     *
     * <p>Pattern rules:
     * <ul>
     *   <li>Normal characters must match themselves.</li>
     *   <li>'?' matches any single character.</li>
     *   <li>'*' matches any sequence of characters (including empty).</li>
     * </ul>
     *
     * @param text    the input string to test
     * @param pattern the pattern that may contain '?' and '*'
     * @return {@code true} if {@code text} matches {@code pattern}, otherwise {@code false}
     */
    public static boolean method1(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        // dp[i][j] is true if first i chars of text match first j chars of pattern
        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        // Empty pattern matches empty text
        dp[0][0] = true;

        // Handle patterns that start with '*' and can match empty text
        for (int j = 1; j <= patternLength; j++) {
            if (pattern.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1];
            }
        }

        // Fill DP table
        for (int i = 1; i <= textLength; i++) {
            for (int j = 1; j <= patternLength; j++) {
                char textChar = text.charAt(i - 1);
                char patternChar = pattern.charAt(j - 1);

                if (patternChar == textChar || patternChar == '?') {
                    // Current characters match; inherit result from previous characters
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (patternChar == '*') {
                    // '*' matches zero characters (dp[i][j - 1])
                    // or one more character (dp[i - 1][j])
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }
}