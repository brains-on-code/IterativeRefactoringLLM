package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for wildcard pattern matching.
 *
 * Supported wildcards:
 * - '?' matches exactly one character.
 * - '*' matches zero or more characters.
 */
public final class WildcardMatcher {

    private WildcardMatcher() {
        // Prevent instantiation
    }

    /**
     * Returns true if the given text matches the pattern.
     *
     * @param text    the input string to test
     * @param pattern the pattern that may contain '?' and '*'
     * @return true if text matches pattern, false otherwise
     */
    public static boolean matches(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];
        dp[0][0] = true;

        initializeLeadingStarMatches(pattern, dp);

        for (int textIndex = 1; textIndex <= textLength; textIndex++) {
            for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
                char textChar = text.charAt(textIndex - 1);
                char patternChar = pattern.charAt(patternIndex - 1);

                if (isDirectMatch(textChar, patternChar)) {
                    dp[textIndex][patternIndex] = dp[textIndex - 1][patternIndex - 1];
                } else if (patternChar == '*') {
                    dp[textIndex][patternIndex] =
                        matchesEmptySequence(dp, textIndex, patternIndex)
                            || matchesNonEmptySequence(dp, textIndex, patternIndex);
                } else {
                    dp[textIndex][patternIndex] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }

    private static void initializeLeadingStarMatches(String pattern, boolean[][] dp) {
        int patternLength = pattern.length();
        for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
            if (pattern.charAt(patternIndex - 1) == '*') {
                dp[0][patternIndex] = dp[0][patternIndex - 1];
            } else {
                // Once a non-* character is found, no further leading positions can match empty text
                break;
            }
        }
    }

    private static boolean isDirectMatch(char textChar, char patternChar) {
        return patternChar == textChar || patternChar == '?';
    }

    private static boolean matchesEmptySequence(boolean[][] dp, int textIndex, int patternIndex) {
        // '*' matches zero characters: rely on previous pattern position
        return dp[textIndex][patternIndex - 1];
    }

    private static boolean matchesNonEmptySequence(boolean[][] dp, int textIndex, int patternIndex) {
        // '*' matches one or more characters: rely on previous text position
        return dp[textIndex - 1][patternIndex];
    }
}