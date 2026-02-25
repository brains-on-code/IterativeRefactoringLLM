package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

public final class CheckAnagrams {

    private CheckAnagrams() {
    }

    public static boolean areAnagrams(String first, String second) {
        String firstLower = first.toLowerCase();
        String secondLower = second.toLowerCase();

        if (firstLower.length() != secondLower.length()) {
            return false;
        }

        Map<Character, Integer> charCounts = new HashMap<>();

        for (int i = 0; i < firstLower.length(); i++) {
            char c = firstLower.charAt(i);
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < secondLower.length(); i++) {
            char c = secondLower.charAt(i);
            if (!charCounts.containsKey(c)) {
                return false;
            }
            charCounts.put(c, charCounts.get(c) - 1);
        }

        for (int count : charCounts.values()) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean areAnagramsUnicode(String first, String second) {
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

    public static boolean areAnagramsOptimized(String first, String second) {
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