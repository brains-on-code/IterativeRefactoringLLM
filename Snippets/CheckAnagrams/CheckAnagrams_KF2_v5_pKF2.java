package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

public final class CheckAnagrams {

    private static final int ASCII_SIZE = 128;
    private static final int ALPHABET_SIZE = 26;

    private CheckAnagrams() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether two strings are anagrams of each other, ignoring case.
     *
     * @param s1 first string
     * @param s2 second string
     * @return {@code true} if {@code s1} and {@code s2} are anagrams;
     *         {@code false} otherwise
     */
    public static boolean isAnagrams(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }

        String first = s1.toLowerCase();
        String second = s2.toLowerCase();

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

        for (int count : charCounts.values()) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether two strings are anagrams of each other, assuming both
     * consist only of ASCII characters (code points 0–127).
     *
     * @param s1 first string
     * @param s2 second string
     * @return {@code true} if {@code s1} and {@code s2} are anagrams;
     *         {@code false} otherwise
     */
    public static boolean isAnagramsUnicode(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }

        int[] counts = new int[ASCII_SIZE];

        for (char ch : s1.toCharArray()) {
            counts[ch]++;
        }

        for (char ch : s2.toCharArray()) {
            counts[ch]--;
        }

        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether two strings are anagrams of each other, assuming both
     * consist only of lowercase English letters {@code 'a'–'z'}.
     *
     * @param s1 first string (must contain only {@code 'a'–'z'})
     * @param s2 second string (must contain only {@code 'a'–'z'})
     * @return {@code true} if {@code s1} and {@code s2} are anagrams;
     *         {@code false} otherwise
     * @throws IllegalArgumentException if any character is outside {@code 'a'–'z'}
     */
    public static boolean isAnagramsOptimised(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }

        int[] counts = new int[ALPHABET_SIZE];

        for (char ch : s1.toCharArray()) {
            validateLowercaseLetter(ch);
            counts[ch - 'a']++;
        }

        for (char ch : s2.toCharArray()) {
            validateLowercaseLetter(ch);
            counts[ch - 'a']--;
        }

        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Ensures that the given character is a lowercase English letter
     * in the range {@code 'a'–'z'}.
     *
     * @param ch character to validate
     * @throws IllegalArgumentException if {@code ch} is not in the range {@code 'a'–'z'}
     */
    private static void validateLowercaseLetter(char ch) {
        int index = ch - 'a';
        if (index < 0 || index >= ALPHABET_SIZE) {
            throw new IllegalArgumentException(
                "Strings must contain only lowercase English letters (a–z)."
            );
        }
    }
}