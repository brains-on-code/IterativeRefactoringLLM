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

        Map<Character, Integer> charFrequencyMap = new HashMap<>();

        for (int i = 0; i < normalizedFirst.length(); i++) {
            char currentChar = normalizedFirst.charAt(i);
            int updatedCount = charFrequencyMap.getOrDefault(currentChar, 0) + 1;
            charFrequencyMap.put(currentChar, updatedCount);
        }

        for (int i = 0; i < normalizedSecond.length(); i++) {
            char currentChar = normalizedSecond.charAt(i);
            if (!charFrequencyMap.containsKey(currentChar)) {
                return false;
            }
            int updatedCount = charFrequencyMap.get(currentChar) - 1;
            charFrequencyMap.put(currentChar, updatedCount);
        }

        for (int frequency : charFrequencyMap.values()) {
            if (frequency != 0) {
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

        int[] asciiFrequencies = new int[128];

        for (char character : first.toCharArray()) {
            asciiFrequencies[character]++;
        }
        for (char character : second.toCharArray()) {
            asciiFrequencies[character]--;
        }
        for (int frequency : asciiFrequencies) {
            if (frequency != 0) {
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

        int[] letterFrequencies = new int[26];

        for (char character : first.toCharArray()) {
            validateLowercaseLetter(character);
            letterFrequencies[character - 'a']++;
        }
        for (char character : second.toCharArray()) {
            validateLowercaseLetter(character);
            letterFrequencies[character - 'a']--;
        }
        for (int frequency : letterFrequencies) {
            if (frequency != 0) {
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