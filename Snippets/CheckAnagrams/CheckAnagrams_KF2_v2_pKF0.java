package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

public final class CheckAnagrams {

    private CheckAnagrams() {
        // Utility class; prevent instantiation
    }

    public static boolean isAnagrams(String first, String second) {
        if (!haveSameLengthNonNull(first, second)) {
            return false;
        }

        String normalizedFirst = first.toLowerCase();
        String normalizedSecond = second.toLowerCase();

        Map<Character, Integer> charCounts = buildCharCountMap(normalizedFirst);

        for (char c : normalizedSecond.toCharArray()) {
            Integer count = charCounts.get(c);
            if (count == null || count == 0) {
                return false;
            }
            charCounts.put(c, count - 1);
        }

        return allCountsZero(charCounts.values());
    }

    public static boolean isAnagramsUnicode(String first, String second) {
        if (!haveSameLengthNonNull(first, second)) {
            return false;
        }

        int[] counts = new int[128];

        for (char ch : first.toCharArray()) {
            counts[ch]++;
        }

        for (char ch : second.toCharArray()) {
            counts[ch]--;
        }

        return allCountsZero(counts);
    }

    public static boolean isAnagramsOptimised(String first, String second) {
        if (!haveSameLengthNonNull(first, second)) {
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

        return allCountsZero(counts);
    }

    private static boolean haveSameLengthNonNull(String first, String second) {
        return first != null && second != null && first.length() == second.length();
    }

    private static Map<Character, Integer> buildCharCountMap(String input) {
        Map<Character, Integer> charCounts = new HashMap<>();
        for (char c : input.toCharArray()) {
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }
        return charCounts;
    }

    private static boolean allCountsZero(Iterable<Integer> counts) {
        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean allCountsZero(int[] counts) {
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