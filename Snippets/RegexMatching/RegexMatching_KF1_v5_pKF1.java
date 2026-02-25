package com.thealgorithms.dynamicprogramming;

public final class WildcardMatcher {

    private WildcardMatcher() {}

    public static boolean isMatchRecursiveSubstring(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        if (textLength == 0 && patternLength == 0) {
            return true;
        }
        if (textLength != 0 && patternLength == 0) {
            return false;
        }
        if (textLength == 0) {
            for (int patternPos = 0; patternPos < patternLength; patternPos++) {
                if (pattern.charAt(patternPos) != '*') {
                    return false;
                }
            }
            return true;
        }

        char textChar = text.charAt(0);
        char patternChar = pattern.charAt(0);

        String remainingText = text.substring(1);
        String remainingPattern = pattern.substring(1);

        if (textChar == patternChar || patternChar == '?') {
            return isMatchRecursiveSubstring(remainingText, remainingPattern);
        } else if (patternChar == '*') {
            boolean matchZeroChars = isMatchRecursiveSubstring(text, remainingPattern);
            boolean matchOneOrMoreChars = isMatchRecursiveSubstring(remainingText, pattern);
            return matchZeroChars || matchOneOrMoreChars;
        }
        return false;
    }

    static boolean isMatchRecursiveIndices(String text, String pattern, int textPos, int patternPos) {
        int textLength = text.length();
        int patternLength = pattern.length();

        if (textLength == textPos && patternLength == patternPos) {
            return true;
        }
        if (textLength != textPos && patternLength == patternPos) {
            return false;
        }
        if (textLength == textPos) {
            for (int currentPatternPos = patternPos; currentPatternPos < patternLength; currentPatternPos++) {
                if (pattern.charAt(currentPatternPos) != '*') {
                    return false;
                }
            }
            return true;
        }

        char textChar = text.charAt(textPos);
        char patternChar = pattern.charAt(patternPos);

        if (textChar == patternChar || patternChar == '?') {
            return isMatchRecursiveIndices(text, pattern, textPos + 1, patternPos + 1);
        } else if (patternChar == '*') {
            boolean matchZeroChars = isMatchRecursiveIndices(text, pattern, textPos, patternPos + 1);
            boolean matchOneOrMoreChars = isMatchRecursiveIndices(text, pattern, textPos + 1, patternPos);
            return matchZeroChars || matchOneOrMoreChars;
        }
        return false;
    }

    public static boolean isMatchRecursiveMemo(
            String text, String pattern, int textPos, int patternPos, int[][] memo) {

        int textLength = text.length();
        int patternLength = pattern.length();

        if (textLength == textPos && patternLength == patternPos) {
            return true;
        }
        if (textLength != textPos && patternLength == patternPos) {
            return false;
        }
        if (textLength == textPos) {
            for (int currentPatternPos = patternPos; currentPatternPos < patternLength; currentPatternPos++) {
                if (pattern.charAt(currentPatternPos) != '*') {
                    return false;
                }
            }
            return true;
        }
        if (memo[textPos][patternPos] != 0) {
            return memo[textPos][patternPos] != 1;
        }

        char textChar = text.charAt(textPos);
        char patternChar = pattern.charAt(patternPos);

        boolean isMatch;
        if (textChar == patternChar || patternChar == '?') {
            isMatch = isMatchRecursiveMemo(text, pattern, textPos + 1, patternPos + 1, memo);
        } else if (patternChar == '*') {
            boolean matchZeroChars =
                    isMatchRecursiveMemo(text, pattern, textPos, patternPos + 1, memo);
            boolean matchOneOrMoreChars =
                    isMatchRecursiveMemo(text, pattern, textPos + 1, patternPos, memo);
            isMatch = matchZeroChars || matchOneOrMoreChars;
        } else {
            isMatch = false;
        }
        memo[textPos][patternPos] = isMatch ? 2 : 1;
        return isMatch;
    }

    static boolean isMatchBottomUp(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];
        dp[textLength][patternLength] = true;

        for (int textPos = textLength; textPos >= 0; textPos--) {
            for (int patternPos = patternLength - 1; patternPos >= 0; patternPos--) {
                if (textPos == textLength) {
                    dp[textPos][patternPos] =
                            pattern.charAt(patternPos) == '*' && dp[textPos][patternPos + 1];
                } else {
                    char textChar = text.charAt(textPos);
                    char patternChar = pattern.charAt(patternPos);

                    boolean isMatch;
                    if (textChar == patternChar || patternChar == '?') {
                        isMatch = dp[textPos + 1][patternPos + 1];
                    } else if (patternChar == '*') {
                        boolean matchZeroChars = dp[textPos][patternPos + 1];
                        boolean matchOneOrMoreChars = dp[textPos + 1][patternPos];
                        isMatch = matchZeroChars || matchOneOrMoreChars;
                    } else {
                        isMatch = false;
                    }
                    dp[textPos][patternPos] = isMatch;
                }
            }
        }
        return dp[0][0];
    }
}