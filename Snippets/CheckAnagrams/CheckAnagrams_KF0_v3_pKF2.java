package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for checking whether two strings are anagrams.
 *
 * <p>Two strings are anagrams if they contain the same characters with the same
 * frequencies, possibly in a different order.
 */
public final class CheckAnagrams {

    private CheckAnagrams() {
        // Prevent instantiation
    }

    /**
     * Checks whether two strings are anagrams, ignoring case.
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return {@code true} if the strings are anagrams (case-insensitive),
     *         otherwise {@code false}
     */
    public static boolean isAnagrams(String s1, String s2) {
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
     * Checks whether two strings are anagrams using a fixed-size frequency
     * table for the first 128 Unicode code points (ASCII range).
     *
     * <p>This method is case-sensitive and assumes that all characters in the
     * input strings fall within the first 128 Unicode code points.
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return {@code true} if the strings are anagrams, otherwise {@code false}
     */
    public static boolean isAnagramsUnicode(String s1, String s2) {
        if (s1.length() != s2.length()) {
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
     * Checks whether two strings are anagrams assuming they contain only
     * lowercase English letters ({@code 'a'} to {@code 'z'}).
     *
     * <p>This method is case-sensitive and uses a fixed-size frequency table of
     * length 26, where index {@code 0} corresponds to {@code 'a'}, index
     * {@code 1} to {@code 'b'}, and so on.
     *
     * @param s1 the first string (only lowercase English letters allowed)
     * @param s2 the second string (only lowercase English letters allowed)
     * @return {@code true} if the strings are anagrams, otherwise {@code false}
     * @throws IllegalArgumentException if either string contains characters
     *         outside the range {@code 'a'} to {@code 'z'}
     */
    public static boolean isAnagramsOptimised(String s1, String s2) {
        if (s1.length() != s2.length()) {
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
     * Validates that the given character is a lowercase English letter
     * ({@code 'a'} to {@code 'z'}).
     *
     * @param ch the character to validate
     * @throws IllegalArgumentException if {@code ch} is not a lowercase
     *         English letter
     */
    private static void validateLowercaseLetter(char ch) {
        int index = ch - 'a';
        if (index < 0 || index >= 26) {
            throw new IllegalArgumentException(
                "Strings must contain only lowercase English letters (a-z)."
            );
        }
    }
}