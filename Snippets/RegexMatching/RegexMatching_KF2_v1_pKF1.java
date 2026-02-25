package com.thealgorithms.dynamicprogramming;

public final class RegexMatching {

    private RegexMatching() {}

    public static boolean regexRecursion(String text, String pattern) {
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
            result = regexRecursion(remainingText, remainingPattern);
        } else if (patternChar == '*') {
            boolean matchEmpty = regexRecursion(text, remainingPattern);
            boolean matchMultiple = regexRecursion(remainingText, pattern);
            result = matchEmpty || matchMultiple;
        } else {
            result = false;
        }
        return result;
    }

    static boolean regexRecursion(String text, String pattern, int textIndex, int patternIndex) {
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
            result = regexRecursion(text, pattern, textIndex + 1, patternIndex + 1);
        } else if (patternChar == '*') {
            boolean matchEmpty = regexRecursion(text, pattern, textIndex, patternIndex + 1);
            boolean matchMultiple = regexRecursion(text, pattern, textIndex + 1, patternIndex);
            result = matchEmpty || matchMultiple;
        } else {
            result = false;
        }
        return result;
    }

    public static boolean regexRecursion(String text, String pattern, int textIndex, int patternIndex, int[][] memo) {
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
            result = regexRecursion(text, pattern, textIndex + 1, patternIndex + 1, memo);
        } else if (patternChar == '*') {
            boolean matchEmpty = regexRecursion(text, pattern, textIndex, patternIndex + 1, memo);
            boolean matchMultiple = regexRecursion(text, pattern, textIndex + 1, patternIndex, memo);
            result = matchEmpty || matchMultiple;
        } else {
            result = false;
        }

        memo[textIndex][patternIndex] = result ? 2 : 1;
        return result;
    }

    static boolean regexBU(String text, String pattern) {
        boolean[][] dp = new boolean[text.length() + 1][pattern.length() + 1];
        dp[text.length()][pattern.length()] = true;

        for (int row = text.length(); row >= 0; row--) {
            for (int col = pattern.length() - 1; col >= 0; col--) {
                if (row == text.length()) {
                    if (pattern.charAt(col) == '*') {
                        dp[row][col] = dp[row][col + 1];
                    } else {
                        dp[row][col] = false;
                    }
                } else {
                    char textChar = text.charAt(row);
                    char patternChar = pattern.charAt(col);

                    boolean result;
                    if (textChar == patternChar || patternChar == '?') {
                        result = dp[row + 1][col + 1];
                    } else if (patternChar == '*') {
                        boolean matchEmpty = dp[row][col + 1];
                        boolean matchMultiple = dp[row + 1][col];
                        result = matchEmpty || matchMultiple;
                    } else {
                        result = false;
                    }
                    dp[row][col] = result;
                }
            }
        }
        return dp[0][0];
    }
}