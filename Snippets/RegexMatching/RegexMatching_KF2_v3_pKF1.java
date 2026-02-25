package com.thealgorithms.dynamicprogramming;

public final class RegexMatching {

    private RegexMatching() {}

    public static boolean isMatchRecursive(String text, String pattern) {
        if (text.length() == 0 && pattern.length() == 0) {
            return true;
        }
        if (text.length() != 0 && pattern.length() == 0) {
            return false;
        }
        if (text.length() == 0 && pattern.length() != 0) {
            for (int patternIndex = 0; patternIndex < pattern.length(); patternIndex++) {
                if (pattern.charAt(patternIndex) != '*') {
                    return false;
                }
            }
            return true;
        }

        char textChar = text.charAt(0);
        char patternChar = pattern.charAt(0);

        String remainingText = text.substring(1);
        String remainingPattern = pattern.substring(1);

        boolean matches;
        if (textChar == patternChar || patternChar == '?') {
            matches = isMatchRecursive(remainingText, remainingPattern);
        } else if (patternChar == '*') {
            boolean matchesEmpty = isMatchRecursive(text, remainingPattern);
            boolean matchesMultiple = isMatchRecursive(remainingText, pattern);
            matches = matchesEmpty || matchesMultiple;
        } else {
            matches = false;
        }
        return matches;
    }

    static boolean isMatchRecursive(String text, String pattern, int textIndex, int patternIndex) {
        int textLength = text.length();
        int patternLength = pattern.length();

        if (textLength == textIndex && patternLength == patternIndex) {
            return true;
        }
        if (textLength != textIndex && patternLength == patternIndex) {
            return false;
        }
        if (textLength == textIndex && patternLength != patternIndex) {
            for (int currentPatternIndex = patternIndex; currentPatternIndex < patternLength; currentPatternIndex++) {
                if (pattern.charAt(currentPatternIndex) != '*') {
                    return false;
                }
            }
            return true;
        }

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patternIndex);

        boolean matches;
        if (textChar == patternChar || patternChar == '?') {
            matches = isMatchRecursive(text, pattern, textIndex + 1, patternIndex + 1);
        } else if (patternChar == '*') {
            boolean matchesEmpty = isMatchRecursive(text, pattern, textIndex, patternIndex + 1);
            boolean matchesMultiple = isMatchRecursive(text, pattern, textIndex + 1, patternIndex);
            matches = matchesEmpty || matchesMultiple;
        } else {
            matches = false;
        }
        return matches;
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
        if (textLength == textIndex && patternLength != patternIndex) {
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

        char textChar = text.charAt(textIndex);
        char patternChar = pattern.charAt(patternIndex);

        boolean matches;
        if (textChar == patternChar || patternChar == '?') {
            matches = isMatchRecursiveMemo(text, pattern, textIndex + 1, patternIndex + 1, memo);
        } else if (patternChar == '*') {
            boolean matchesEmpty = isMatchRecursiveMemo(text, pattern, textIndex, patternIndex + 1, memo);
            boolean matchesMultiple = isMatchRecursiveMemo(text, pattern, textIndex + 1, patternIndex, memo);
            matches = matchesEmpty || matchesMultiple;
        } else {
            matches = false;
        }

        memo[textIndex][patternIndex] = matches ? 2 : 1;
        return matches;
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
                    char textChar = text.charAt(textIndex);
                    char patternChar = pattern.charAt(patternIndex);

                    boolean matches;
                    if (textChar == patternChar || patternChar == '?') {
                        matches = dp[textIndex + 1][patternIndex + 1];
                    } else if (patternChar == '*') {
                        boolean matchesEmpty = dp[textIndex][patternIndex + 1];
                        boolean matchesMultiple = dp[textIndex + 1][patternIndex];
                        matches = matchesEmpty || matchesMultiple;
                    } else {
                        matches = false;
                    }
                    dp[textIndex][patternIndex] = matches;
                }
            }
        }
        return dp[0][0];
    }
}