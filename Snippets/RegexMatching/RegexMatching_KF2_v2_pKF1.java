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

        char currentTextChar = text.charAt(0);
        char currentPatternChar = pattern.charAt(0);

        String remainingText = text.substring(1);
        String remainingPattern = pattern.substring(1);

        boolean isMatch;
        if (currentTextChar == currentPatternChar || currentPatternChar == '?') {
            isMatch = isMatchRecursive(remainingText, remainingPattern);
        } else if (currentPatternChar == '*') {
            boolean matchEmpty = isMatchRecursive(text, remainingPattern);
            boolean matchMultiple = isMatchRecursive(remainingText, pattern);
            isMatch = matchEmpty || matchMultiple;
        } else {
            isMatch = false;
        }
        return isMatch;
    }

    static boolean isMatchRecursive(String text, String pattern, int textIndex, int patternIndex) {
        if (text.length() == textIndex && pattern.length() == patternIndex) {
            return true;
        }
        if (text.length() != textIndex && pattern.length() == patternIndex) {
            return false;
        }
        if (text.length() == textIndex && pattern.length() != patternIndex) {
            for (int currentPatternIndex = patternIndex; currentPatternIndex < pattern.length(); currentPatternIndex++) {
                if (pattern.charAt(currentPatternIndex) != '*') {
                    return false;
                }
            }
            return true;
        }

        char currentTextChar = text.charAt(textIndex);
        char currentPatternChar = pattern.charAt(patternIndex);

        boolean isMatch;
        if (currentTextChar == currentPatternChar || currentPatternChar == '?') {
            isMatch = isMatchRecursive(text, pattern, textIndex + 1, patternIndex + 1);
        } else if (currentPatternChar == '*') {
            boolean matchEmpty = isMatchRecursive(text, pattern, textIndex, patternIndex + 1);
            boolean matchMultiple = isMatchRecursive(text, pattern, textIndex + 1, patternIndex);
            isMatch = matchEmpty || matchMultiple;
        } else {
            isMatch = false;
        }
        return isMatch;
    }

    public static boolean isMatchRecursiveMemo(String text, String pattern, int textIndex, int patternIndex, int[][] memo) {
        if (text.length() == textIndex && pattern.length() == patternIndex) {
            return true;
        }
        if (text.length() != textIndex && pattern.length() == patternIndex) {
            return false;
        }
        if (text.length() == textIndex && pattern.length() != patternIndex) {
            for (int currentPatternIndex = patternIndex; currentPatternIndex < pattern.length(); currentPatternIndex++) {
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
            boolean matchEmpty = isMatchRecursiveMemo(text, pattern, textIndex, patternIndex + 1, memo);
            boolean matchMultiple = isMatchRecursiveMemo(text, pattern, textIndex + 1, patternIndex, memo);
            isMatch = matchEmpty || matchMultiple;
        } else {
            isMatch = false;
        }

        memo[textIndex][patternIndex] = isMatch ? 2 : 1;
        return isMatch;
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
                    char currentTextChar = text.charAt(textIndex);
                    char currentPatternChar = pattern.charAt(patternIndex);

                    boolean isMatch;
                    if (currentTextChar == currentPatternChar || currentPatternChar == '?') {
                        isMatch = dp[textIndex + 1][patternIndex + 1];
                    } else if (currentPatternChar == '*') {
                        boolean matchEmpty = dp[textIndex][patternIndex + 1];
                        boolean matchMultiple = dp[textIndex + 1][patternIndex];
                        isMatch = matchEmpty || matchMultiple;
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