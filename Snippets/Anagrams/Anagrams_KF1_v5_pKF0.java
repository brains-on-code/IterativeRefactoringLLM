package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for checking if two strings are anagrams of each other.
 */
public final class AnagramChecker {

    private AnagramChecker() {
        // Utility class; prevent instantiation
    }

    private static boolean haveSameNonNullLength(String first, String second) {
        return first != null && second != null && first.length() == second.length();
    }

    /**
     * Checks if two strings are anagrams by sorting their characters and comparing.
     *
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean areAnagramsBySorting(String first, String second) {
        if (!haveSameNonNullLength(first, second)) {
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
     * @param first  the first string (assumed to contain only 'a'–'z')
     * @param second the second string (assumed to contain only 'a'–'z')
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean areAnagramsWithFrequencyArray(String first, String second) {
        if (!haveSameNonNullLength(first, second)) {
            return false;
        }

        int[] charFrequencies = new int[26];
        int length = first.length();

        for (int i = 0; i < length; i++) {
            charFrequencies[first.charAt(i) - 'a']++;
            charFrequencies[second.charAt(i) - 'a']--;
        }

        for (int frequency : charFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if two strings are anagrams using a {@link HashMap} to count character frequencies.
     *
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean areAnagramsWithMap(String first, String second) {
        if (!haveSameNonNullLength(first, second)) {
            return false;
        }

        Map<Character, Integer> charCounts = new HashMap<>();

        for (char c : first.toCharArray()) {
            charCounts.merge(c, 1, Integer::sum);
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