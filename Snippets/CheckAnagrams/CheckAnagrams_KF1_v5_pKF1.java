package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for string-related algorithms.
 */
public final class AnagramUtils {

    private AnagramUtils() {
    }

    /**
     * Checks if two strings are anagrams of each other (case-insensitive)
     * using a character frequency map.
     */
    public static boolean areAnagramsUsingMap(String first, String second) {
        String normalizedFirst = first.toLowerCase();
        String normalizedSecond = second.toLowerCase();

        if (normalizedFirst.length() != normalizedSecond.length()) {
            return false;
        }

        Map<Character, Integer> characterCounts = new HashMap<>();

        for (int index = 0; index < normalizedFirst.length(); index++) {
            char character = normalizedFirst.charAt(index);
            int updatedCount = characterCounts.getOrDefault(character, 0) + 1;
            characterCounts.put(character, updatedCount);
        }

        for (int index = 0; index < normalizedSecond.length(); index++) {
            char character = normalizedSecond.charAt(index);
            if (!characterCounts.containsKey(character)) {
                return false;
            }
            int updatedCount = characterCounts.get(character) - 1;
            characterCounts.put(character, updatedCount);
        }

        for (int count : characterCounts.values()) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if two strings are anagrams of each other assuming ASCII characters,
     * using a fixed-size frequency array.
     */
    public static boolean areAnagramsAscii(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        int[] asciiCharacterCounts = new int[128];

        for (char character : first.toCharArray()) {
            asciiCharacterCounts[character]++;
        }
        for (char character : second.toCharArray()) {
            asciiCharacterCounts[character]--;
        }
        for (int count : asciiCharacterCounts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if two strings are anagrams of each other assuming only
     * lowercase English letters ('a' to 'z').
     */
    public static boolean areAnagramsLowercase(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        int[] letterCounts = new int[26];

        for (char character : first.toCharArray()) {
            validateLowercaseLetter(character);
            letterCounts[character - 'a']++;
        }
        for (char character : second.toCharArray()) {
            validateLowercaseLetter(character);
            letterCounts[character - 'a']--;
        }
        for (int count : letterCounts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    private static void validateLowercaseLetter(char character) {
        int alphabetIndex = character - 'a';
        if (alphabetIndex < 0 || alphabetIndex >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}