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
        Map<Character, String> charToSubstring = new HashMap<>();
        Map<String, Character> substringToChar = new HashMap<>();
        return backtrack(pattern, text, 0, 0, charToSubstring, substringToChar);
    }

    private static boolean backtrack(
            String pattern,
            String text,
            int patternIndex,
            int textIndex,
            Map<Character, String> charToSubstring,
            Map<String, Character> substringToChar
    ) {
        if (patternIndex == pattern.length() && textIndex == text.length()) {
            return true;
        }
        if (patternIndex == pattern.length() || textIndex == text.length()) {
            return false;
        }

        char currentPatternChar = pattern.charAt(patternIndex);

        if (charToSubstring.containsKey(currentPatternChar)) {
            String mappedSubstring = charToSubstring.get(currentPatternChar);
            if (text.startsWith(mappedSubstring, textIndex)) {
                return backtrack(
                        pattern,
                        text,
                        patternIndex + 1,
                        textIndex + mappedSubstring.length(),
                        charToSubstring,
                        substringToChar
                );
            }
            return false;
        }

        for (int endIndex = textIndex + 1; endIndex <= text.length(); endIndex++) {
            String candidateSubstring = text.substring(textIndex, endIndex);
            if (substringToChar.containsKey(candidateSubstring)) {
                continue;
            }

            charToSubstring.put(currentPatternChar, candidateSubstring);
            substringToChar.put(candidateSubstring, currentPatternChar);

            if (backtrack(pattern, text, patternIndex + 1, endIndex, charToSubstring, substringToChar)) {
                return true;
            }

            charToSubstring.remove(currentPatternChar);
            substringToChar.remove(candidateSubstring);
        }

        return false;
    }
}