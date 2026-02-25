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
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the given pattern matches the entire text.
     *
     * @param text    the input text to match
     * @param pattern the pattern that may contain '?' and '*'
     * @return {@code true} if the pattern matches the text, {@code false} otherwise
     */
    public static boolean isMatch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        // dp[i][j] is true if the first i characters of text
        // match the first j characters of pattern
        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        // Empty pattern matches empty text
        dp[0][0] = true;

        // Handle leading '*' characters in the pattern:
        // they can match an empty text prefix
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
                    // Exact character match or single-character wildcard:
                    // rely on the result for the previous characters
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (patternChar == '*') {
                    // Multi-character wildcard:
                    //   - dp[i][j - 1]: '*' matches zero characters
                    //   - dp[i - 1][j]: '*' matches one more character
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else {
                    // Characters do not match and no wildcard applies
                    dp[i][j] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }
}