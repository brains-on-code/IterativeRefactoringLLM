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

        boolean isMatch;
        if (textChar == patternChar || patternChar == '?') {
            isMatch = isMatchRecursive(remainingText, remainingPattern);
        } else if (patternChar == '*') {
            boolean matchesEmptySequence = isMatchRecursive(text, remainingPattern);
            boolean matchesMultipleCharacters = isMatchRecursive(remainingText, pattern);
            isMatch = matchesEmptySequence || matchesMultipleCharacters;
        } else {
            isMatch = false;
        }
        return isMatch;
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

        boolean isMatch;
        if (textChar == patternChar || patternChar == '?') {
            isMatch = isMatchRecursive(text, pattern, textIndex + 1, patternIndex + 1);
        } else if (patternChar == '*') {
            boolean matchesEmptySequence = isMatchRecursive(text, pattern, textIndex, patternIndex + 1);
            boolean matchesMultipleCharacters = isMatchRecursive(text, pattern, textIndex + 1, patternIndex);
            isMatch = matchesEmptySequence || matchesMultipleCharacters;
        } else {
            isMatch = false;
        }
        return isMatch;
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

        boolean isMatch;
        if (textChar == patternChar || patternChar == '?') {
            isMatch = isMatchRecursiveMemo(text, pattern, textIndex + 1, patternIndex + 1, memo);
        } else if (patternChar == '*') {
            boolean matchesEmptySequence =
                    isMatchRecursiveMemo(text, pattern, textIndex, patternIndex + 1, memo);
            boolean matchesMultipleCharacters =
                    isMatchRecursiveMemo(text, pattern, textIndex + 1, patternIndex, memo);
            isMatch = matchesEmptySequence || matchesMultipleCharacters;
        } else {
            isMatch = false;
        }

        memo[textIndex][patternIndex] = isMatch ? 2 : 1;
        return isMatch;
    }

    static boolean isMatchBottomUp(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] matchTable = new boolean[textLength + 1][patternLength + 1];
        matchTable[textLength][patternLength] = true;

        for (int textIndex = textLength; textIndex >= 0; textIndex--) {
            for (int patternIndex = patternLength - 1; patternIndex >= 0; patternIndex--) {
                if (textIndex == textLength) {
                    matchTable[textIndex][patternIndex] =
                            pattern.charAt(patternIndex) == '*' && matchTable[textIndex][patternIndex + 1];
                } else {
                    char textChar = text.charAt(textIndex);
                    char patternChar = pattern.charAt(patternIndex);

                    boolean isMatch;
                    if (textChar == patternChar || patternChar == '?') {
                        isMatch = matchTable[textIndex + 1][patternIndex + 1];
                    } else if (patternChar == '*') {
                        boolean matchesEmptySequence = matchTable[textIndex][patternIndex + 1];
                        boolean matchesMultipleCharacters = matchTable[textIndex + 1][patternIndex];
                        isMatch = matchesEmptySequence || matchesMultipleCharacters;
                    } else {
                        isMatch = false;
                    }
                    matchTable[textIndex][patternIndex] = isMatch;
                }
            }
        }
        return matchTable[0][0];
    }
}