package com.thealgorithms.dynamicprogramming;

public final class WildcardMatching {

    private WildcardMatching() {
        // Utility class; prevent instantiation
    }

    public static boolean isMatch(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] matchTable = new boolean[textLength + 1][patternLength + 1];

        matchTable[0][0] = true;

        for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
            if (pattern.charAt(patternIndex - 1) == '*') {
                matchTable[0][patternIndex] = matchTable[0][patternIndex - 1];
            }
        }

        for (int textIndex = 1; textIndex <= textLength; textIndex++) {
            for (int patternIndex = 1; patternIndex <= patternLength; patternIndex++) {
                char currentTextChar = text.charAt(textIndex - 1);
                char currentPatternChar = pattern.charAt(patternIndex - 1);

                if (currentPatternChar == currentTextChar || currentPatternChar == '?') {
                    matchTable[textIndex][patternIndex] = matchTable[textIndex - 1][patternIndex - 1];
                } else if (currentPatternChar == '*') {
                    matchTable[textIndex][patternIndex] =
                        matchTable[textIndex - 1][patternIndex] || matchTable[textIndex][patternIndex - 1];
                } else {
                    matchTable[textIndex][patternIndex] = false;
                }
            }
        }

        return matchTable[textLength][patternLength];
    }
}