package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for wildcard pattern matching.
 *
 * Supported wildcards:
 * - '?' matches exactly one character.
 * - '*' matches zero or more characters.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Returns true if the given text matches the pattern.
     *
     * @param text    the input string to test
     * @param pattern the pattern that may contain '?' and '*'
     * @return true if text matches pattern, false otherwise
     */
    public static boolean method1(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];

        dp[0][0] = true;
        initializeFirstRow(pattern, patternLength, dp);

        for (int textIndex = 1; textIndex <= textLength; textIndex++) {
            for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
                char textChar = text.charAt(textIndex - 1);
                char patternChar = pattern.charAt(patternIndex - 1);

                if (charactersMatch(textChar, patternChar)) {
                    dp[textIndex][patternIndex] = dp[textIndex - 1][patternIndex - 1];
                } else if (patternChar == '*') {
                    dp[textIndex][patternIndex] =
                        matchesZeroCharacters(dp, textIndex, patternIndex)
                            || matchesOneOrMoreCharacters(dp, textIndex, patternIndex);
                } else {
                    dp[textIndex][patternIndex] = false;
                }
            }
        }

        return dp[textLength][patternLength];
    }

    private static void initializeFirstRow(String pattern, int patternLength, boolean[][] dp) {
        for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
            if (pattern.charAt(patternIndex - 1) == '*') {
                dp[0][patternIndex] = dp[0][patternIndex - 1];
            }
        }
    }

    private static boolean charactersMatch(char textChar, char patternChar) {
        return patternChar == textChar || patternChar == '?';
    }

    private static boolean matchesZeroCharacters(boolean[][] dp, int textIndex, int patternIndex) {
        return dp[textIndex][patternIndex - 1];
    }

    private static boolean matchesOneOrMoreCharacters(boolean[][] dp, int textIndex, int patternIndex) {
        return dp[textIndex - 1][patternIndex];
    }
}