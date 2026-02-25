package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

public final class WordPatternMatcher {

    private WordPatternMatcher() {
        // Prevent instantiation
    }

    /**
     * Determines whether {@code inputString} follows the given {@code pattern}.
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
     * Attempts to match {@code pattern} to {@code inputString} starting from the
     * given indices, maintaining a bijection between pattern characters and
     * substrings of the input.
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
        boolean patternExhausted = patternIndex == pattern.length();
        boolean stringExhausted = strIndex == inputString.length();

        if (patternExhausted && stringExhausted) {
            return true;
        }

        if (patternExhausted || stringExhausted) {
            return false;
        }

        char currentPatternChar = pattern.charAt(patternIndex);

        String existingMapping = patternToString.get(currentPatternChar);
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

        for (int end = strIndex + 1; end <= inputString.length(); end++) {
            String candidateSubstring = inputString.substring(strIndex, end);

            if (stringToPattern.containsKey(candidateSubstring)) {
                continue;
            }

            patternToString.put(currentPatternChar, candidateSubstring);
            stringToPattern.put(candidateSubstring, currentPatternChar);

            if (backtrack(pattern, inputString, patternIndex + 1, end, patternToString, stringToPattern)) {
                return true;
            }

            patternToString.remove(currentPatternChar);
            stringToPattern.remove(candidateSubstring);
        }

        return false;
    }
}