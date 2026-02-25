package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;

public final class Anagrams {

    private Anagrams() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if two strings are anagrams by sorting their characters
     * and comparing the sorted arrays.
     *
     * @param s first string
     * @param t second string
     * @return true if s and t are anagrams, false otherwise
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
     * Checks if two lowercase alphabetic strings are anagrams by counting
     * character frequencies using a fixed-size array.
     *
     * @param s first string (assumed to contain 'a'–'z')
     * @param t second string (assumed to contain 'a'–'z')
     * @return true if s and t are anagrams, false otherwise
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
     * Same logic as approach2: uses a frequency array for lowercase letters.
     *
     * @param s first string (assumed to contain 'a'–'z')
     * @param t second string (assumed to contain 'a'–'z')
     * @return true if s and t are anagrams, false otherwise
     */
    public static boolean approach3(String s, String t) {
        return approach2(s, t);
    }

    /**
     * Checks if two strings are anagrams using a HashMap to count
     * character frequencies. Works for general Unicode characters.
     *
     * @param s first string
     * @param t second string
     * @return true if s and t are anagrams, false otherwise
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
     * Another frequency-array-based approach for lowercase alphabetic strings.
     *
     * @param s first string (assumed to contain 'a'–'z')
     * @param t second string (assumed to contain 'a'–'z')
     * @return true if s and t are anagrams, false otherwise
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