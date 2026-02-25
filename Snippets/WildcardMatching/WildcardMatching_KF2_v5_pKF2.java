package com.thealgorithms.dynamicprogramming;

public final class WildcardMatching {

    private WildcardMatching() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if the given text matches the given pattern.
     *
     * Wildcards in the pattern:
     * <ul>
     *   <li>{@code ?} matches any single character</li>
     *   <li>{@code *} matches any sequence of characters (including empty)</li>
     * </ul>
     *
     * Dynamic programming definition:
     * dp[i][j] is true if and only if text[0..i-1] matches pattern[0..j-1].
     */
    public static boolean isMatch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        // Base case: empty pattern matches empty text
        dp[0][0] = true;

        // Base case: pattern prefixes that can match empty text (only '*' can)
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
                    // Current characters match (or pattern has '?'):
                    // inherit match status from previous characters
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (patternChar == '*') {
                    // '*' can represent:
                    // - an empty sequence:  ignore '*' (dp[i][j - 1])
                    // - a non-empty sequence: consume one text char (dp[i - 1][j])
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else {
                    // Mismatched characters with no wildcard
                    dp[i][j] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }
}