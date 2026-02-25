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
     * Determines if two strings are anagrams by:
     * <ol>
     *     <li>Converting each string to a character array</li>
     *     <li>Sorting both arrays</li>
     *     <li>Comparing the sorted arrays for equality</li>
     * </ol>
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
     * Determines if two lowercase alphabetic strings are anagrams using a
     * fixed-size frequency array for characters 'a' through 'z'.
     *
     * <p>For each index {@code i}, this method:
     * <ul>
     *     <li>Increments the count for {@code s.charAt(i)}</li>
     *     <li>Decrements the count for {@code t.charAt(i)}</li>
     * </ul>
     * After processing, all counts must be zero for the strings to be anagrams.
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
     * Delegates directly to {@link #approach2(String, String)}.
     *
     * @param s first string (must contain only characters 'a'–'z')
     * @param t second string (must contain only characters 'a'–'z')
     * @return {@code true} if {@code s} and {@code t} are anagrams; {@code false} otherwise
     */
    public static boolean approach3(String s, String t) {
        return approach2(s, t);
    }

    /**
     * Determines if two strings are anagrams using a {@link HashMap} to track
     * character frequencies. This approach supports general Unicode characters.
     *
     * <p>Processing steps:
     * <ol>
     *     <li>Count occurrences of each character in {@code s}</li>
     *     <li>Decrement the corresponding count for each character in {@code t}</li>
     *     <li>If any character in {@code t} is missing or overused, return {@code false}</li>
     *     <li>Finally, verify that all counts are zero</li>
     * </ol>
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
     * Another frequency-array-based approach for lowercase alphabetic strings.
     * Functionally equivalent to {@link #approach2(String, String)} but uses
     * different variable naming.
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