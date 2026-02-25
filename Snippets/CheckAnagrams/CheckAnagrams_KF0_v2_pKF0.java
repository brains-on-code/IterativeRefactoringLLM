package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for checking if two strings are anagrams.
 * Two strings are anagrams if they are made of the same letters arranged
 * differently (ignoring the case, unless specified otherwise).
 */
public final class CheckAnagrams {

    private static final int ASCII_LIMIT = 128;
    private static final int ALPHABET_SIZE = 26;

    private CheckAnagrams() {
        // Utility class; prevent instantiation
    }

    /**
     * Check if two strings are anagrams (case-insensitive).
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return {@code true} if two strings are anagrams, otherwise {@code false}
     */
    public static boolean isAnagrams(String s1, String s2) {
        if (!haveSameNonNullLength(s1, s2)) {
            return false;
        }

        String first = s1.toLowerCase();
        String second = s2.toLowerCase();

        Map<Character, Integer> charCounts = buildCharFrequencyMap(first);

        for (int i = 0; i < second.length(); i++) {
            char c = second.charAt(i);
            Integer count = charCounts.get(c);
            if (count == null || count == 0) {
                return false;
            }
            charCounts.put(c, count - 1);
        }

        return allCountsZero(charCounts);
    }

    /**
     * If given strings contain Unicode symbols.
     * The first 128 ASCII codes are identical to Unicode.
     * This algorithm is case-sensitive.
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return {@code true} if two strings are anagrams, otherwise {@code false}
     */
    public static boolean isAnagramsUnicode(String s1, String s2) {
        if (!haveSameNonNullLength(s1, s2)) {
            return false;
        }

        int[] dict = new int[ASCII_LIMIT];

        for (char ch : s1.toCharArray()) {
            validateAsciiChar(ch);
            dict[ch]++;
        }

        for (char ch : s2.toCharArray()) {
            validateAsciiChar(ch);
            dict[ch]--;
        }

        return allCountsZero(dict);
    }

    /**
     * If given strings contain only lowercase English letters.
     * <p>
     * The main "trick":
     * To map each character from the first string 's1' we need to subtract an integer value of 'a' character
     * as 'dict' array starts with 'a' character.
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return {@code true} if two strings are anagrams, otherwise {@code false}
     */
    public static boolean isAnagramsOptimised(String s1, String s2) {
        if (!haveSameNonNullLength(s1, s2)) {
            return false;
        }

        int[] dict = new int[ALPHABET_SIZE];

        for (char ch : s1.toCharArray()) {
            validateLowercaseLetter(ch);
            dict[ch - 'a']++;
        }

        for (char ch : s2.toCharArray()) {
            validateLowercaseLetter(ch);
            dict[ch - 'a']--;
        }

        return allCountsZero(dict);
    }

    private static boolean haveSameNonNullLength(String s1, String s2) {
        return s1 != null && s2 != null && s1.length() == s2.length();
    }

    private static Map<Character, Integer> buildCharFrequencyMap(String input) {
        Map<Character, Integer> charCounts = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
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

    private static boolean allCountsZero(int[] counts) {
        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    private static void validateAsciiChar(char ch) {
        if (ch >= ASCII_LIMIT) {
            throw new IllegalArgumentException("Only first 128 Unicode characters are supported.");
        }
    }

    private static void validateLowercaseLetter(char ch) {
        int index = ch - 'a';
        if (index < 0 || index >= ALPHABET_SIZE) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}