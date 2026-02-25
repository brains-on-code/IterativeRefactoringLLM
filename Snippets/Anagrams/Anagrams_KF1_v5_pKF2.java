package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility methods for checking whether two strings are anagrams.
 */
public final class AnagramChecker {

    private AnagramChecker() {
        // Prevent instantiation of utility class.
    }

    /**
     * Determines whether two strings are anagrams by sorting their characters
     * and comparing the sorted results.
     *
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if {@code first} and {@code second} are anagrams;
     *         {@code false} otherwise
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
     * Determines whether two strings are anagrams using a fixed-size frequency
     * array for characters {@code 'a'}–{@code 'z'}.
     *
     * <p>This method assumes both strings contain only lowercase letters
     * in the range {@code 'a'} to {@code 'z'}.</p>
     *
     * @param first  the first string (lowercase {@code 'a'}–{@code 'z'} only)
     * @param second the second string (lowercase {@code 'a'}–{@code 'z'} only)
     * @return {@code true} if {@code first} and {@code second} are anagrams;
     *         {@code false} otherwise
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
     * Determines whether two strings are anagrams using a {@link HashMap} to
     * count character frequencies. This method supports arbitrary Unicode
     * characters.
     *
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if {@code first} and {@code second} are anagrams;
     *         {@code false} otherwise
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