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
        // Utility class; prevent instantiation
    }

    /**
     * Returns {@code true} if the given {@code text} matches the wildcard {@code pattern}.
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

        /*
         * dp[i][j] is true if and only if:
         *   the first i characters of text (text[0..i-1])
         *   match the first j characters of pattern (pattern[0..j-1])
         */
        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        // Base case: empty pattern matches empty text
        dp[0][0] = true;

        // Base cases for an empty text (i == 0):
        // Only a prefix of '*' characters in the pattern can match an empty text.
        for (int j = 1; j <= patternLength; j++) {
            if (pattern.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 1];
            }
        }

        // Fill the DP table for non-empty prefixes of text and pattern
        for (int i = 1; i <= textLength; i++) {
            for (int j = 1; j <= patternLength; j++) {
                char textChar = text.charAt(i - 1);
                char patternChar = pattern.charAt(j - 1);

                if (patternChar == textChar || patternChar == '?') {
                    // Current characters match; inherit result from previous characters
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (patternChar == '*') {
                    /*
                     * '*' can represent:
                     *   - zero characters:  dp[i][j - 1]
                     *   - one more character (extend previous match): dp[i - 1][j]
                     */
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else {
                    // Mismatched literal characters
                    dp[i][j] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }
}