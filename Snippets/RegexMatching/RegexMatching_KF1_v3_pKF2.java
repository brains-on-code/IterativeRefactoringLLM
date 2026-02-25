package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for wildcard pattern matching.
 *
 * Supported wildcards in the pattern:
 * - '?' matches exactly one character.
 * - '*' matches zero or more characters.
 */
public final class Class1 {

    private static final int MEMO_UNCOMPUTED = 0;
    private static final int MEMO_FALSE = 1;
    private static final int MEMO_TRUE = 2;

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Recursive wildcard matching using substrings.
     *
     * @param text    the input text
     * @param pattern the pattern containing normal characters, '?' and '*'
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

        if (textChar == patternChar || patternChar == '?') {
            return method3(remainingText, remainingPattern);
        }
        if (patternChar == '*') {
            boolean matchZeroChars = method3(text, remainingPattern);
            boolean matchOneOrMoreChars = method3(remainingText, pattern);
            return matchZeroChars || matchOneOrMoreChars;
        }
        return false;
    }

    /**
     * Recursive wildcard matching using indices (no substring allocations).
     *
     * @param text      the input text
     * @param pattern   the pattern containing normal characters, '?' and '*'
     * @param textIndex current index in text
     * @param patIndex  current index in pattern
     * @return true if the pattern matches the text from the given indices, false otherwise
     */
    static boolean method3(String text, String pattern, int textIndex, int patIndex) {
        boolean isTextExhausted = textIndex == text.length();
        boolean isPatternExhausted = patIndex == pattern.length();

        if (isTextExhausted && isPatternExhausted) {
            return true;
        }
        if (!isTextExhausted && isPatternExhausted) {
            return false;
        }
        if (isTextExhausted) {
            return isAllStars(pattern, patIndex);
        }

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patIndex);

        if (textChar == patternChar || patternChar == '?') {
            return method3(text, pattern, textIndex + 1, patIndex + 1);
        }
        if (patternChar == '*') {
            boolean matchZeroChars = method3(text, pattern, textIndex, patIndex + 1);
            boolean matchOneOrMoreChars = method3(text, pattern, textIndex + 1, patIndex);
            return matchZeroChars || matchOneOrMoreChars;
        }
        return false;
    }

    /**
     * Recursive wildcard matching with memoization.
     *
     * Memo table encoding:
     * - MEMO_UNCOMPUTED: value not yet computed
     * - MEMO_FALSE: subproblem evaluates to false
     * - MEMO_TRUE: subproblem evaluates to true
     *
     * @param text      the input text
     * @param pattern   the pattern containing normal characters, '?' and '*'
     * @param textIndex current index in text
     * @param patIndex  current index in pattern
     * @param memo      memoization table
     * @return true if the pattern matches the text from the given indices, false otherwise
     */
    public static boolean method3(String text, String pattern, int textIndex, int patIndex, int[][] memo) {
        boolean isTextExhausted = textIndex == text.length();
        boolean isPatternExhausted = patIndex == pattern.length();

        if (isTextExhausted && isPatternExhausted) {
            return true;
        }
        if (!isTextExhausted && isPatternExhausted) {
            return false;
        }
        if (isTextExhausted) {
            return isAllStars(pattern, patIndex);
        }

        if (memo[textIndex][patIndex] != MEMO_UNCOMPUTED) {
            return memo[textIndex][patIndex] == MEMO_TRUE;
        }

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patIndex);

        boolean result;
        if (textChar == patternChar || patternChar == '?') {
            result = method3(text, pattern, textIndex + 1, patIndex + 1, memo);
        } else if (patternChar == '*') {
            boolean matchZeroChars = method3(text, pattern, textIndex, patIndex + 1, memo);
            boolean matchOneOrMoreChars = method3(text, pattern, textIndex + 1, patIndex, memo);
            result = matchZeroChars || matchOneOrMoreChars;
        } else {
            result = false;
        }

        memo[textIndex][patIndex] = result ? MEMO_TRUE : MEMO_FALSE;
        return result;
    }

    /**
     * Bottom-up dynamic programming wildcard matching.
     *
     * @param text    the input text
     * @param pattern the pattern containing normal characters, '?' and '*'
     * @return true if the pattern matches the entire text, false otherwise
     */
    static boolean method4(String text, String pattern) {
        int textLen = text.length();
        int patLen = pattern.length();
        boolean[][] dp = new boolean[textLen + 1][patLen + 1];

        // Empty pattern matches empty text.
        dp[textLen][patLen] = true;

        for (int i = textLen; i >= 0; i--) {
            for (int j = patLen - 1; j >= 0; j--) {
                if (i == textLen) {
                    // Text exhausted: remaining pattern must be all '*' to match.
                    dp[i][j] = pattern.charAt(j) == '*' && dp[i][j + 1];
                } else {
                    char textChar = text.charAt(i);
                    char patternChar = pattern.charAt(j);

                    boolean result;
                    if (textChar == patternChar || patternChar == '?') {
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

    /**
     * Returns true if the pattern consists only of '*' characters from the given index.
     *
     * @param pattern the pattern string
     * @param start   starting index in the pattern
     * @return true if all remaining characters are '*', false otherwise
     */
    private static boolean isAllStars(String pattern, int start) {
        for (int i = start; i < pattern.length(); i++) {
            if (pattern.charAt(i) != '*') {
                return false;
            }
        }
        return true;
    }
}