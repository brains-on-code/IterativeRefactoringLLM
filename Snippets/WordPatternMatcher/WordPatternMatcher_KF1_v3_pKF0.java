package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for checking if there exists a bijective mapping between
 * characters of one string and substrings of another string.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Determines whether there is a bijective mapping between each character in
     * {@code pattern} and a non-empty substring of {@code text} such that
     * concatenating the mapped substrings in order equals {@code text}.
     *
     * @param pattern the pattern string whose characters are to be mapped
     * @param text    the text string to be segmented according to the pattern
     * @return {@code true} if such a mapping exists; {@code false} otherwise
     */
    public static boolean method1(String pattern, String text) {
        Map<Character, String> charToSubstring = new HashMap<>();
        Map<String, Character> substringToChar = new HashMap<>();
        return hasBijectiveMapping(pattern, text, 0, 0, charToSubstring, substringToChar);
    }

    /**
     * Backtracking helper to build a bijective mapping between pattern characters
     * and substrings of text.
     */
    private static boolean hasBijectiveMapping(
            String pattern,
            String text,
            int patternIndex,
            int textIndex,
            Map<Character, String> charToSubstring,
            Map<String, Character> substringToChar
    ) {
        boolean isPatternExhausted = patternIndex == pattern.length();
        boolean isTextExhausted = textIndex == text.length();

        if (isPatternExhausted && isTextExhausted) {
            return true;
        }
        if (isPatternExhausted || isTextExhausted) {
            return false;
        }

        char currentPatternChar = pattern.charAt(patternIndex);

        String existingMapping = charToSubstring.get(currentPatternChar);
        if (existingMapping != null) {
            if (!text.startsWith(existingMapping, textIndex)) {
                return false;
            }
            return hasBijectiveMapping(
                    pattern,
                    text,
                    patternIndex + 1,
                    textIndex + existingMapping.length(),
                    charToSubstring,
                    substringToChar
            );
        }

        for (int end = textIndex + 1; end <= text.length(); end++) {
            String candidateSubstring = text.substring(textIndex, end);

            if (substringToChar.containsKey(candidateSubstring)) {
                continue;
            }

            charToSubstring.put(currentPatternChar, candidateSubstring);
            substringToChar.put(candidateSubstring, currentPatternChar);

            if (hasBijectiveMapping(
                    pattern,
                    text,
                    patternIndex + 1,
                    end,
                    charToSubstring,
                    substringToChar
            )) {
                return true;
            }

            charToSubstring.remove(currentPatternChar);
            substringToChar.remove(candidateSubstring);
        }

        return false;
    }
}