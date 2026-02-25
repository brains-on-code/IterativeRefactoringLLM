package com.thealgorithms.dynamicprogramming;

public final class Class1 {
    private Class1() {
    }

    public static boolean isMatchRecursiveSubstring(String text, String pattern) {
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
            result = isMatchRecursiveSubstring(remainingText, remainingPattern);
        } else if (patternChar == '*') {
            boolean matchZeroChars = isMatchRecursiveSubstring(text, remainingPattern);
            boolean matchOneOrMoreChars = isMatchRecursiveSubstring(remainingText, pattern);
            result = matchZeroChars || matchOneOrMoreChars;
        } else {
            result = false;
        }
        return result;
    }

    static boolean isMatchRecursiveIndices(String text, String pattern, int textIndex, int patternIndex) {
        if (text.length() == textIndex && pattern.length() == patternIndex) {
            return true;
        }
        if (text.length() != textIndex && pattern.length() == patternIndex) {
            return false;
        }
        if (text.length() == textIndex && pattern.length() != patternIndex) {
            for (int i = patternIndex; i < pattern.length(); i++) {
                if (pattern.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patternIndex);

        boolean result;
        if (textChar == patternChar || patternChar == '?') {
            result = isMatchRecursiveIndices(text, pattern, textIndex + 1, patternIndex + 1);
        } else if (patternChar == '*') {
            boolean matchZeroChars = isMatchRecursiveIndices(text, pattern, textIndex, patternIndex + 1);
            boolean matchOneOrMoreChars = isMatchRecursiveIndices(text, pattern, textIndex + 1, patternIndex);
            result = matchZeroChars || matchOneOrMoreChars;
        } else {
            result = false;
        }
        return result;
    }

    public static boolean isMatchRecursiveMemo(String text, String pattern, int textIndex, int patternIndex, int[][] memo) {
        if (text.length() == textIndex && pattern.length() == patternIndex) {
            return true;
        }
        if (text.length() != textIndex && pattern.length() == patternIndex) {
            return false;
        }
        if (text.length() == textIndex && pattern.length() != patternIndex) {
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
        if (textChar == patternChar || patternChar == '?') {
            result = isMatchRecursiveMemo(text, pattern, textIndex + 1, patternIndex + 1, memo);
        } else if (patternChar == '*') {
            boolean matchZeroChars = isMatchRecursiveMemo(text, pattern, textIndex, patternIndex + 1, memo);
            boolean matchOneOrMoreChars = isMatchRecursiveMemo(text, pattern, textIndex + 1, patternIndex, memo);
            result = matchZeroChars || matchOneOrMoreChars;
        } else {
            result = false;
        }
        memo[textIndex][patternIndex] = result ? 2 : 1;
        return result;
    }

    static boolean isMatchBottomUp(String text, String pattern) {
        boolean[][] dp = new boolean[text.length() + 1][pattern.length() + 1];
        dp[text.length()][pattern.length()] = true;

        for (int textIndex = text.length(); textIndex >= 0; textIndex--) {
            for (int patternIndex = pattern.length() - 1; patternIndex >= 0; patternIndex--) {
                if (textIndex == text.length()) {
                    if (pattern.charAt(patternIndex) == '*') {
                        dp[textIndex][patternIndex] = dp[textIndex][patternIndex + 1];
                    } else {
                        dp[textIndex][patternIndex] = false;
                    }
                } else {
                    char textChar = text.charAt(textIndex);
                    char patternChar = pattern.charAt(patternIndex);

                    boolean result;
                    if (textChar == patternChar || patternChar == '?') {
                        result = dp[textIndex + 1][patternIndex + 1];
                    } else if (patternChar == '*') {
                        boolean matchZeroChars = dp[textIndex][patternIndex + 1];
                        boolean matchOneOrMoreChars = dp[textIndex + 1][patternIndex];
                        result = matchZeroChars || matchOneOrMoreChars;
                    } else {
                        result = false;
                    }
                    dp[textIndex][patternIndex] = result;
                }
            }
        }
        return dp[0][0];
    }
}