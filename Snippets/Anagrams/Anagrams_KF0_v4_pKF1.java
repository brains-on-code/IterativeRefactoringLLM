package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;

/**
 * An anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
 * typically using all the original letters exactly once.[1]
 * For example, the word anagram itself can be rearranged into nag a ram,
 * also the word binary into brainy and the word adobe into abode.
 * Reference from https://en.wikipedia.org/wiki/Anagram
 */
public final class Anagrams {

    private Anagrams() {
    }

    /**
     * Checks if two strings are anagrams by sorting the characters and comparing them.
     * Time Complexity: O(n log n)
     * Space Complexity: O(n)
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
     * Checks if two strings are anagrams by counting the frequency of each character.
     * Assumes input consists of lowercase English letters a-z.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param firstString  the first string
     * @param secondString the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsByFixedArrayCount(String firstString, String secondString) {
        if (firstString.length() != secondString.length()) {
            return false;
        }

        int[] characterFrequencies = new int[26];

        for (int index = 0; index < firstString.length(); index++) {
            characterFrequencies[firstString.charAt(index) - 'a']++;
            characterFrequencies[secondString.charAt(index) - 'a']--;
        }

        for (int frequency : characterFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if two strings are anagrams using a HashMap to store character frequencies.
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     *
     * @param firstString  the first string
     * @param secondString the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsByMapCount(String firstString, String secondString) {
        if (firstString.length() != secondString.length()) {
            return false;
        }

        HashMap<Character, Integer> characterFrequencyMap = new HashMap<>();

        for (char character : firstString.toCharArray()) {
            characterFrequencyMap.put(character, characterFrequencyMap.getOrDefault(character, 0) + 1);
        }

        for (char character : secondString.toCharArray()) {
            Integer currentFrequency = characterFrequencyMap.get(character);
            if (currentFrequency == null || currentFrequency == 0) {
                return false;
            }
            characterFrequencyMap.put(character, currentFrequency - 1);
        }

        return characterFrequencyMap.values().stream().allMatch(frequency -> frequency == 0);
    }

    /**
     * Checks if two strings are anagrams using an array to track character frequencies.
     * This approach optimizes space complexity by using only one array.
     * Assumes input consists of lowercase English letters a-z.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param firstString  the first string
     * @param secondString the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsBySingleArray(String firstString, String secondString) {
        if (firstString.length() != secondString.length()) {
            return false;
        }

        int[] characterFrequencies = new int[26];

        for (int index = 0; index < firstString.length(); index++) {
            characterFrequencies[firstString.charAt(index) - 'a']++;
            characterFrequencies[secondString.charAt(index) - 'a']--;
        }

        for (int frequency : characterFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }
}