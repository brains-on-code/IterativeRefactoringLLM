package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to determine if a pattern matches a string using backtracking.
 *
 * <p>Examples:
 * <pre>
 * Pattern: "abab"
 * Input:   "JavaPythonJavaPython"
 * Output:  true
 *
 * Pattern: "aaaa"
 * Input:   "JavaJavaJavaJava"
 * Output:  true
 *
 * Pattern: "aabb"
 * Input:   "JavaPythonPythonJava"
 * Output:  false
 * </pre>
 */
public final class WordPatternMatcher {

    private WordPatternMatcher() {
        // Prevent instantiation
    }

    /**
     * Determines if the given pattern matches the input string using backtracking.
     *
     * @param pattern     the pattern to match
     * @param inputString the string to match against the pattern
     * @return {@code true} if the pattern matches the string; {@code false} otherwise
     */
    public static boolean matchWordPattern(String pattern, String inputString) {
        Map<Character, String> patternToString = new HashMap<>();
        Map<String, Character> stringToPattern = new HashMap<>();
        return backtrack(pattern, inputString, 0, 0, patternToString, stringToPattern);
    }

    /**
     * Recursive backtracking helper to check if the pattern matches the string.
     *
     * <p>The method maintains a bijection between:
     * <ul>
     *   <li>pattern characters, and</li>
     *   <li>non-empty substrings of {@code inputString}</li>
     * </ul>
     *
     * @param pattern         the pattern string
     * @param inputString     the string to match against the pattern
     * @param patternIndex    current index in the pattern
     * @param stringIndex     current index in the input string
     * @param patternToString mapping from pattern characters to substrings
     * @param stringToPattern mapping from substrings to pattern characters
     * @return {@code true} if the pattern matches; {@code false} otherwise
     */
    private static boolean backtrack(
            String pattern,
            String inputString,
            int patternIndex,
            int stringIndex,
            Map<Character, String> patternToString,
            Map<String, Character> stringToPattern
    ) {
        // Both pattern and string are fully consumed
        if (patternIndex == pattern.length() && stringIndex == inputString.length()) {
            return true;
        }

        // One is exhausted before the other
        if (patternIndex == pattern.length() || stringIndex == inputString.length()) {
            return false;
        }

        char currentPatternChar = pattern.charAt(patternIndex);

        // Existing mapping for current pattern character
        if (patternToString.containsKey(currentPatternChar)) {
            String mappedSubstring = patternToString.get(currentPatternChar);

            // Next part of the input must match the mapped substring
            if (!inputString.startsWith(mappedSubstring, stringIndex)) {
                return false;
            }

            // Continue with next pattern character and advanced string index
            return backtrack(
                    pattern,
                    inputString,
                    patternIndex + 1,
                    stringIndex + mappedSubstring.length(),
                    patternToString,
                    stringToPattern
            );
        }

        // No mapping yet; try all possible non-empty substrings
        for (int end = stringIndex + 1; end <= inputString.length(); end++) {
            String candidateSubstring = inputString.substring(stringIndex, end);

            // Enforce bijection: candidate must not already be mapped
            if (stringToPattern.containsKey(candidateSubstring)) {
                continue;
            }

            // Tentatively assign the mapping
            patternToString.put(currentPatternChar, candidateSubstring);
            stringToPattern.put(candidateSubstring, currentPatternChar);

            // Recurse with next pattern character and updated string index
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