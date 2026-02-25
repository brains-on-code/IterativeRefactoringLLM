package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for wildcard pattern matching.
 *
 * <p>Supported wildcards in the pattern:
 * <ul>
 *   <li>'?' matches exactly one character</li>
 *   <li>'*' matches zero or more characters</li>
 * </ul>
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Checks whether the given text matches the specified wildcard pattern.
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

        // dp[i][j] == true if the first i characters of text
        // match the first j characters of pattern
        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        // Empty pattern matches empty text
        dp[0][0] = true;

        // Initialize first row: patterns that can match an empty text
        for (int j = 1; j <= patternLength; j++) {
            if (pattern.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1];
            }
        }

        // Fill the DP table
        for (int i = 1; i <= textLength; i++) {
            for (int j = 1; j <= patternLength; j++) {
                char textChar = text.charAt(i - 1);
                char patternChar = pattern.charAt(j - 1);

                if (patternChar == textChar || patternChar == '?') {
                    // Current characters match; rely on the result for the previous characters
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (patternChar == '*') {
                    // '*' matches:
                    // - zero characters: dp[i][j - 1]
                    // - one additional character: dp[i - 1][j]
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }
}