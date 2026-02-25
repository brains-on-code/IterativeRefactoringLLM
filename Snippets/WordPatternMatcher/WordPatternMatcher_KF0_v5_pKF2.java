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
        // Utility class; prevent instantiation
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
     */
    private static boolean backtrack(
            String pattern,
            String inputString,
            int patternIndex,
            int stringIndex,
            Map<Character, String> patternToString,
            Map<String, Character> stringToPattern
    ) {
        boolean patternConsumed = patternIndex == pattern.length();
        boolean stringConsumed = stringIndex == inputString.length();

        if (patternConsumed && stringConsumed) {
            return true;
        }

        if (patternConsumed || stringConsumed) {
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

        for (int end = stringIndex + 1; end <= inputString.length(); end++) {
            String candidateSubstring = inputString.substring(stringIndex, end);

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