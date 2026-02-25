package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Utility class for checking if two strings are anagrams of each other.
 */
public final class AnagramChecker {

    private AnagramChecker() {
    }

    /**
     * Checks if two strings are anagrams by sorting their characters and comparing.
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
     * Checks if two lowercase alphabetic strings are anagrams using a frequency array.
     *
     * @param first  the first string (assumed to contain 'a'–'z')
     * @param second the second string (assumed to contain 'a'–'z')
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsByFrequencyArray(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }
        int[] charCounts = new int[26];
        for (int index = 0; index < first.length(); index++) {
            charCounts[first.charAt(index) - 'a']++;
            charCounts[second.charAt(index) - 'a']--;
        }
        for (int count : charCounts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Alias of {@link #areAnagramsByFrequencyArray(String, String)} kept for backward compatibility.
     */
    public static boolean areAnagramsByFrequencyArrayAlt(String first, String second) {
        return areAnagramsByFrequencyArray(first, second);
    }

    /**
     * Checks if two strings are anagrams using a HashMap to count character frequencies.
     *
     * @param first  the first string
     * @param second the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsByHashMap(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }
        HashMap<Character, Integer> charFrequencyMap = new HashMap<>();
        for (char ch : first.toCharArray()) {
            charFrequencyMap.put(ch, charFrequencyMap.getOrDefault(ch, 0) + 1);
        }
        for (char ch : second.toCharArray()) {
            if (!charFrequencyMap.containsKey(ch) || charFrequencyMap.get(ch) == 0) {
                return false;
            }
            charFrequencyMap.put(ch, charFrequencyMap.get(ch) - 1);
        }
        return charFrequencyMap.values().stream().allMatch(count -> count == 0);
    }

    /**
     * Another implementation using a frequency array for lowercase alphabetic strings.
     *
     * @param first  the first string (assumed to contain 'a'–'z')
     * @param second the second string (assumed to contain 'a'–'z')
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagramsByFrequencyArrayAlt2(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }
        int[] charCounts = new int[26];
        for (int index = 0; index < first.length(); index++) {
            charCounts[first.charAt(index) - 'a']++;
            charCounts[second.charAt(index) - 'a']--;
        }
        for (int count : charCounts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }
}