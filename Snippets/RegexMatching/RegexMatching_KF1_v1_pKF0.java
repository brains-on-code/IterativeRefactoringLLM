package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for wildcard pattern matching.
 *
 * Supported wildcards in the pattern:
 * - '?' matches exactly one character
 * - '*' matches zero or more characters
 */
public final class Class1 {

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
            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        char textChar = text.charAt(0);
        char patternChar = pattern.charAt(0);

        String remainingText = text.substring(1);
        String remainingPattern = pattern.substring(1);

        if (patternChar == textChar || patternChar == '?') {
            return method3(remainingText, remainingPattern);
        } else if (patternChar == '*') {
            boolean matchZeroChars = method3(text, remainingPattern);
            boolean matchOneOrMoreChars = method3(remainingText, pattern);
            return matchZeroChars || matchOneOrMoreChars;
        }

        return false;
    }

    /**
     * Recursive wildcard matching using indices (no memoization).
     *
     * @param text       the input text
     * @param pattern    the pattern containing wildcards
     * @param textIndex  current index in text
     * @param patternIndex current index in pattern
     * @return true if the pattern matches the text from the given indices, false otherwise
     */
    static boolean method3(String text, String pattern, int textIndex, int patternIndex) {
        if (text.length() == textIndex && pattern.length() == patternIndex) {
            return true;
        }

        if (text.length() != textIndex && pattern.length() == patternIndex) {
            return false;
        }

        if (text.length() == textIndex) {
            for (int i = patternIndex; i < pattern.length(); i++) {
                if (pattern.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patternIndex);

        if (patternChar == textChar || patternChar == '?') {
            return method3(text, pattern, textIndex + 1, patternIndex + 1);
        } else if (patternChar == '*') {
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
     * @param text       the input text
     * @param pattern    the pattern containing wildcards
     * @param textIndex  current index in text
     * @param patternIndex current index in pattern
     * @param memo       memoization table
     * @return true if the pattern matches the text from the given indices, false otherwise
     */
    public static boolean method3(String text, String pattern, int textIndex, int patternIndex, int[][] memo) {
        if (text.length() == textIndex && pattern.length() == patternIndex) {
            return true;
        }

        if (text.length() != textIndex && pattern.length() == patternIndex) {
            return false;
        }

        if (text.length() == textIndex) {
            for (int i = patternIndex; i < pattern.length(); i++) {
                if (pattern.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        if (memo[textIndex][patternIndex] != 0) {
            return memo[textIndex][patternIndex] != 1;
        }

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patternIndex);

        boolean result;
        if (patternChar == textChar || patternChar == '?') {
            result = method3(text, pattern, textIndex + 1, patternIndex + 1, memo);
        } else if (patternChar == '*') {
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
                if (i == textLength) {
                    dp[i][j] = pattern.charAt(j) == '*' && dp[i][j + 1];
                } else {
                    char textChar = text.charAt(i);
                    char patternChar = pattern.charAt(j);

                    boolean result;
                    if (patternChar == textChar || patternChar == '?') {
                        result = dp[i + 1][j + 1];
                    } else if (patternChar == '*') {
                        boolean matchZeroChars = dp[i][j + 1];
                        boolean matchOneOrMoreChars = dp[i + 1][j];
                        result = matchZeroChars || matchOneOrMoreChars;
                    } else {
                        result = false;
                    }
                    dp[i][j] = result;
                }
            }
        }

        return dp[0][0];
    }
}