package com.thealgorithms.dynamicprogramming;

public final class WildcardPatternMatcher {

    private WildcardPatternMatcher() {
    }

    /**
     * Returns true if the input string matches the given pattern.
     * Pattern supports:
     *   '?' – matches any single character
     *   '*' – matches any sequence of characters (including empty)
     */
    public static boolean matches(String text, String pattern) {
        int textLength = text.length();
        int patternLength = pattern.length();

        boolean[][] matchTable = new boolean[textLength + 1][patternLength + 1];

        matchTable[0][0] = true;

        for (int patternPos = 1; patternPos <= patternLength; patternPos++) {
            if (pattern.charAt(patternPos - 1) == '*') {
                matchTable[0][patternPos] = matchTable[0][patternPos - 1];
            }
        }

        for (int textPos = 1; textPos <= textLength; textPos++) {
            for (int patternPos = 1; patternPos <= patternLength; patternPos++) {
                char currentTextChar = text.charAt(textPos - 1);
                char currentPatternChar = pattern.charAt(patternPos - 1);

                if (currentPatternChar == currentTextChar || currentPatternChar == '?') {
                    matchTable[textPos][patternPos] = matchTable[textPos - 1][patternPos - 1];
                } else if (currentPatternChar == '*') {
                    matchTable[textPos][patternPos] =
                        matchTable[textPos - 1][patternPos] || matchTable[textPos][patternPos - 1];
                } else {
                    matchTable[textPos][patternPos] = false;
                }
            }
        }

        return matchTable[textLength][patternLength];
    }
}