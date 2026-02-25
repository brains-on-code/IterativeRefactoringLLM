package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for wildcard pattern matching using dynamic programming.
 *
 * <p>Supported wildcards in the pattern:
 * <ul>
 *   <li>'?': matches exactly one character</li>
 *   <li>'*': matches zero or more characters</li>
 * </ul>
 *
 * <p>The match must cover the entire input text.
 */
public final class WildcardMatching {

    private WildcardMatching() {
        // Prevent instantiation
    }

    /**
     * Returns {@code true} if the given pattern matches the entire text, {@code false} otherwise.
     *
     * @param text    the input text to match
     * @param pattern the pattern that may contain '?' and '*'
     * @return {@code true} if the pattern matches the text, {@code false} otherwise
     */
    public static boolean isMatch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        // Empty pattern matches empty text
        dp[0][0] = true;

        // An initial run of '*' can match an empty text
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
                    // '*' matches:
                    //   - zero characters: dp[i][j - 1]
                    //   - one or more characters: dp[i - 1][j]
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }
}