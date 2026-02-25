/**
 *
 * Author: Janmesh Singh
 * Github: https://github.com/janmeshjs
 *
 * Problem Statement: To determine if the pattern matches the text.
 * The pattern can include two special wildcard characters:
 *       ' ? ': Matches any single character.
 *       ' * ': Matches zero or more of any character sequence.
 *
 * Use DP to return True if the pattern matches the entire text and False otherwise
 *
 */

package com.thealgorithms.dynamicprogramming;

public final class WildcardMatching {
    private WildcardMatching() {
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