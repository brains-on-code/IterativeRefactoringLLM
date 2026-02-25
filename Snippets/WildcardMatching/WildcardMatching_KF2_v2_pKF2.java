package com.thealgorithms.dynamicprogramming;

public final class WildcardMatching {

    private WildcardMatching() {
        // Prevent instantiation
    }

    /**
     * Returns true if {@code text} matches {@code pattern}.
     *
     * Supported wildcards:
     * <ul>
     *   <li>{@code ?} matches any single character</li>
     *   <li>{@code *} matches any sequence of characters (including empty)</li>
     * </ul>
     *
     * Dynamic programming definition:
     * dp[i][j] is true iff text[0..i-1] matches pattern[0..j-1].
     */
    public static boolean isMatch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        // Base case: empty pattern matches empty text
        dp[0][0] = true;

        // Base case: leading '*' characters can match empty text
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
                    // Exact match or single-character wildcard
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (patternChar == '*') {
                    // '*' matches:
                    // - empty sequence: dp[i][j - 1]
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