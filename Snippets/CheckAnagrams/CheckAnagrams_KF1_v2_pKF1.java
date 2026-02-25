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

        Map<Character, Integer> characterFrequencyMap = new HashMap<>();

        for (int i = 0; i < normalizedFirst.length(); i++) {
            char currentChar = normalizedFirst.charAt(i);
            int currentCount = characterFrequencyMap.getOrDefault(currentChar, 0);
            characterFrequencyMap.put(currentChar, currentCount + 1);
        }

        for (int i = 0; i < normalizedSecond.length(); i++) {
            char currentChar = normalizedSecond.charAt(i);
            if (!characterFrequencyMap.containsKey(currentChar)) {
                return false;
            }
            characterFrequencyMap.put(currentChar, characterFrequencyMap.get(currentChar) - 1);
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

        int[] characterCounts = new int[128];

        for (char character : first.toCharArray()) {
            characterCounts[character]++;
        }
        for (char character : second.toCharArray()) {
            characterCounts[character]--;
        }
        for (int count : characterCounts) {
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

        int[] characterCounts = new int[26];

        for (char character : first.toCharArray()) {
            validateLowercaseLetter(character);
            characterCounts[character - 'a']++;
        }
        for (char character : second.toCharArray()) {
            validateLowercaseLetter(character);
            characterCounts[character - 'a']--;
        }
        for (int count : characterCounts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    private static void validateLowercaseLetter(char character) {
        int index = character - 'a';
        if (index < 0 || index >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}