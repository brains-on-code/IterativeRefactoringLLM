package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Two strings are anagrams if they are made of the same letters arranged
 * differently (ignoring the case).
 */
public final class CheckAnagrams {

    private CheckAnagrams() {
    }

    /**
     * Check if two strings are anagrams or not
     *
     * @param firstString  the first string
     * @param secondString the second string
     * @return {@code true} if two strings are anagrams, otherwise {@code false}
     */
    public static boolean areAnagrams(String firstString, String secondString) {
        String normalizedFirstString = firstString.toLowerCase();
        String normalizedSecondString = secondString.toLowerCase();

        if (normalizedFirstString.length() != normalizedSecondString.length()) {
            return false;
        }

        Map<Character, Integer> characterFrequencyMap = new HashMap<>();

        for (int index = 0; index < normalizedFirstString.length(); index++) {
            char character = normalizedFirstString.charAt(index);
            int updatedFrequency = characterFrequencyMap.getOrDefault(character, 0) + 1;
            characterFrequencyMap.put(character, updatedFrequency);
        }

        for (int index = 0; index < normalizedSecondString.length(); index++) {
            char character = normalizedSecondString.charAt(index);
            Integer currentFrequency = characterFrequencyMap.get(character);
            if (currentFrequency == null) {
                return false;
            }
            characterFrequencyMap.put(character, currentFrequency - 1);
        }

        for (int remainingFrequency : characterFrequencyMap.values()) {
            if (remainingFrequency != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * If given strings contain Unicode symbols.
     * The first 128 ASCII codes are identical to Unicode.
     * This algorithm is case-sensitive.
     *
     * @param firstString  the first string
     * @param secondString the second string
     * @return true if two strings are anagrams, otherwise false
     */
    public static boolean areAnagramsUnicode(String firstString, String secondString) {
        int[] asciiCharacterFrequencies = new int[128];

        for (char character : firstString.toCharArray()) {
            asciiCharacterFrequencies[character]++;
        }
        for (char character : secondString.toCharArray()) {
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
     * If given strings contain only lowercase English letters.
     * <p>
     * The main "trick":
     * To map each character from the first string 'firstString' we need to subtract an integer value of 'a' character
     * as 'letterCounts' array starts with 'a' character.
     *
     * @param firstString  the first string
     * @param secondString the second string
     * @return true if two strings are anagrams, otherwise false
     */
    public static boolean areAnagramsOptimized(String firstString, String secondString) {
        int[] letterFrequencies = new int[26];

        for (char character : firstString.toCharArray()) {
            validateLowercaseLetter(character);
            letterFrequencies[character - 'a']++;
        }
        for (char character : secondString.toCharArray()) {
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