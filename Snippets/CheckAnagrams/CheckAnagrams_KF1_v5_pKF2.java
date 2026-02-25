package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for checking whether two strings are anagrams of each other.
 */
public final class AnagramChecker {

    private static final int ASCII_SIZE = 128;
    private static final int ALPHABET_SIZE = 26;

    private AnagramChecker() {
        // Prevent instantiation.
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
        if (!haveSameNonNullLength(first, second)) {
            return false;
        }

        String normalizedFirst = first.toLowerCase();
        String normalizedSecond = second.toLowerCase();

        Map<Character, Integer> charCounts = buildCharFrequencyMap(normalizedFirst);

        for (int i = 0; i < normalizedSecond.length(); i++) {
            char c = normalizedSecond.charAt(i);
            Integer currentCount = charCounts.get(c);
            if (currentCount == null || currentCount == 0) {
                return false;
            }
            charCounts.put(c, currentCount - 1);
        }

        return allCountsZero(charCounts.values());
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
        if (!haveSameNonNullLength(first, second)) {
            return false;
        }

        int[] charCounts = new int[ASCII_SIZE];

        for (char c : first.toCharArray()) {
            if (!isAscii(c)) {
                return false;
            }
            charCounts[c]++;
        }

        for (char c : second.toCharArray()) {
            if (!isAscii(c)) {
                return false;
            }
            charCounts[c]--;
        }

        return allCountsZero(charCounts);
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
        if (!haveSameNonNullLength(first, second)) {
            return false;
        }

        int[] charCounts = new int[ALPHABET_SIZE];

        for (char c : first.toCharArray()) {
            validateLowercaseLetter(c);
            charCounts[c - 'a']++;
        }

        for (char c : second.toCharArray()) {
            validateLowercaseLetter(c);
            charCounts[c - 'a']--;
        }

        return allCountsZero(charCounts);
    }

    private static boolean haveSameNonNullLength(String first, String second) {
        return first != null && second != null && first.length() == second.length();
    }

    private static Map<Character, Integer> buildCharFrequencyMap(String input) {
        Map<Character, Integer> charCounts = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }
        return charCounts;
    }

    private static boolean allCountsZero(Iterable<Integer> counts) {
        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean allCountsZero(int[] counts) {
        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAscii(char c) {
        return c < ASCII_SIZE;
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
        if (index < 0 || index >= ALPHABET_SIZE) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}