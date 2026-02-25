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
            for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
                if (pattern.charAt(patternIndex) != '*') {
                    return false;
                }
            }
            return true;
        }

        char currentTextChar = text.charAt(0);
        char currentPatternChar = pattern.charAt(0);

        String remainingText = text.substring(1);
        String remainingPattern = pattern.substring(1);

        if (currentTextChar == currentPatternChar || currentPatternChar == '?') {
            return isMatchRecursiveSubstring(remainingText, remainingPattern);
        } else if (currentPatternChar == '*') {
            boolean matchZeroChars = isMatchRecursiveSubstring(text, remainingPattern);
            boolean matchOneOrMoreChars = isMatchRecursiveSubstring(remainingText, pattern);
            return matchZeroChars || matchOneOrMoreChars;
        }
        return false;
    }

    static boolean isMatchRecursiveIndices(String text, String pattern, int textIndex, int patternIndex) {
        int textLength = text.length();
        int patternLength = pattern.length();

        if (textLength == textIndex && patternLength == patternIndex) {
            return true;
        }
        if (textLength != textIndex && patternLength == patternIndex) {
            return false;
        }
        if (textLength == textIndex) {
            for (int currentPatternIndex = patternIndex; currentPatternIndex < patternLength; currentPatternIndex++) {
                if (pattern.charAt(currentPatternIndex) != '*') {
                    return false;
                }
            }
            return true;
        }

        char currentTextChar = text.charAt(textIndex);
        char currentPatternChar = pattern.charAt(patternIndex);

        if (currentTextChar == currentPatternChar || currentPatternChar == '?') {
            return isMatchRecursiveIndices(text, pattern, textIndex + 1, patternIndex + 1);
        } else if (currentPatternChar == '*') {
            boolean matchZeroChars = isMatchRecursiveIndices(text, pattern, textIndex, patternIndex + 1);
            boolean matchOneOrMoreChars = isMatchRecursiveIndices(text, pattern, textIndex + 1, patternIndex);
            return matchZeroChars || matchOneOrMoreChars;
        }
        return false;
    }

    public static boolean isMatchRecursiveMemo(
            String text, String pattern, int textIndex, int patternIndex, int[][] memo) {

        int textLength = text.length();
        int patternLength = pattern.length();

        if (textLength == textIndex && patternLength == patternIndex) {
            return true;
        }
        if (textLength != textIndex && patternLength == patternIndex) {
            return false;
        }
        if (textLength == textIndex) {
            for (int currentPatternIndex = patternIndex; currentPatternIndex < patternLength; currentPatternIndex++) {
                if (pattern.charAt(currentPatternIndex) != '*') {
                    return false;
                }
            }
            return true;
        }
        if (memo[textIndex][patternIndex] != 0) {
            return memo[textIndex][patternIndex] != 1;
        }

        char currentTextChar = text.charAt(textIndex);
        char currentPatternChar = pattern.charAt(patternIndex);

        boolean isMatch;
        if (currentTextChar == currentPatternChar || currentPatternChar == '?') {
            isMatch = isMatchRecursiveMemo(text, pattern, textIndex + 1, patternIndex + 1, memo);
        } else if (currentPatternChar == '*') {
            boolean matchZeroChars =
                    isMatchRecursiveMemo(text, pattern, textIndex, patternIndex + 1, memo);
            boolean matchOneOrMoreChars =
                    isMatchRecursiveMemo(text, pattern, textIndex + 1, patternIndex, memo);
            isMatch = matchZeroChars || matchOneOrMoreChars;
        } else {
            isMatch = false;
        }
        memo[textIndex][patternIndex] = isMatch ? 2 : 1;
        return isMatch;
    }

    static boolean isMatchBottomUp(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] dp = new boolean[textLength + 1][patternLength + 1];
        dp[textLength][patternLength] = true;

        for (int textIndex = textLength; textIndex >= 0; textIndex--) {
            for (int patternIndex = patternLength - 1; patternIndex >= 0; patternIndex--) {
                if (textIndex == textLength) {
                    dp[textIndex][patternIndex] =
                            pattern.charAt(patternIndex) == '*' && dp[textIndex][patternIndex + 1];
                } else {
                    char currentTextChar = text.charAt(textIndex);
                    char currentPatternChar = pattern.charAt(patternIndex);

                    boolean isMatch;
                    if (currentTextChar == currentPatternChar || currentPatternChar == '?') {
                        isMatch = dp[textIndex + 1][patternIndex + 1];
                    } else if (currentPatternChar == '*') {
                        boolean matchZeroChars = dp[textIndex][patternIndex + 1];
                        boolean matchOneOrMoreChars = dp[textIndex + 1][patternIndex];
                        isMatch = matchZeroChars || matchOneOrMoreChars;
                    } else {
                        isMatch = false;
                    }
                    dp[textIndex][patternIndex] = isMatch;
                }
            }
        }
        return dp[0][0];
    }
}