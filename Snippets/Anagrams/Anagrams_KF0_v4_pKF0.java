package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * An anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
 * typically using all the original letters exactly once.
 * For example, the word anagram itself can be rearranged into nag a ram,
 * also the word binary into brainy and the word adobe into abode.
 * Reference: https://en.wikipedia.org/wiki/Anagram
 */
public final class Anagrams {

    private static final int ALPHABET_SIZE = 26;

    private Anagrams() {
        // Utility class; prevent instantiation
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
    public static boolean approach1(String first, String second) {
        if (!haveSameLength(first, second)) {
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
    public static boolean approach2(String first, String second) {
        return areAnagramsWithArray(first, second);
    }

    /**
     * Checks if two strings are anagrams by counting the frequency of each character
     * using a single array.
     * Assumes input consists of lowercase English letters a-z.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param first  the first string
     * @param second the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean approach3(String first, String second) {
        return areAnagramsWithArray(first, second);
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
    public static boolean approach4(String first, String second) {
        if (!haveSameLength(first, second)) {
            return false;
        }

        Map<Character, Integer> charCountMap = buildFrequencyMap(first);

        for (char c : second.toCharArray()) {
            Integer count = charCountMap.get(c);
            if (count == null || count == 0) {
                return false;
            }
            charCountMap.put(c, count - 1);
        }

        return charCountMap.values().stream().allMatch(count -> count == 0);
    }

    /**
     * Checks if two strings are anagrams using an array to track character frequencies.
     * Assumes input consists of lowercase English letters a-z.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param first  the first string
     * @param second the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean approach5(String first, String second) {
        return areAnagramsWithArray(first, second);
    }

    private static boolean areAnagramsWithArray(String first, String second) {
        if (!haveSameLength(first, second)) {
            return false;
        }

        int[] charCount = new int[ALPHABET_SIZE];
        updateFrequencies(first, second, charCount);

        return allZero(charCount);
    }

    private static boolean haveSameLength(String first, String second) {
        if (first == null || second == null) {
            return false;
        }
        return first.length() == second.length();
    }

    private static boolean allZero(int[] counts) {
        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    private static void updateFrequencies(String first, String second, int[] counts) {
        for (int i = 0; i < first.length(); i++) {
            counts[first.charAt(i) - 'a']++;
            counts[second.charAt(i) - 'a']--;
        }
    }

    private static Map<Character, Integer> buildFrequencyMap(String input) {
        Map<Character, Integer> charCountMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            charCountMap.merge(c, 1, Integer::sum);
        }
        return charCountMap;
    }
}