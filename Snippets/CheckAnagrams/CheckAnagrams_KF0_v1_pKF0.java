package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Two strings are anagrams if they are made of the same letters arranged
 * differently (ignoring the case).
 */
public final class CheckAnagrams {

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
        if (s1 == null || s2 == null) {
            return false;
        }

        if (s1.length() != s2.length()) {
            return false;
        }

        String first = s1.toLowerCase();
        String second = s2.toLowerCase();

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
     * If given strings contain Unicode symbols.
     * The first 128 ASCII codes are identical to Unicode.
     * This algorithm is case-sensitive.
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return {@code true} if two strings are anagrams, otherwise {@code false}
     */
    public static boolean isAnagramsUnicode(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }

        int[] dict = new int[128];

        for (char ch : s1.toCharArray()) {
            if (ch >= 128) {
                throw new IllegalArgumentException("Only first 128 Unicode characters are supported.");
            }
            dict[ch]++;
        }

        for (char ch : s2.toCharArray()) {
            if (ch >= 128) {
                throw new IllegalArgumentException("Only first 128 Unicode characters are supported.");
            }
            dict[ch]--;
        }

        for (int count : dict) {
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
     * To map each character from the first string 's1' we need to subtract an integer value of 'a' character
     * as 'dict' array starts with 'a' character.
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return {@code true} if two strings are anagrams, otherwise {@code false}
     */
    public static boolean isAnagramsOptimised(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }

        int[] dict = new int[26];

        for (char ch : s1.toCharArray()) {
            validateLowercaseLetter(ch);
            dict[ch - 'a']++;
        }

        for (char ch : s2.toCharArray()) {
            validateLowercaseLetter(ch);
            dict[ch - 'a']--;
        }

        for (int count : dict) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    private static void validateLowercaseLetter(char ch) {
        int index = ch - 'a';
        if (index < 0 || index >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}