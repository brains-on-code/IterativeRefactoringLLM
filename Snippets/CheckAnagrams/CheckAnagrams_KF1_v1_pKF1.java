package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for string-related algorithms.
 */
public final class Class1 {

    private Class1() {
    }

    /**
     * Checks if two strings are anagrams of each other (case-insensitive)
     * using a character frequency map.
     */
    public static boolean method1(String first, String second) {
        int firstLength = first.length();
        int secondLength = second.length();

        first = first.toLowerCase();
        second = second.toLowerCase();

        Map<Character, Integer> charFrequency = new HashMap<>();

        for (int i = 0; i < firstLength; i++) {
            char currentChar = first.charAt(i);
            int count = charFrequency.getOrDefault(currentChar, 0);
            charFrequency.put(currentChar, count + 1);
        }

        for (int i = 0; i < secondLength; i++) {
            char currentChar = second.charAt(i);
            if (!charFrequency.containsKey(currentChar)) {
                return false;
            }
            charFrequency.put(currentChar, charFrequency.get(currentChar) - 1);
        }

        for (int count : charFrequency.values()) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if two strings are anagrams of each other assuming ASCII characters,
     * using a fixed-size frequency array.
     */
    public static boolean method2(String first, String second) {
        int[] charCounts = new int[128];

        for (char c : first.toCharArray()) {
            charCounts[c]++;
        }
        for (char c : second.toCharArray()) {
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
     * Checks if two strings are anagrams of each other assuming only
     * lowercase English letters ('a' to 'z').
     */
    public static boolean method3(String first, String second) {
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
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}