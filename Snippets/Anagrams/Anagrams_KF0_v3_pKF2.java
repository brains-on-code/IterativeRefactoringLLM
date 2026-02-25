package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Utility class for checking whether two strings are anagrams.
 *
 * <p>An anagram is a word or phrase formed by rearranging the letters of another word or phrase,
 * typically using all the original letters exactly once.
 *
 * <p>Reference: https://en.wikipedia.org/wiki/Anagram
 */
public final class Anagrams {

    private static final int ALPHABET_SIZE = 26;

    private Anagrams() {
        // Prevent instantiation
    }

    /**
     * Checks if two strings are anagrams by sorting their characters and comparing the results.
     *
     * <p>Time Complexity: O(n log n) due to sorting.
     * <p>Space Complexity: O(n) for the character arrays.
     *
     * @param s first string
     * @param t second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
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
     * Checks if two strings are anagrams by counting the frequency of each character
     * using a fixed-size array for lowercase English letters.
     *
     * <p>Assumes input consists of lowercase letters 'a' to 'z'.
     *
     * <p>Time Complexity: O(n).
     * <p>Space Complexity: O(1) (constant-size array).
     *
     * @param s first string
     * @param t second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean approach2(String s, String t) {
        return areAnagramsWithFixedArray(s, t);
    }

    /**
     * Same as {@link #approach2(String, String)}: checks if two strings are anagrams
     * by counting character frequencies with a single fixed-size array.
     *
     * @param s first string
     * @param t second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean approach3(String s, String t) {
        return areAnagramsWithFixedArray(s, t);
    }

    /**
     * Checks if two strings are anagrams using a {@link HashMap} to store character frequencies.
     *
     * <p>This approach works for arbitrary Unicode characters, not just lowercase English letters.
     *
     * <p>Time Complexity: O(n).
     * <p>Space Complexity: O(n) for the map.
     *
     * @param s first string
     * @param t second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean approach4(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        HashMap<Character, Integer> charCountMap = new HashMap<>();

        for (char c : s.toCharArray()) {
            charCountMap.merge(c, 1, Integer::sum);
        }

        for (char c : t.toCharArray()) {
            Integer count = charCountMap.get(c);
            if (count == null || count == 0) {
                return false;
            }
            charCountMap.put(c, count - 1);
        }

        return charCountMap.values().stream().allMatch(count -> count == 0);
    }

    /**
     * Another fixed-array frequency approach for lowercase English letters.
     *
     * <p>Functionally equivalent to {@link #approach2(String, String)} and
     * {@link #approach3(String, String)}.
     *
     * <p>Time Complexity: O(n).
     * <p>Space Complexity: O(1).
     *
     * @param s first string
     * @param t second string
     * @return {@code true} if the strings are anagrams, {@code false} otherwise
     */
    public static boolean approach5(String s, String t) {
        return areAnagramsWithFixedArray(s, t);
    }

    /**
     * Shared implementation for approaches that use a fixed-size array for lowercase letters.
     */
    private static boolean areAnagramsWithFixedArray(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] charCount = new int[ALPHABET_SIZE];

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
}