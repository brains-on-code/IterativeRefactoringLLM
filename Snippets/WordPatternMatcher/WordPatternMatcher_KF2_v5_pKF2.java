package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

public final class WordPatternMatcher {

    private WordPatternMatcher() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether {@code inputString} follows the given {@code pattern}.
     *
     * Each character in {@code pattern} must map to a unique, non-empty
     * substring of {@code inputString}, and this mapping must be consistent
     * throughout the entire string (bijection).
     *
     * @param pattern     the pattern consisting of characters
     * @param inputString the string to be matched against the pattern
     * @return {@code true} if {@code inputString} matches {@code pattern},
     *         {@code false} otherwise
     */
    public static boolean matchWordPattern(String pattern, String inputString) {
        Map<Character, String> patternToString = new HashMap<>();
        Map<String, Character> stringToPattern = new HashMap<>();
        return backtrack(pattern, inputString, 0, 0, patternToString, stringToPattern);
    }

    /**
     * Recursive backtracking helper that tries to match {@code pattern} to
     * {@code inputString} starting from the given indices, while maintaining a
     * bijection between pattern characters and substrings of the input.
     *
     * @param pattern         the pattern string
     * @param inputString     the input string
     * @param patternIndex    current index in the pattern
     * @param strIndex        current index in the input string
     * @param patternToString mapping from pattern characters to substrings
     * @param stringToPattern mapping from substrings to pattern characters
     * @return {@code true} if a valid mapping is found from this state,
     *         {@code false} otherwise
     */
    private static boolean backtrack(
            String pattern,
            String inputString,
            int patternIndex,
            int strIndex,
            Map<Character, String> patternToString,
            Map<String, Character> stringToPattern
    ) {
        boolean isPatternExhausted = patternIndex == pattern.length();
        boolean isStringExhausted = strIndex == inputString.length();

        // Both pattern and string are fully matched
        if (isPatternExhausted && isStringExhausted) {
            return true;
        }

        // One is exhausted but not the other: mismatch
        if (isPatternExhausted || isStringExhausted) {
            return false;
        }

        char currentPatternChar = pattern.charAt(patternIndex);
        String existingMapping = patternToString.get(currentPatternChar);

        // If the current pattern character already has a mapping, verify it
        if (existingMapping != null) {
            if (!inputString.startsWith(existingMapping, strIndex)) {
                return false;
            }

            return backtrack(
                    pattern,
                    inputString,
                    patternIndex + 1,
                    strIndex + existingMapping.length(),
                    patternToString,
                    stringToPattern
            );
        }

        // Try all possible new substrings for the current pattern character
        for (int end = strIndex + 1; end <= inputString.length(); end++) {
            String candidateSubstring = inputString.substring(strIndex, end);

            // Ensure bijection: substring must not already be mapped
            if (stringToPattern.containsKey(candidateSubstring)) {
                continue;
            }

            patternToString.put(currentPatternChar, candidateSubstring);
            stringToPattern.put(candidateSubstring, currentPatternChar);

            if (backtrack(pattern, inputString, patternIndex + 1, end, patternToString, stringToPattern)) {
                return true;
            }

            // Backtrack: remove the tentative mapping
            patternToString.remove(currentPatternChar);
            stringToPattern.remove(candidateSubstring);
        }

        return false;
    }
}