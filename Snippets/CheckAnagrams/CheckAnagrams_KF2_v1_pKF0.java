package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

public final class CheckAnagrams {

    private CheckAnagrams() {
        // Utility class; prevent instantiation
    }

    public static boolean isAnagrams(String first, String second) {
        if (first == null || second == null) {
            return false;
        }

        first = first.toLowerCase();
        second = second.toLowerCase();

        if (first.length() != second.length()) {
            return false;
        }

        Map<Character, Integer> charCounts = new HashMap<>();

        for (char c : first.toCharArray()) {
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }

        for (char c : second.toCharArray()) {
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

    public static boolean isAnagramsUnicode(String first, String second) {
        if (first == null || second == null || first.length() != second.length()) {
            return false;
        }

        int[] counts = new int[128];

        for (char ch : first.toCharArray()) {
            counts[ch]++;
        }

        for (char ch : second.toCharArray()) {
            counts[ch]--;
        }

        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAnagramsOptimised(String first, String second) {
        if (first == null || second == null || first.length() != second.length()) {
            return false;
        }

        int[] counts = new int[26];

        for (char ch : first.toCharArray()) {
            validateLowercaseLetter(ch);
            counts[ch - 'a']++;
        }

        for (char ch : second.toCharArray()) {
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

    private static void validateLowercaseLetter(char ch) {
        int index = ch - 'a';
        if (index < 0 || index >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}