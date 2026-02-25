package com.thealgorithms.dynamicprogramming;

public final class WildcardMatching {

    private WildcardMatching() {
        // Prevent instantiation
    }

    /**
     * Returns {@code true} if {@code text} matches {@code pattern}.
     *
     * Wildcards:
     * <ul>
     *   <li>{@code ?} matches any single character</li>
     *   <li>{@code *} matches any sequence of characters (including empty)</li>
     * </ul>
     *
     * Dynamic programming:
     * dp[i][j] == true iff text[0..i-1] matches pattern[0..j-1].
     */
    public static boolean isMatch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        // Empty pattern matches empty text
        dp[0][0] = true;

        // Initialize first row: pattern prefixes that can match empty text
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
                    // Exact match or '?' wildcard: inherit diagonal state
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (patternChar == '*') {
                    // '*' as:
                    // - empty sequence: dp[i][j - 1]
                    // - one more character: dp[i - 1][j]
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }
}