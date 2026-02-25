package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Utility class for checking if two strings are anagrams of each other.
 */
public final class AnagramChecker {

    private static final int ALPHABET_SIZE = 26;
    private static final char LOWERCASE_A = 'a';

    private AnagramChecker() {
    }

    /**
     * Checks if two strings are anagrams by sorting their characters and comparing.
     *
     * @param firstString  the first string
     * @param secondString the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsBySorting(String firstString, String secondString) {
        if (firstString.length() != secondString.length()) {
            return false;
        }

        char[] firstCharacters = firstString.toCharArray();
        char[] secondCharacters = secondString.toCharArray();

        Arrays.sort(firstCharacters);
        Arrays.sort(secondCharacters);

        return Arrays.equals(firstCharacters, secondCharacters);
    }

    /**
     * Checks if two lowercase alphabetic strings are anagrams using a frequency array.
     *
     * @param firstString  the first string (assumed to contain 'a'–'z')
     * @param secondString the second string (assumed to contain 'a'–'z')
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsByFrequencyArray(String firstString, String secondString) {
        if (firstString.length() != secondString.length()) {
            return false;
        }

        int[] characterFrequencies = new int[ALPHABET_SIZE];

        for (int index = 0; index < firstString.length(); index++) {
            characterFrequencies[firstString.charAt(index) - LOWERCASE_A]++;
            characterFrequencies[secondString.charAt(index) - LOWERCASE_A]--;
        }

        for (int frequency : characterFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Alias of {@link #areAnagramsByFrequencyArray(String, String)} kept for backward compatibility.
     */
    public static boolean areAnagramsByFrequencyArrayAlt(String firstString, String secondString) {
        return areAnagramsByFrequencyArray(firstString, secondString);
    }

    /**
     * Checks if two strings are anagrams using a HashMap to count character frequencies.
     *
     * @param firstString  the first string
     * @param secondString the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsByHashMap(String firstString, String secondString) {
        if (firstString.length() != secondString.length()) {
            return false;
        }

        HashMap<Character, Integer> characterFrequencyMap = new HashMap<>();

        for (char character : firstString.toCharArray()) {
            characterFrequencyMap.put(character, characterFrequencyMap.getOrDefault(character, 0) + 1);
        }

        for (char character : secondString.toCharArray()) {
            Integer currentCount = characterFrequencyMap.get(character);
            if (currentCount == null || currentCount == 0) {
                return false;
            }
            characterFrequencyMap.put(character, currentCount - 1);
        }

        return characterFrequencyMap.values().stream().allMatch(count -> count == 0);
    }

    /**
     * Another implementation using a frequency array for lowercase alphabetic strings.
     *
     * @param firstString  the first string (assumed to contain 'a'–'z')
     * @param secondString the second string (assumed to contain 'a'–'z')
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsByFrequencyArrayAlt2(String firstString, String secondString) {
        if (firstString.length() != secondString.length()) {
            return false;
        }

        int[] characterFrequencies = new int[ALPHABET_SIZE];

        for (int index = 0; index < firstString.length(); index++) {
            characterFrequencies[firstString.charAt(index) - LOWERCASE_A]++;
            characterFrequencies[secondString.charAt(index) - LOWERCASE_A]--;
        }

        for (int frequency : characterFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }
}