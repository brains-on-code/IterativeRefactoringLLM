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
        String normalizedFirst = firstString.toLowerCase();
        String normalizedSecond = secondString.toLowerCase();

        if (normalizedFirst.length() != normalizedSecond.length()) {
            return false;
        }

        Map<Character, Integer> characterCounts = new HashMap<>();

        for (int i = 0; i < normalizedFirst.length(); i++) {
            char currentChar = normalizedFirst.charAt(i);
            int updatedCount = characterCounts.getOrDefault(currentChar, 0) + 1;
            characterCounts.put(currentChar, updatedCount);
        }

        for (int i = 0; i < normalizedSecond.length(); i++) {
            char currentChar = normalizedSecond.charAt(i);
            Integer currentCount = characterCounts.get(currentChar);
            if (currentCount == null) {
                return false;
            }
            characterCounts.put(currentChar, currentCount - 1);
        }

        for (int remainingCount : characterCounts.values()) {
            if (remainingCount != 0) {
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
        int[] asciiFrequencies = new int[128];

        for (char character : firstString.toCharArray()) {
            asciiFrequencies[character]++;
        }
        for (char character : secondString.toCharArray()) {
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
     * If given strings contain only lowercase English letters.
     * <p>
     * The main "trick":
     * To map each character from the first string 'firstString' we need to subtract an integer value of 'a' character
     * as 'letterFrequencies' array starts with 'a' character.
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