package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for wildcard pattern matching.
 *
 * Supported wildcards in the pattern:
 * - '?' matches exactly one character
 * - '*' matches zero or more characters
 */
public final class Class1 {

    private static final char SINGLE_WILDCARD = '?';
    private static final char MULTI_WILDCARD = '*';

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Recursive wildcard matching using substrings.
     *
     * @param text    the input text
     * @param pattern the pattern containing wildcards
     * @return true if the pattern matches the entire text, false otherwise
     */
    public static boolean method3(String text, String pattern) {
        if (text.isEmpty() && pattern.isEmpty()) {
            return true;
        }

        if (!text.isEmpty() && pattern.isEmpty()) {
            return false;
        }

        if (text.isEmpty()) {
            return isAllStars(pattern, 0);
        }

        char textChar = text.charAt(0);
        char patternChar = pattern.charAt(0);

        String remainingText = text.substring(1);
        String remainingPattern = pattern.substring(1);

        if (patternChar == textChar || patternChar == SINGLE_WILDCARD) {
            return method3(remainingText, remainingPattern);
        }

        if (patternChar == MULTI_WILDCARD) {
            boolean matchZeroChars = method3(text, remainingPattern);
            boolean matchOneOrMoreChars = method3(remainingText, pattern);
            return matchZeroChars || matchOneOrMoreChars;
        }

        return false;
    }

    /**
     * Recursive wildcard matching using indices (no memoization).
     *
     * @param text          the input text
     * @param pattern       the pattern containing wildcards
     * @param textIndex     current index in text
     * @param patternIndex  current index in pattern
     * @return true if the pattern matches the text from the given indices, false otherwise
     */
    static boolean method3(String text, String pattern, int textIndex, int patternIndex) {
        int textLength = text.length();
        int patternLength = pattern.length();

        if (textIndex == textLength && patternIndex == patternLength) {
            return true;
        }

        if (textIndex != textLength && patternIndex == patternLength) {
            return false;
        }

        if (textIndex == textLength) {
            return isAllStars(pattern, patternIndex);
        }

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patternIndex);

        if (patternChar == textChar || patternChar == SINGLE_WILDCARD) {
            return method3(text, pattern, textIndex + 1, patternIndex + 1);
        }

        if (patternChar == MULTI_WILDCARD) {
            boolean matchZeroChars = method3(text, pattern, textIndex, patternIndex + 1);
            boolean matchOneOrMoreChars = method3(text, pattern, textIndex + 1, patternIndex);
            return matchZeroChars || matchOneOrMoreChars;
        }

        return false;
    }

    /**
     * Recursive wildcard matching with memoization.
     *
     * Memo table encoding:
     * - 0: uncomputed
     * - 1: false
     * - 2: true
     *
     * @param text          the input text
     * @param pattern       the pattern containing wildcards
     * @param textIndex     current index in text
     * @param patternIndex  current index in pattern
     * @param memo          memoization table
     * @return true if the pattern matches the text from the given indices, false otherwise
     */
    public static boolean method3(String text, String pattern, int textIndex, int patternIndex, int[][] memo) {
        int textLength = text.length();
        int patternLength = pattern.length();

        if (textIndex == textLength && patternIndex == patternLength) {
            return true;
        }

        if (textIndex != textLength && patternIndex == patternLength) {
            return false;
        }

        if (textIndex == textLength) {
            return isAllStars(pattern, patternIndex);
        }

        if (memo[textIndex][patternIndex] != 0) {
            return memo[textIndex][patternIndex] == 2;
        }

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patternIndex);

        boolean result;
        if (patternChar == textChar || patternChar == SINGLE_WILDCARD) {
            result = method3(text, pattern, textIndex + 1, patternIndex + 1, memo);
        } else if (patternChar == MULTI_WILDCARD) {
            boolean matchZeroChars = method3(text, pattern, textIndex, patternIndex + 1, memo);
            boolean matchOneOrMoreChars = method3(text, pattern, textIndex + 1, patternIndex, memo);
            result = matchZeroChars || matchOneOrMoreChars;
        } else {
            result = false;
        }

        memo[textIndex][patternIndex] = result ? 2 : 1;
        return result;
    }

    /**
     * Bottom-up dynamic programming wildcard matching.
     *
     * @param text    the input text
     * @param pattern the pattern containing wildcards
     * @return true if the pattern matches the entire text, false otherwise
     */
    static boolean method4(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];
        dp[textLength][patternLength] = true;

        for (int i = textLength; i >= 0; i--) {
            for (int j = patternLength - 1; j >= 0; j--) {
                char patternChar = pattern.charAt(j);

                if (i == textLength) {
                    dp[i][j] = patternChar == MULTI_WILDCARD && dp[i][j + 1];
                    continue;
                }

                char textChar = text.charAt(i);

                if (patternChar == textChar || patternChar == SINGLE_WILDCARD) {
                    dp[i][j] = dp[i + 1][j + 1];
                } else if (patternChar == MULTI_WILDCARD) {
                    boolean matchZeroChars = dp[i][j + 1];
                    boolean matchOneOrMoreChars = dp[i + 1][j];
                    dp[i][j] = matchZeroChars || matchOneOrMoreChars;
                } else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[0][0];
    }

    /**
     * Checks if the substring of pattern starting at startIndex consists only of '*'.
     *
     * @param pattern    the pattern string
     * @param startIndex starting index to check from
     * @return true if all remaining characters are '*', false otherwise
     */
    private static boolean isAllStars(String pattern, int startIndex) {
        for (int i = startIndex; i < pattern.length(); i++) {
            if (pattern.charAt(i) != MULTI_WILDCARD) {
                return false;
            }
        }
        return true;
    }
}