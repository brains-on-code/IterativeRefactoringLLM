package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for checking if there exists a bijective mapping between
 * characters of a pattern string and non-empty substrings of a target string.
 *
 * Example:
 * pattern = "ab", target = "redblue" can map as:
 *   'a' -> "red"
 *   'b' -> "blue"
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Determines whether there exists a bijective mapping between each character
     * in {@code pattern} and a non-empty substring of {@code str} such that
     * replacing the characters in {@code pattern} with their mapped substrings
     * yields {@code str}.
     *
     * @param pattern the pattern string whose characters are to be mapped
     * @param str     the target string to match against the pattern
     * @return {@code true} if such a bijective mapping exists; {@code false} otherwise
     */
    public static boolean method1(String pattern, String str) {
        Map<Character, String> charToString = new HashMap<>();
        Map<String, Character> stringToChar = new HashMap<>();
        return method2(pattern, str, 0, 0, charToString, stringToChar);
    }

    /**
     * Backtracking helper that attempts to build a bijective mapping between
     * pattern characters and substrings of {@code str}.
     *
     * @param pattern      the pattern string
     * @param str          the target string
     * @param patternIndex current index in the pattern
     * @param strIndex     current index in the target string
     * @param charToString mapping from pattern characters to substrings
     * @param stringToChar mapping from substrings to pattern characters
     * @return {@code true} if a valid mapping can be completed from this state
     */
    private static boolean method2(
            String pattern,
            String str,
            int patternIndex,
            int strIndex,
            Map<Character, String> charToString,
            Map<String, Character> stringToChar
    ) {
        // Both pattern and string fully matched
        if (patternIndex == pattern.length() && strIndex == str.length()) {
            return true;
        }

        // One finished before the other: invalid mapping
        if (patternIndex == pattern.length() || strIndex == str.length()) {
            return false;
        }

        char currentChar = pattern.charAt(patternIndex);

        // If the current pattern character already has a mapping, verify it
        if (charToString.containsKey(currentChar)) {
            String mapped = charToString.get(currentChar);
            if (str.startsWith(mapped, strIndex)) {
                return method2(
                        pattern,
                        str,
                        patternIndex + 1,
                        strIndex + mapped.length(),
                        charToString,
                        stringToChar
                );
            }
            return false;
        }

        // Try all possible non-empty substrings starting at strIndex
        for (int end = strIndex + 1; end <= str.length(); end++) {
            String candidate = str.substring(strIndex, end);

            // Ensure bijection: candidate must not already be mapped
            if (stringToChar.containsKey(candidate)) {
                continue;
            }

            // Choose
            charToString.put(currentChar, candidate);
            stringToChar.put(candidate, currentChar);

            // Explore
            if (method2(pattern, str, patternIndex + 1, end, charToString, stringToChar)) {
                return true;
            }

            // Backtrack
            charToString.remove(currentChar);
            stringToChar.remove(candidate);
        }

        return false;
    }
}