package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

/**
 * Determines whether there exists a bijective mapping between characters of a
 * pattern string and non-empty substrings of a target string.
 *
 * Example:
 * pattern = "ab", target = "redblue":
 *   'a' -> "red"
 *   'b' -> "blue"
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Returns true if there exists a bijective mapping between each character
     * in {@code pattern} and a non-empty substring of {@code str} such that
     * replacing the characters in {@code pattern} with their mapped substrings
     * yields {@code str}.
     */
    public static boolean method1(String pattern, String str) {
        Map<Character, String> charToString = new HashMap<>();
        Map<String, Character> stringToChar = new HashMap<>();
        return backtrack(pattern, str, 0, 0, charToString, stringToChar);
    }

    /**
     * Attempts to build a bijective mapping between pattern characters and
     * substrings of {@code str} using backtracking.
     */
    private static boolean backtrack(
            String pattern,
            String str,
            int patternIndex,
            int strIndex,
            Map<Character, String> charToString,
            Map<String, Character> stringToChar
    ) {
        // Both pattern and string are fully matched
        if (patternIndex == pattern.length() && strIndex == str.length()) {
            return true;
        }

        // One is exhausted before the other
        if (patternIndex == pattern.length() || strIndex == str.length()) {
            return false;
        }

        char currentChar = pattern.charAt(patternIndex);

        // If currentChar already has a mapping, verify it matches the string at strIndex
        if (charToString.containsKey(currentChar)) {
            String mapped = charToString.get(currentChar);
            if (str.startsWith(mapped, strIndex)) {
                return backtrack(
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

        // Try all possible non-empty substrings starting at strIndex as a candidate mapping
        for (int end = strIndex + 1; end <= str.length(); end++) {
            String candidate = str.substring(strIndex, end);

            // Enforce bijection: candidate must not already be mapped to another character
            if (stringToChar.containsKey(candidate)) {
                continue;
            }

            // Assign mapping
            charToString.put(currentChar, candidate);
            stringToChar.put(candidate, currentChar);

            // Recurse with the new mapping
            if (backtrack(pattern, str, patternIndex + 1, end, charToString, stringToChar)) {
                return true;
            }

            // Remove mapping (backtrack)
            charToString.remove(currentChar);
            stringToChar.remove(candidate);
        }

        return false;
    }
}