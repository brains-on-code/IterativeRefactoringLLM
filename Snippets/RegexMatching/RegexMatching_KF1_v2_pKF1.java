package com.thealgorithms.dynamicprogramming;

public final class WildcardMatcher {

    private WildcardMatcher() {
    }

    public static boolean isMatchRecursiveSubstring(String text, String pattern) {
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
            isMatch = isMatchRecursiveSubstring(remainingText, remainingPattern);
        } else if (currentPatternChar == '*') {
            boolean matchZeroChars = isMatchRecursiveSubstring(text, remainingPattern);
            boolean matchOneOrMoreChars = isMatchRecursiveSubstring(remainingText, pattern);
            isMatch = matchZeroChars || matchOneOrMoreChars;
        } else {
            isMatch = false;
        }
        return isMatch;
    }

    static boolean isMatchRecursiveIndices(String text, String pattern, int textIndex, int patternIndex) {
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
            isMatch = isMatchRecursiveIndices(text, pattern, textIndex + 1, patternIndex + 1);
        } else if (currentPatternChar == '*') {
            boolean matchZeroChars = isMatchRecursiveIndices(text, pattern, textIndex, patternIndex + 1);
            boolean matchOneOrMoreChars = isMatchRecursiveIndices(text, pattern, textIndex + 1, patternIndex);
            isMatch = matchZeroChars || matchOneOrMoreChars;
        } else {
            isMatch = false;
        }
        return isMatch;
    }

    public static boolean isMatchRecursiveMemo(
            String text, String pattern, int textIndex, int patternIndex, int[][] memoTable) {

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
        if (memoTable[textIndex][patternIndex] != 0) {
            return memoTable[textIndex][patternIndex] != 1;
        }

        char currentTextChar = text.charAt(textIndex);
        char currentPatternChar = pattern.charAt(patternIndex);

        boolean isMatch;
        if (currentTextChar == currentPatternChar || currentPatternChar == '?') {
            isMatch = isMatchRecursiveMemo(text, pattern, textIndex + 1, patternIndex + 1, memoTable);
        } else if (currentPatternChar == '*') {
            boolean matchZeroChars =
                    isMatchRecursiveMemo(text, pattern, textIndex, patternIndex + 1, memoTable);
            boolean matchOneOrMoreChars =
                    isMatchRecursiveMemo(text, pattern, textIndex + 1, patternIndex, memoTable);
            isMatch = matchZeroChars || matchOneOrMoreChars;
        } else {
            isMatch = false;
        }
        memoTable[textIndex][patternIndex] = isMatch ? 2 : 1;
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