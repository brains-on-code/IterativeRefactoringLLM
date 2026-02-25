package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

public final class WordPatternMatcher {

    private WordPatternMatcher() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the given input string follows the specified pattern.
     *
     * Each character in {@code pattern} must map to a non-empty, unique substring
     * of {@code inputString}, and this mapping must be consistent across the
     * entire string.
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
     * Recursive backtracking helper that attempts to build a bijection between
     * pattern characters and substrings of the input string.
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
        // Both pattern and string are fully matched
        if (patternIndex == pattern.length() && strIndex == inputString.length()) {
            return true;
        }

        // One is exhausted before the other: invalid match
        if (patternIndex == pattern.length() || strIndex == inputString.length()) {
            return false;
        }

        char currentPatternChar = pattern.charAt(patternIndex);

        // Case 1: current pattern character already has a mapped substring
        if (patternToString.containsKey(currentPatternChar)) {
            String mappedSubstring = patternToString.get(currentPatternChar);

            // The next part of inputString must start with this mapped substring
            if (!inputString.startsWith(mappedSubstring, strIndex)) {
                return false;
            }

            // Continue with the next pattern character and advanced string index
            return backtrack(
                    pattern,
                    inputString,
                    patternIndex + 1,
                    strIndex + mappedSubstring.length(),
                    patternToString,
                    stringToPattern
            );
        }

        // Case 2: current pattern character is not yet mapped
        // Try all possible non-empty substrings starting at strIndex
        for (int end = strIndex + 1; end <= inputString.length(); end++) {
            String candidateSubstring = inputString.substring(strIndex, end);

            // Ensure bijection: candidateSubstring must not already be mapped
            if (stringToPattern.containsKey(candidateSubstring)) {
                continue;
            }

            // Tentatively assign the mapping
            patternToString.put(currentPatternChar, candidateSubstring);
            stringToPattern.put(candidateSubstring, currentPatternChar);

            // Recurse with the next pattern character and updated string index
            if (backtrack(pattern, inputString, patternIndex + 1, end, patternToString, stringToPattern)) {
                return true;
            }

            // Backtrack: remove tentative mapping
            patternToString.remove(currentPatternChar);
            stringToPattern.remove(candidateSubstring);
        }

        // No valid mapping found for this configuration
        return false;
    }
}