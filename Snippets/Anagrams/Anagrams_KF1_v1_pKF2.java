package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Utility class for checking whether two strings are anagrams of each other.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Checks if two strings are anagrams by sorting their characters and comparing.
     *
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean method1(String first, String second) {
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
     * Checks if two lowercase alphabetic strings are anagrams using a fixed-size
     * frequency array (assuming characters 'a' to 'z').
     *
     * @param first  the first string (lowercase a–z)
     * @param second the second string (lowercase a–z)
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean method2(String first, String second) {
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
     * Checks if two lowercase alphabetic strings are anagrams using a fixed-size
     * frequency array (assuming characters 'a' to 'z').
     *
     * @param first  the first string (lowercase a–z)
     * @param second the second string (lowercase a–z)
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean method3(String first, String second) {
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
     * Checks if two strings are anagrams using a {@link HashMap} to count
     * character frequencies. Works for general Unicode characters.
     *
     * @param first  the first string
     * @param second the second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean method4(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }
        HashMap<Character, Integer> charCounts = new HashMap<>();
        for (char c : first.toCharArray()) {
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }
        for (char c : second.toCharArray()) {
            if (!charCounts.containsKey(c) || charCounts.get(c) == 0) {
                return false;
            }
            charCounts.put(c, charCounts.get(c) - 1);
        }
        return charCounts.values().stream().allMatch(count -> count == 0);
    }

    /**
     * Checks if two lowercase alphabetic strings are anagrams using a fixed-size
     * frequency array (assuming characters 'a' to 'z').
     *
     * @param first  the first string (lowercase a–z)
     * @param second the second string (lowercase a–z)
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean method5(String first, String second) {
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
}