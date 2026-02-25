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
        // Both pattern and string are fully matched
        if (patternIndex == pattern.length() && stringIndex == inputString.length()) {
            return true;
        }

        // One is exhausted before the other
        if (patternIndex == pattern.length() || stringIndex == inputString.length()) {
            return false;
        }

        char currentPatternChar = pattern.charAt(patternIndex);

        // Existing mapping for this pattern character: validate and advance
        if (patternToString.containsKey(currentPatternChar)) {
            String mapped = patternToString.get(currentPatternChar);

            if (!inputString.startsWith(mapped, stringIndex)) {
                return false;
            }

            return backtrack(
                    pattern,
                    inputString,
                    patternIndex + 1,
                    stringIndex + mapped.length(),
                    patternToString,
                    stringToPattern
            );
        }

        // No mapping yet: try all possible substrings for the current pattern character
        for (int end = stringIndex + 1; end <= inputString.length(); end++) {
            String candidate = inputString.substring(stringIndex, end);

            // Enforce bijection: candidate must not already be mapped
            if (stringToPattern.containsKey(candidate)) {
                continue;
            }

            patternToString.put(currentPatternChar, candidate);
            stringToPattern.put(candidate, currentPatternChar);

            if (backtrack(pattern, inputString, patternIndex + 1, end, patternToString, stringToPattern)) {
                return true;
            }

            // Undo mapping (backtrack)
            patternToString.remove(currentPatternChar);
            stringToPattern.remove(candidate);
        }

        return false;
    }
}