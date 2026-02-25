package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to determine if a pattern matches a string using backtracking.
 *
 * Example:
 * Pattern: "abab"
 * Input String: "JavaPythonJavaPython"
 * Output: true
 *
 * Pattern: "aaaa"
 * Input String: "JavaJavaJavaJava"
 * Output: true
 *
 * Pattern: "aabb"
 * Input String: "JavaPythonPythonJava"
 * Output: false
 */
public final class WordPatternMatcher {

    private WordPatternMatcher() {
        // Utility class; prevent instantiation
    }

    /**
     * Determines if the given pattern matches the input string using backtracking.
     *
     * @param pattern     The pattern to match.
     * @param inputString The string to match against the pattern.
     * @return True if the pattern matches the string, False otherwise.
     */
    public static boolean matchWordPattern(String pattern, String inputString) {
        Map<Character, String> patternToString = new HashMap<>();
        Map<String, Character> stringToPattern = new HashMap<>();
        return backtrack(pattern, inputString, 0, 0, patternToString, stringToPattern);
    }

    /**
     * Backtracking helper function to check if the pattern matches the string.
     *
     * @param pattern         The pattern string.
     * @param inputString     The string to match against the pattern.
     * @param patternIndex    Current index in the pattern.
     * @param stringIndex     Current index in the input string.
     * @param patternToString Map to store pattern characters to string mappings.
     * @param stringToPattern Map to store string to pattern character mappings.
     * @return True if the pattern matches, False otherwise.
     */
    private static boolean backtrack(
        String pattern,
        String inputString,
        int patternIndex,
        int stringIndex,
        Map<Character, String> patternToString,
        Map<String, Character> stringToPattern
    ) {
        if (patternIndex == pattern.length() && stringIndex == inputString.length()) {
            return true;
        }

        if (patternIndex == pattern.length() || stringIndex == inputString.length()) {
            return false;
        }

        char currentPatternChar = pattern.charAt(patternIndex);

        String existingMapping = patternToString.get(currentPatternChar);
        if (existingMapping != null) {
            if (!inputString.startsWith(existingMapping, stringIndex)) {
                return false;
            }
            return backtrack(
                pattern,
                inputString,
                patternIndex + 1,
                stringIndex + existingMapping.length(),
                patternToString,
                stringToPattern
            );
        }

        for (int endIndex = stringIndex + 1; endIndex <= inputString.length(); endIndex++) {
            String candidate = inputString.substring(stringIndex, endIndex);

            if (stringToPattern.containsKey(candidate)) {
                continue;
            }

            patternToString.put(currentPatternChar, candidate);
            stringToPattern.put(candidate, currentPatternChar);

            if (backtrack(pattern, inputString, patternIndex + 1, endIndex, patternToString, stringToPattern)) {
                return true;
            }

            patternToString.remove(currentPatternChar);
            stringToPattern.remove(candidate);
        }

        return false;
    }
}