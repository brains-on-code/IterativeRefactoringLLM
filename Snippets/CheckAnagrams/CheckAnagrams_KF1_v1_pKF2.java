package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for checking whether two strings are anagrams of each other.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Checks if two strings are anagrams of each other (case-insensitive) using a
     * {@link Map} to count character frequencies.
     *
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean method1(String first, String second) {
        int firstLength = first.length();
        int secondLength = second.length();

        first = first.toLowerCase();
        second = second.toLowerCase();

        Map<Character, Integer> charCounts = new HashMap<>();

        for (int i = 0; i < firstLength; i++) {
            char c = first.charAt(i);
            int count = charCounts.getOrDefault(c, 0);
            charCounts.put(c, count + 1);
        }

        for (int i = 0; i < secondLength; i++) {
            char c = second.charAt(i);
            if (!charCounts.containsKey(c)) {
                return false;
            }
            charCounts.put(c, charCounts.get(c) - 1);
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
    public static boolean method2(String first, String second) {
        int[] charCounts = new int[128];

        for (char c : first.toCharArray()) {
            charCounts[c]++;
        }
        for (char c : second.toCharArray()) {
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
    public static boolean method3(String first, String second) {
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