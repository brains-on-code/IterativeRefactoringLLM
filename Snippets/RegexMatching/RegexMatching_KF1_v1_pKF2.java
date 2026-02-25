package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for wildcard pattern matching.
 *
 * Supported wildcards in the pattern:
 * - '?' matches exactly one character.
 * - '*' matches zero or more characters.
 */
public final class Class1 {

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
        if (text.length() == 0 && pattern.length() == 0) {
            return true;
        }
        if (text.length() != 0 && pattern.length() == 0) {
            return false;
        }
        if (text.length() == 0 && pattern.length() != 0) {
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

        boolean result;
        if (textChar == patternChar || patternChar == '?') {
            // Match current character and move both pointers.
            result = method3(remainingText, remainingPattern);
        } else if (patternChar == '*') {
            // '*' can match zero characters (move pattern) or
            // one character (move text).
            boolean matchZero = method3(text, remainingPattern);
            boolean matchOneOrMore = method3(remainingText, pattern);
            result = matchZero || matchOneOrMore;
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Recursive wildcard matching using indices (no substring allocations).
     *
     * @param text       the input text
     * @param pattern    the pattern containing normal characters, '?' and '*'
     * @param textIndex  current index in text
     * @param patIndex   current index in pattern
     * @return true if the pattern matches the text from the given indices, false otherwise
     */
    static boolean method3(String text, String pattern, int textIndex, int patIndex) {
        if (text.length() == textIndex && pattern.length() == patIndex) {
            return true;
        }
        if (text.length() != textIndex && pattern.length() == patIndex) {
            return false;
        }
        if (text.length() == textIndex && pattern.length() != patIndex) {
            for (int i = patIndex; i < pattern.length(); i++) {
                if (pattern.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patIndex);

        boolean result;
        if (textChar == patternChar || patternChar == '?') {
            // Match current character and move both pointers.
            result = method3(text, pattern, textIndex + 1, patIndex + 1);
        } else if (patternChar == '*') {
            // '*' can match zero characters (move pattern) or
            // one character (move text).
            boolean matchZero = method3(text, pattern, textIndex, patIndex + 1);
            boolean matchOneOrMore = method3(text, pattern, textIndex + 1, patIndex);
            result = matchZero || matchOneOrMore;
        } else {
            result = false;
        }
        return result;
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
     * @param pattern    the pattern containing normal characters, '?' and '*'
     * @param textIndex  current index in text
     * @param patIndex   current index in pattern
     * @param memo       memoization table
     * @return true if the pattern matches the text from the given indices, false otherwise
     */
    public static boolean method3(String text, String pattern, int textIndex, int patIndex, int[][] memo) {
        if (text.length() == textIndex && pattern.length() == patIndex) {
            return true;
        }
        if (text.length() != textIndex && pattern.length() == patIndex) {
            return false;
        }
        if (text.length() == textIndex && pattern.length() != patIndex) {
            for (int i = patIndex; i < pattern.length(); i++) {
                if (pattern.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        if (memo[textIndex][patIndex] != 0) {
            return memo[textIndex][patIndex] != 1;
        }

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patIndex);

        boolean result;
        if (textChar == patternChar || patternChar == '?') {
            // Match current character and move both pointers.
            result = method3(text, pattern, textIndex + 1, patIndex + 1, memo);
        } else if (patternChar == '*') {
            // '*' can match zero characters (move pattern) or
            // one character (move text).
            boolean matchZero = method3(text, pattern, textIndex, patIndex + 1, memo);
            boolean matchOneOrMore = method3(text, pattern, textIndex + 1, patIndex, memo);
            result = matchZero || matchOneOrMore;
        } else {
            result = false;
        }

        memo[textIndex][patIndex] = result ? 2 : 1;
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
        boolean[][] dp = new boolean[text.length() + 1][pattern.length() + 1];

        // Empty pattern matches empty text.
        dp[text.length()][pattern.length()] = true;

        for (int i = text.length(); i >= 0; i--) {
            for (int j = pattern.length() - 1; j >= 0; j--) {
                if (i == text.length()) {
                    // Text is exhausted: pattern must be all '*' from here.
                    if (pattern.charAt(j) == '*') {
                        dp[i][j] = dp[i][j + 1];
                    } else {
                        dp[i][j] = false;
                    }
                } else {
                    char textChar = text.charAt(i);
                    char patternChar = pattern.charAt(j);

                    boolean result;
                    if (textChar == patternChar || patternChar == '?') {
                        // Match current character and move both pointers.
                        result = dp[i + 1][j + 1];
                    } else if (patternChar == '*') {
                        // '*' can match zero characters (move pattern) or
                        // one character (move text).
                        boolean matchZero = dp[i][j + 1];
                        boolean matchOneOrMore = dp[i + 1][j];
                        result = matchZero || matchOneOrMore;
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