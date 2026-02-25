package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for string-related algorithms.
 */
public final class Class1 {

    private static final int ASCII_SIZE = 128;
    private static final int ALPHABET_SIZE = 26;

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
        if (!areNonNullAndSameLength(first, second)) {
            return false;
        }

        String normalizedFirst = first.toLowerCase();
        String normalizedSecond = second.toLowerCase();

        Map<Character, Integer> charCounts = buildCharCountMap(normalizedFirst);

        for (int i = 0; i < normalizedSecond.length(); i++) {
            char currentChar = normalizedSecond.charAt(i);
            Integer count = charCounts.get(currentChar);

            if (count == null || count == 0) {
                return false;
            }

            charCounts.put(currentChar, count - 1);
        }

        return allCountsZero(charCounts);
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
        if (!areNonNullAndSameLength(first, second)) {
            return false;
        }

        int[] charCounts = new int[ASCII_SIZE];

        for (char c : first.toCharArray()) {
            validateAsciiCharacter(c);
            charCounts[c]++;
        }

        for (char c : second.toCharArray()) {
            validateAsciiCharacter(c);
            charCounts[c]--;
        }

        return allCountsZero(charCounts);
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
        if (!areNonNullAndSameLength(first, second)) {
            return false;
        }

        int[] charCounts = new int[ALPHABET_SIZE];

        for (char c : first.toCharArray()) {
            validateLowercaseLetter(c);
            charCounts[c - 'a']++;
        }

        for (char c : second.toCharArray()) {
            validateLowercaseLetter(c);
            charCounts[c - 'a']--;
        }

        return allCountsZero(charCounts);
    }

    private static boolean areNonNullAndSameLength(String first, String second) {
        return first != null && second != null && first.length() == second.length();
    }

    private static Map<Character, Integer> buildCharCountMap(String input) {
        Map<Character, Integer> charCounts = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            charCounts.put(currentChar, charCounts.getOrDefault(currentChar, 0) + 1);
        }
        return charCounts;
    }

    private static boolean allCountsZero(Map<Character, Integer> charCounts) {
        for (int count : charCounts.values()) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean allCountsZero(int[] charCounts) {
        for (int count : charCounts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    private static void validateAsciiCharacter(char c) {
        if (c >= ASCII_SIZE) {
            throw new IllegalArgumentException("Strings must contain only ASCII characters (0–127).");
        }
    }

    private static void validateLowercaseLetter(char c) {
        int index = c - 'a';
        if (index < 0 || index >= ALPHABET_SIZE) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters (a–z).");
        }
    }
}