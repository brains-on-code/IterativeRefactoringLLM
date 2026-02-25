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
        Map<Character, String> charToString = new HashMap<>();
        Map<String, Character> stringToChar = new HashMap<>();
        return backtrack(pattern, text, 0, 0, charToString, stringToChar);
    }

    /**
     * Backtracking helper to build a bijective mapping between pattern characters
     * and substrings of text.
     */
    private static boolean backtrack(
            String pattern,
            String text,
            int patternIndex,
            int textIndex,
            Map<Character, String> charToString,
            Map<String, Character> stringToChar
    ) {
        if (patternIndex == pattern.length() && textIndex == text.length()) {
            return true;
        }
        if (patternIndex == pattern.length() || textIndex == text.length()) {
            return false;
        }

        char currentChar = pattern.charAt(patternIndex);

        // If the character already has a mapping, verify it matches the text at this position
        if (charToString.containsKey(currentChar)) {
            String mapped = charToString.get(currentChar);
            if (text.startsWith(mapped, textIndex)) {
                return backtrack(
                        pattern,
                        text,
                        patternIndex + 1,
                        textIndex + mapped.length(),
                        charToString,
                        stringToChar
                );
            }
            return false;
        }

        // Try all possible new mappings for the current character
        for (int end = textIndex + 1; end <= text.length(); end++) {
            String candidate = text.substring(textIndex, end);

            // Ensure bijection: candidate must not already be mapped to another character
            if (stringToChar.containsKey(candidate)) {
                continue;
            }

            charToString.put(currentChar, candidate);
            stringToChar.put(candidate, currentChar);

            if (backtrack(pattern, text, patternIndex + 1, end, charToString, stringToChar)) {
                return true;
            }

            // Backtrack
            charToString.remove(currentChar);
            stringToChar.remove(candidate);
        }

        return false;
    }
}