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
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if two strings are anagrams, otherwise {@code false}
     */
    public static boolean isAnagrams(String first, String second) {
        if (!haveSameNonNullLength(first, second)) {
            return false;
        }

        String normalizedFirst = first.toLowerCase();
        String normalizedSecond = second.toLowerCase();

        Map<Character, Integer> charCounts = buildCharFrequencyMap(normalizedFirst);

        for (int i = 0; i < normalizedSecond.length(); i++) {
            char currentChar = normalizedSecond.charAt(i);
            Integer count = charCounts.get(currentChar);
            if (count == null || count == 0) {
                return false;
            }
            charCounts.put(currentChar, count - 1);
        }

        return allCountsZero(charCounts.values());
    }

    /**
     * Check if two strings are anagrams when they may contain Unicode symbols.
     * The first 128 ASCII codes are identical to Unicode.
     * This algorithm is case-sensitive.
     *
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if two strings are anagrams, otherwise {@code false}
     */
    public static boolean isAnagramsUnicode(String first, String second) {
        if (!haveSameNonNullLength(first, second)) {
            return false;
        }

        int[] charFrequencies = new int[ASCII_LIMIT];

        for (char ch : first.toCharArray()) {
            validateAsciiChar(ch);
            charFrequencies[ch]++;
        }

        for (char ch : second.toCharArray()) {
            validateAsciiChar(ch);
            charFrequencies[ch]--;
        }

        return allCountsZero(charFrequencies);
    }

    /**
     * Check if two strings are anagrams when they contain only lowercase English letters.
     * <p>
     * The main "trick":
     * To map each character from the first string we subtract the integer value of 'a'
     * because the 'dict' array starts with the 'a' character.
     *
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if two strings are anagrams, otherwise {@code false}
     */
    public static boolean isAnagramsOptimised(String first, String second) {
        if (!haveSameNonNullLength(first, second)) {
            return false;
        }

        int[] charFrequencies = new int[ALPHABET_SIZE];

        for (char ch : first.toCharArray()) {
            validateLowercaseLetter(ch);
            charFrequencies[ch - 'a']++;
        }

        for (char ch : second.toCharArray()) {
            validateLowercaseLetter(ch);
            charFrequencies[ch - 'a']--;
        }

        return allCountsZero(charFrequencies);
    }

    private static boolean haveSameNonNullLength(String first, String second) {
        return first != null && second != null && first.length() == second.length();
    }

    private static Map<Character, Integer> buildCharFrequencyMap(String input) {
        Map<Character, Integer> charCounts = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            charCounts.put(currentChar, charCounts.getOrDefault(currentChar, 0) + 1);
        }
        return charCounts;
    }

    private static boolean allCountsZero(Iterable<Integer> counts) {
        for (int count : counts) {
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