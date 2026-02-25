package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for wildcard pattern matching.
 *
 * Supported wildcards in the pattern:
 * - '?' matches exactly one character
 * - '*' matches zero or more characters
 */
public final class WildcardMatcher {

    private static final char SINGLE_WILDCARD = '?';
    private static final char MULTI_WILDCARD = '*';

    private static final int MEMO_UNCOMPUTED = 0;
    private static final int MEMO_FALSE = 1;
    private static final int MEMO_TRUE = 2;

    private WildcardMatcher() {
        // Utility class; prevent instantiation
    }

    /**
     * Recursive wildcard matching using substrings.
     *
     * @param text    the input text
     * @param pattern the pattern containing wildcards
     * @return true if the pattern matches the entire text, false otherwise
     */
    public static boolean matchesRecursive(String text, String pattern) {
        if (text.isEmpty() && pattern.isEmpty()) {
            return true;
        }

        if (pattern.isEmpty()) {
            return false;
        }

        if (text.isEmpty()) {
            return isAllStars(pattern, 0);
        }

        char textChar = text.charAt(0);
        char patternChar = pattern.charAt(0);

        String remainingText = text.substring(1);
        String remainingPattern = pattern.substring(1);

        if (charsMatch(textChar, patternChar)) {
            return matchesRecursive(remainingText, remainingPattern);
        }

        if (patternChar == MULTI_WILDCARD) {
            return matchesRecursive(text, remainingPattern)
                || matchesRecursive(remainingText, pattern);
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
    static boolean matchesRecursive(String text, String pattern, int textIndex, int patternIndex) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean textExhausted = textIndex == textLength;
        boolean patternExhausted = patternIndex == patternLength;

        if (textExhausted && patternExhausted) {
            return true;
        }

        if (patternExhausted) {
            return false;
        }

        if (textExhausted) {
            return isAllStars(pattern, patternIndex);
        }

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patternIndex);

        if (charsMatch(textChar, patternChar)) {
            return matchesRecursive(text, pattern, textIndex + 1, patternIndex + 1);
        }

        if (patternChar == MULTI_WILDCARD) {
            return matchesRecursive(text, pattern, textIndex, patternIndex + 1)
                || matchesRecursive(text, pattern, textIndex + 1, patternIndex);
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
    public static boolean matchesRecursiveMemo(
            String text,
            String pattern,
            int textIndex,
            int patternIndex,
            int[][] memo
    ) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean textExhausted = textIndex == textLength;
        boolean patternExhausted = patternIndex == patternLength;

        if (textExhausted && patternExhausted) {
            return true;
        }

        if (patternExhausted) {
            return false;
        }

        if (textExhausted) {
            return isAllStars(pattern, patternIndex);
        }

        if (memo[textIndex][patternIndex] != MEMO_UNCOMPUTED) {
            return memo[textIndex][patternIndex] == MEMO_TRUE;
        }

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patternIndex);

        boolean result;
        if (charsMatch(textChar, patternChar)) {
            result = matchesRecursiveMemo(text, pattern, textIndex + 1, patternIndex + 1, memo);
        } else if (patternChar == MULTI_WILDCARD) {
            result =
                matchesRecursiveMemo(text, pattern, textIndex, patternIndex + 1, memo)
                    || matchesRecursiveMemo(text, pattern, textIndex + 1, patternIndex, memo);
        } else {
            result = false;
        }

        memo[textIndex][patternIndex] = result ? MEMO_TRUE : MEMO_FALSE;
        return result;
    }

    /**
     * Bottom-up dynamic programming wildcard matching.
     *
     * @param text    the input text
     * @param pattern the pattern containing wildcards
     * @return true if the pattern matches the entire text, false otherwise
     */
    static boolean matchesBottomUp(String text, String pattern) {
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

                if (charsMatch(textChar, patternChar)) {
                    dp[i][j] = dp[i + 1][j + 1];
                } else if (patternChar == MULTI_WILDCARD) {
                    dp[i][j] = dp[i][j + 1] || dp[i + 1][j];
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

    /**
     * Returns true if the pattern character matches the text character,
     * considering the single-character wildcard.
     */
    private static boolean charsMatch(char textChar, char patternChar) {
        return patternChar == textChar || patternChar == SINGLE_WILDCARD;
    }
}