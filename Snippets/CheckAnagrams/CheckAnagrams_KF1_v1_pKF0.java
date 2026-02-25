package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for string-related algorithms.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Checks if two strings are anagrams of each other (case-insensitive) using a HashMap.
     *
     * @param first  the first string
     * @param second the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean method1(String first, String second) {
        if (first == null || second == null) {
            return false;
        }

        first = first.toLowerCase();
        second = second.toLowerCase();

        if (first.length() != second.length()) {
            return false;
        }

        Map<Character, Integer> charCounts = new HashMap<>();

        for (int i = 0; i < first.length(); i++) {
            char c = first.charAt(i);
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < second.length(); i++) {
            char c = second.charAt(i);
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
     * Checks if two strings are anagrams of each other using an ASCII frequency array.
     * Assumes input consists of standard ASCII characters (0–127).
     *
     * @param first  the first string
     * @param second the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean method2(String first, String second) {
        if (first == null || second == null) {
            return false;
        }

        if (first.length() != second.length()) {
            return false;
        }

        int[] charCounts = new int[128];

        for (char c : first.toCharArray()) {
            if (c >= 128) {
                throw new IllegalArgumentException("Strings must contain only ASCII characters (0–127).");
            }
            charCounts[c]++;
        }

        for (char c : second.toCharArray()) {
            if (c >= 128) {
                throw new IllegalArgumentException("Strings must contain only ASCII characters (0–127).");
            }
            charCounts[c]--;
        }

        for (int count : charCounts) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if two strings are anagrams of each other using a frequency array
     * for lowercase English letters only ('a'–'z').
     *
     * @param first  the first string (must contain only 'a'–'z')
     * @param second the second string (must contain only 'a'–'z')
     * @return true if the strings are anagrams, false otherwise
     * @throws IllegalArgumentException if either string contains characters outside 'a'–'z'
     */
    public static boolean method3(String first, String second) {
        if (first == null || second == null) {
            return false;
        }

        if (first.length() != second.length()) {
            return false;
        }

        int[] charCounts = new int[26];

        for (char c : first.toCharArray()) {
            validateLowercaseLetter(c);
            charCounts[c - 'a']++;
        }

        for (char c : second.toCharArray()) {
            validateLowercaseLetter(c);
            charCounts[c - 'a']--;
        }

        for (int count : charCounts) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    private static void validateLowercaseLetter(char c) {
        int index = c - 'a';
        if (index < 0 || index >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters (a–z).");
        }
    }
}