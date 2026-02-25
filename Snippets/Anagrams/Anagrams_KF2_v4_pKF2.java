package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Utility class providing multiple approaches to check if two strings are anagrams.
 */
public final class Anagrams {

    private Anagrams() {
        // Prevent instantiation
    }

    /**
     * Checks if two strings are anagrams by sorting their characters and comparing.
     *
     * @param s first string
     * @param t second string
     * @return {@code true} if {@code s} and {@code t} are anagrams; {@code false} otherwise
     */
    public static boolean approach1(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        char[] firstChars = s.toCharArray();
        char[] secondChars = t.toCharArray();

        Arrays.sort(firstChars);
        Arrays.sort(secondChars);

        return Arrays.equals(firstChars, secondChars);
    }

    /**
     * Checks if two lowercase alphabetic strings are anagrams using a frequency array.
     * Assumes input consists only of characters 'a'–'z'.
     *
     * @param s first string (must contain only characters 'a'–'z')
     * @param t second string (must contain only characters 'a'–'z')
     * @return {@code true} if {@code s} and {@code t} are anagrams; {@code false} otherwise
     */
    public static boolean approach2(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] charCount = new int[26];

        for (int i = 0; i < s.length(); i++) {
            charCount[s.charAt(i) - 'a']++;
            charCount[t.charAt(i) - 'a']--;
        }

        for (int count : charCount) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Delegates to {@link #approach2(String, String)}.
     *
     * @param s first string (must contain only characters 'a'–'z')
     * @param t second string (must contain only characters 'a'–'z')
     * @return {@code true} if {@code s} and {@code t} are anagrams; {@code false} otherwise
     */
    public static boolean approach3(String s, String t) {
        return approach2(s, t);
    }

    /**
     * Checks if two strings are anagrams using a {@link HashMap} for character frequencies.
     * Supports general Unicode characters.
     *
     * @param s first string
     * @param t second string
     * @return {@code true} if {@code s} and {@code t} are anagrams; {@code false} otherwise
     */
    public static boolean approach4(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        HashMap<Character, Integer> charCountMap = new HashMap<>();

        for (char c : s.toCharArray()) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }

        for (char c : t.toCharArray()) {
            Integer currentCount = charCountMap.get(c);
            if (currentCount == null || currentCount == 0) {
                return false;
            }
            charCountMap.put(c, currentCount - 1);
        }

        return charCountMap.values().stream().allMatch(count -> count == 0);
    }

    /**
     * Frequency-array-based approach for lowercase alphabetic strings.
     * Functionally equivalent to {@link #approach2(String, String)}.
     *
     * @param s first string (must contain only characters 'a'–'z')
     * @param t second string (must contain only characters 'a'–'z')
     * @return {@code true} if {@code s} and {@code t} are anagrams; {@code false} otherwise
     */
    public static boolean approach5(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] freq = new int[26];

        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i) - 'a']++;
            freq[t.charAt(i) - 'a']--;
        }

        for (int count : freq) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }
}