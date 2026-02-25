package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

public final class CheckAnagrams {

    private CheckAnagrams() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if two strings are anagrams of each other (case-insensitive).
     *
     * @param s1 first string
     * @param s2 second string
     * @return true if s1 and s2 are anagrams, false otherwise
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
     * Checks if two strings are anagrams using a fixed-size ASCII table (0–127).
     * Assumes input consists only of ASCII characters.
     *
     * @param s1 first string
     * @param s2 second string
     * @return true if s1 and s2 are anagrams, false otherwise
     */
    public static boolean isAnagramsUnicode(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }

        int[] counts = new int[128];

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
     * Checks if two strings are anagrams assuming only lowercase English letters (a–z).
     *
     * @param s1 first string (must contain only 'a'–'z')
     * @param s2 second string (must contain only 'a'–'z')
     * @return true if s1 and s2 are anagrams, false otherwise
     * @throws IllegalArgumentException if any character is outside 'a'–'z'
     */
    public static boolean isAnagramsOptimised(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }

        int[] counts = new int[26];

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
     * Validates that the given character is a lowercase English letter (a–z).
     *
     * @param ch character to validate
     * @throws IllegalArgumentException if ch is not in the range 'a'–'z'
     */
    private static void validateLowercaseLetter(char ch) {
        int index = ch - 'a';
        if (index < 0 || index >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters (a–z).");
        }
    }
}