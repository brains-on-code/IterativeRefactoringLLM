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
        String firstLowercase = first.toLowerCase();
        String secondLowercase = second.toLowerCase();

        if (firstLowercase.length() != secondLowercase.length()) {
            return false;
        }

        Map<Character, Integer> characterFrequencyMap = new HashMap<>();

        for (int index = 0; index < firstLowercase.length(); index++) {
            char character = firstLowercase.charAt(index);
            int updatedCount = characterFrequencyMap.getOrDefault(character, 0) + 1;
            characterFrequencyMap.put(character, updatedCount);
        }

        for (int index = 0; index < secondLowercase.length(); index++) {
            char character = secondLowercase.charAt(index);
            if (!characterFrequencyMap.containsKey(character)) {
                return false;
            }
            int updatedCount = characterFrequencyMap.get(character) - 1;
            characterFrequencyMap.put(character, updatedCount);
        }

        for (int frequency : characterFrequencyMap.values()) {
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

        int[] asciiCharacterFrequencies = new int[128];

        for (char character : first.toCharArray()) {
            asciiCharacterFrequencies[character]++;
        }
        for (char character : second.toCharArray()) {
            asciiCharacterFrequencies[character]--;
        }
        for (int frequency : asciiCharacterFrequencies) {
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

        int[] lowercaseLetterFrequencies = new int[26];

        for (char character : first.toCharArray()) {
            validateLowercaseLetter(character);
            lowercaseLetterFrequencies[character - 'a']++;
        }
        for (char character : second.toCharArray()) {
            validateLowercaseLetter(character);
            lowercaseLetterFrequencies[character - 'a']--;
        }
        for (int frequency : lowercaseLetterFrequencies) {
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