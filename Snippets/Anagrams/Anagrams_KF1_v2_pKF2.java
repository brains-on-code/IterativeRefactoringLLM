package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for checking whether two strings are anagrams of each other.
 */
public final class AnagramChecker {

    private AnagramChecker() {
        // Utility class; prevent instantiation.
    }

    /**
     * Checks if two strings are anagrams by sorting their characters and comparing.
     *
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
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
     * Checks if two lowercase alphabetic strings are anagrams using a fixed-size
     * frequency array (assuming characters 'a' to 'z').
     *
     * @param first  the first string (lowercase a–z)
     * @param second the second string (lowercase a–z)
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean areAnagramsByFrequencyArray(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        int[] charCounts = new int[26];

        for (int i = 0; i < first.length(); i++) {
            charCounts[first.charAt(i) - 'a']++;
            charCounts[second.charAt(i) - 'a']--;
        }

        for (int count : charCounts) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if two strings are anagrams using a {@link HashMap} to count
     * character frequencies. Works for general Unicode characters.
     *
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean areAnagramsByMap(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        Map<Character, Integer> charCounts = new HashMap<>();

        for (char c : first.toCharArray()) {
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }

        for (char c : second.toCharArray()) {
            Integer count = charCounts.get(c);
            if (count == null || count == 0) {
                return false;
            }
            charCounts.put(c, count - 1);
        }

        return charCounts.values().stream().allMatch(count -> count == 0);
    }
}