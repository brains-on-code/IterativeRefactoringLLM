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
     * @param first  the first string
     * @param second the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsBySorting(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        char[] firstChars = first.toCharArray();
        char[] secondChars = second.toCharArray();

        Arrays.sort(firstChars);
        Arrays.sort(secondChars);

        return Arrays.equals(firstChars, secondChars);
    }

    /**
     * Checks if two strings are anagrams by counting the frequency of each character.
     * Assumes input consists of lowercase English letters a-z.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param first  the first string
     * @param second the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsByFixedArrayCount(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        int[] characterFrequencies = new int[26];

        for (int index = 0; index < first.length(); index++) {
            characterFrequencies[first.charAt(index) - 'a']++;
            characterFrequencies[second.charAt(index) - 'a']--;
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
     * @param first  the first string
     * @param second the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsByMapCount(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        HashMap<Character, Integer> characterCountMap = new HashMap<>();

        for (char character : first.toCharArray()) {
            characterCountMap.put(character, characterCountMap.getOrDefault(character, 0) + 1);
        }

        for (char character : second.toCharArray()) {
            Integer currentCount = characterCountMap.get(character);
            if (currentCount == null || currentCount == 0) {
                return false;
            }
            characterCountMap.put(character, currentCount - 1);
        }

        return characterCountMap.values().stream().allMatch(count -> count == 0);
    }

    /**
     * Checks if two strings are anagrams using an array to track character frequencies.
     * This approach optimizes space complexity by using only one array.
     * Assumes input consists of lowercase English letters a-z.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param first  the first string
     * @param second the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsBySingleArray(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        int[] characterFrequencies = new int[26];

        for (int index = 0; index < first.length(); index++) {
            characterFrequencies[first.charAt(index) - 'a']++;
            characterFrequencies[second.charAt(index) - 'a']--;
        }

        for (int frequency : characterFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }
}