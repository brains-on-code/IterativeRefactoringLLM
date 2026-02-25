package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for checking if a pattern can be mapped to a text using a
 * bijective (one-to-one and onto) mapping between pattern characters and
 * non-empty substrings of the text.
 */
public final class PatternMatcher {

    private PatternMatcher() {
    }

    /**
     * Determines whether the given pattern can be mapped to the text such that:
     * - Each character in the pattern maps to a non-empty substring of the text.
     * - The mapping is consistent for each character.
     * - The mapping is bijective (no two characters map to the same substring).
     *
     * @param pattern the pattern string consisting of characters to be mapped
     * @param text    the text string to be matched against the pattern
     * @return true if such a mapping exists, false otherwise
     */
    public static boolean matchesPattern(String pattern, String text) {
        Map<Character, String> patternToTextMap = new HashMap<>();
        Map<String, Character> textToPatternMap = new HashMap<>();
        return isMatch(pattern, text, 0, 0, patternToTextMap, textToPatternMap);
    }

    private static boolean isMatch(
            String pattern,
            String text,
            int patternPosition,
            int textPosition,
            Map<Character, String> patternToTextMap,
            Map<String, Character> textToPatternMap
    ) {
        if (patternPosition == pattern.length() && textPosition == text.length()) {
            return true;
        }
        if (patternPosition == pattern.length() || textPosition == text.length()) {
            return false;
        }

        char patternChar = pattern.charAt(patternPosition);

        if (patternToTextMap.containsKey(patternChar)) {
            String mappedTextSegment = patternToTextMap.get(patternChar);
            if (text.startsWith(mappedTextSegment, textPosition)) {
                return isMatch(
                        pattern,
                        text,
                        patternPosition + 1,
                        textPosition + mappedTextSegment.length(),
                        patternToTextMap,
                        textToPatternMap
                );
            }
            return false;
        }

        for (int segmentEnd = textPosition + 1; segmentEnd <= text.length(); segmentEnd++) {
            String candidateTextSegment = text.substring(textPosition, segmentEnd);
            if (textToPatternMap.containsKey(candidateTextSegment)) {
                continue;
            }

            patternToTextMap.put(patternChar, candidateTextSegment);
            textToPatternMap.put(candidateTextSegment, patternChar);

            if (isMatch(pattern, text, patternPosition + 1, segmentEnd, patternToTextMap, textToPatternMap)) {
                return true;
            }

            patternToTextMap.remove(patternChar);
            textToPatternMap.remove(candidateTextSegment);
        }

        return false;
    }
}