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

        /*
         * dp[i][j] is true if and only if:
         *   text[0..i-1] matches pattern[0..j-1]
         */
        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        // Base case: empty pattern matches empty text
        dp[0][0] = true;

        /*
         * Initialize first row (i = 0):
         * An empty text can only be matched by a pattern prefix consisting solely of '*'.
         */
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
                    // Exact character match or '?' wildcard: consume one character from both
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (patternChar == '*') {
                    /*
                     * '*' wildcard:
                     *   - dp[i][j - 1]: '*' matches empty sequence (ignore '*')
                     *   - dp[i - 1][j]: '*' matches one more character from text
                     */
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else {
                    // Mismatched characters with no applicable wildcard
                    dp[i][j] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }
}