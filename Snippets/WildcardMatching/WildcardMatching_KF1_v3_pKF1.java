package com.thealgorithms.dynamicprogramming;

public final class WildcardPatternMatcher {

    private WildcardPatternMatcher() {
    }

    /**
     * Returns true if the input string matches the given pattern.
     * Pattern supports:
     *   '?' – matches any single character
     *   '*' – matches any sequence of characters (including empty)
     */
    public static boolean matches(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        dp[0][0] = true;

        for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
            if (pattern.charAt(patternIndex - 1) == '*') {
                dp[0][patternIndex] = dp[0][patternIndex - 1];
            }
        }

        for (int textIndex = 1; textIndex <= textLength; textIndex++) {
            for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
                char textChar = text.charAt(textIndex - 1);
                char patternChar = pattern.charAt(patternIndex - 1);

                if (patternChar == textChar || patternChar == '?') {
                    dp[textIndex][patternIndex] = dp[textIndex - 1][patternIndex - 1];
                } else if (patternChar == '*') {
                    dp[textIndex][patternIndex] =
                        dp[textIndex - 1][patternIndex] || dp[textIndex][patternIndex - 1];
                } else {
                    dp[textIndex][patternIndex] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }
}