package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Two strings are anagrams if they are made of the same letters arranged
 * differently (ignoring the case).
 */
public final class CheckAnagrams {

    private CheckAnagrams() {
    }

    /**
     * Check if two strings are anagrams or not
     *
     * @param firstString  the first string
     * @param secondString the second string
     * @return {@code true} if two strings are anagrams, otherwise {@code false}
     */
    public static boolean areAnagrams(String firstString, String secondString) {
        String normalizedFirst = firstString.toLowerCase();
        String normalizedSecond = secondString.toLowerCase();

        if (normalizedFirst.length() != normalizedSecond.length()) {
            return false;
        }

        Map<Character, Integer> charCounts = new HashMap<>();

        for (int i = 0; i < normalizedFirst.length(); i++) {
            char currentChar = normalizedFirst.charAt(i);
            int updatedCount = charCounts.getOrDefault(currentChar, 0) + 1;
            charCounts.put(currentChar, updatedCount);
        }

        for (int i = 0; i < normalizedSecond.length(); i++) {
            char currentChar = normalizedSecond.charAt(i);
            Integer currentCount = charCounts.get(currentChar);
            if (currentCount == null) {
                return false;
            }
            charCounts.put(currentChar, currentCount - 1);
        }

        for (int remainingCount : charCounts.values()) {
            if (remainingCount != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * If given strings contain Unicode symbols.
     * The first 128 ASCII codes are identical to Unicode.
     * This algorithm is case-sensitive.
     *
     * @param firstString  the first string
     * @param secondString the second string
     * @return true if two strings are anagrams, otherwise false
     */
    public static boolean areAnagramsUnicode(String firstString, String secondString) {
        int[] asciiCounts = new int[128];

        for (char currentChar : firstString.toCharArray()) {
            asciiCounts[currentChar]++;
        }
        for (char currentChar : secondString.toCharArray()) {
            asciiCounts[currentChar]--;
        }
        for (int count : asciiCounts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * If given strings contain only lowercase English letters.
     * <p>
     * The main "trick":
     * To map each character from the first string 'firstString' we need to subtract an integer value of 'a' character
     * as 'letterCounts' array starts with 'a' character.
     *
     * @param firstString  the first string
     * @param secondString the second string
     * @return true if two strings are anagrams, otherwise false
     */
    public static boolean areAnagramsOptimized(String firstString, String secondString) {
        int[] letterCounts = new int[26];

        for (char currentChar : firstString.toCharArray()) {
            validateLowercaseLetter(currentChar);
            letterCounts[currentChar - 'a']++;
        }
        for (char currentChar : secondString.toCharArray()) {
            validateLowercaseLetter(currentChar);
            letterCounts[currentChar - 'a']--;
        }
        for (int count : letterCounts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    private static void validateLowercaseLetter(char character) {
        int alphabetIndex = character - 'a';
        if (alphabetIndex < 0 || alphabetIndex >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}