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
    public static boolean isAnagrams(String firstString, String secondString) {
        int firstLength = firstString.length();
        int secondLength = secondString.length();

        firstString = firstString.toLowerCase();
        secondString = secondString.toLowerCase();

        Map<Character, Integer> characterCounts = new HashMap<>();

        for (int i = 0; i < firstLength; i++) {
            char currentChar = firstString.charAt(i);
            int currentCount = characterCounts.getOrDefault(currentChar, 0);
            characterCounts.put(currentChar, currentCount + 1);
        }

        for (int i = 0; i < secondLength; i++) {
            char currentChar = secondString.charAt(i);
            if (!characterCounts.containsKey(currentChar)) {
                return false;
            }
            characterCounts.put(currentChar, characterCounts.get(currentChar) - 1);
        }

        for (int count : characterCounts.values()) {
            if (count != 0) {
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
    public static boolean isAnagramsUnicode(String firstString, String secondString) {
        int[] characterFrequencies = new int[128];

        for (char character : firstString.toCharArray()) {
            characterFrequencies[character]++;
        }
        for (char character : secondString.toCharArray()) {
            characterFrequencies[character]--;
        }
        for (int frequency : characterFrequencies) {
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
    public static boolean isAnagramsOptimised(String firstString, String secondString) {
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
        int index = character - 'a';
        if (index < 0 || index >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}