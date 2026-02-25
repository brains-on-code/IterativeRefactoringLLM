package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for checking whether two strings are anagrams of each other.
 */
public final class AnagramChecker {

    private AnagramChecker() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if two strings are anagrams of each other (case-insensitive) using a
     * {@link Map} to count character frequencies.
     *
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean areAnagramsUsingMap(String first, String second) {
        if (first == null || second == null) {
            return false;
        }

        if (first.length() != second.length()) {
            return false;
        }

        String normalizedFirst = first.toLowerCase();
        String normalizedSecond = second.toLowerCase();

        Map<Character, Integer> charCounts = new HashMap<>();

        for (int i = 0; i < normalizedFirst.length(); i++) {
            char c = normalizedFirst.charAt(i);
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < normalizedSecond.length(); i++) {
            char c = normalizedSecond.charAt(i);
            Integer currentCount = charCounts.get(c);
            if (currentCount == null || currentCount == 0) {
                return false;
            }
            charCounts.put(c, currentCount - 1);
        }

        for (int count : charCounts.values()) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if two strings are anagrams of each other assuming ASCII characters.
     * Uses a fixed-size array of length 128 to count character frequencies.
     *
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean areAnagramsAscii(String first, String second) {
        if (first == null || second == null) {
            return false;
        }

        if (first.length() != second.length()) {
            return false;
        }

        int[] charCounts = new int[128];

        for (char c : first.toCharArray()) {
            if (c >= 128) {
                return false;
            }
            charCounts[c]++;
        }

        for (char c : second.toCharArray()) {
            if (c >= 128) {
                return false;
            }
            charCounts[c]--;
        }

        for (int count : charCounts) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if two strings are anagrams of each other assuming they contain only
     * lowercase English letters {@code 'a'} to {@code 'z'}.
     *
     * @param first  the first string (lowercase letters only)
     * @param second the second string (lowercase letters only)
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     * @throws IllegalArgumentException if either string contains characters
     *                                  outside {@code 'a'} to {@code 'z'}
     */
    public static boolean areAnagramsLowercase(String first, String second) {
        if (first == null || second == null) {
            return false;
        }

        if (first.length() != second.length()) {
            return false;
        }

        int[] charCounts = new int[26];

        for (char c : first.toCharArray()) {
            validateLowercaseLetter(c);
            charCounts[c - 'a']++;
        }

        for (char c : second.toCharArray()) {
            validateLowercaseLetter(c);
            charCounts[c - 'a']--;
        }

        for (int count : charCounts) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validates that the given character is a lowercase English letter.
     *
     * @param c the character to validate
     * @throws IllegalArgumentException if the character is not between
     *                                  {@code 'a'} and {@code 'z'}
     */
    private static void validateLowercaseLetter(char c) {
        int index = c - 'a';
        if (index < 0 || index >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}