package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

public final class CheckAnagrams {

    private CheckAnagrams() {
    }

    public static boolean areAnagrams(String first, String second) {
        String normalizedFirst = first.toLowerCase();
        String normalizedSecond = second.toLowerCase();

        if (normalizedFirst.length() != normalizedSecond.length()) {
            return false;
        }

        Map<Character, Integer> charCounts = new HashMap<>();

        for (int i = 0; i < normalizedFirst.length(); i++) {
            char ch = normalizedFirst.charAt(i);
            int updatedCount = charCounts.getOrDefault(ch, 0) + 1;
            charCounts.put(ch, updatedCount);
        }

        for (int i = 0; i < normalizedSecond.length(); i++) {
            char ch = normalizedSecond.charAt(i);
            if (!charCounts.containsKey(ch)) {
                return false;
            }
            int updatedCount = charCounts.get(ch) - 1;
            charCounts.put(ch, updatedCount);
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

        for (char ch : first.toCharArray()) {
            charCounts[ch]++;
        }

        for (char ch : second.toCharArray()) {
            charCounts[ch]--;
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

        for (char ch : first.toCharArray()) {
            validateLowercaseLetter(ch);
            charCounts[ch - 'a']++;
        }

        for (char ch : second.toCharArray()) {
            validateLowercaseLetter(ch);
            charCounts[ch - 'a']--;
        }

        for (int count : charCounts) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    private static void validateLowercaseLetter(char ch) {
        int alphabetIndex = ch - 'a';
        if (alphabetIndex < 0 || alphabetIndex >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}