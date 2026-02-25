package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class Anagrams {

    private static final int ALPHABET_SIZE = 26;

    private Anagrams() {
    }

    public static boolean areAnagramsSorted(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        char[] firstChars = first.toCharArray();
        char[] secondChars = second.toCharArray();

        Arrays.sort(firstChars);
        Arrays.sort(secondChars);

        return Arrays.equals(firstChars, secondChars);
    }

    public static boolean areAnagramsCountArray1(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        int[] charCounts = new int[ALPHABET_SIZE];

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

    public static boolean areAnagramsCountArray2(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        int[] charCounts = new int[ALPHABET_SIZE];

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

    public static boolean areAnagramsHashMap(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        Map<Character, Integer> charCounts = new HashMap<>();

        for (char c : first.toCharArray()) {
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }

        for (char c : second.toCharArray()) {
            Integer currentCount = charCounts.get(c);
            if (currentCount == null || currentCount == 0) {
                return false;
            }
            charCounts.put(c, currentCount - 1);
        }

        return charCounts.values().stream().allMatch(count -> count == 0);
    }

    public static boolean areAnagramsCountArray3(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        int[] charCounts = new int[ALPHABET_SIZE];

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