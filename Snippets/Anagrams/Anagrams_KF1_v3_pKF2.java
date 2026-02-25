package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility methods for checking whether two strings are anagrams.
 */
public final class AnagramChecker {

    private AnagramChecker() {
        // Prevent instantiation.
    }

    /**
     * Returns {@code true} if {@code first} and {@code second} are anagrams.
     * <p>
     * Implementation detail: converts both strings to character arrays, sorts
     * them, and compares the sorted arrays.
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
     * Returns {@code true} if {@code first} and {@code second} are anagrams.
     * <p>
     * Assumes both strings consist only of lowercase letters {@code 'a'}–{@code 'z'}.
     * Uses a fixed-size frequency array to count character occurrences.
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
     * Returns {@code true} if {@code first} and {@code second} are anagrams.
     * <p>
     * Uses a {@link HashMap} to count character frequencies and supports
     * arbitrary Unicode characters.
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