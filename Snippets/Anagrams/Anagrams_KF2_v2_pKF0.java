package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class Anagrams {

    private Anagrams() {
        // Utility class; prevent instantiation
    }

    /**
     * Approach 1: Sort both strings and compare.
     */
    public static boolean approach1(String first, String second) {
        if (!haveSameLength(first, second)) {
            return false;
        }

        char[] firstChars = first.toCharArray();
        char[] secondChars = second.toCharArray();

        Arrays.sort(firstChars);
        Arrays.sort(secondChars);

        return Arrays.equals(firstChars, secondChars);
    }

    /**
     * Approach 2: Count character frequencies using an array (assuming lowercase a-z).
     */
    public static boolean approach2(String first, String second) {
        return areAnagramsByFrequencyArray(first, second);
    }

    /**
     * Approach 3: Same as approach2 (kept for API compatibility).
     */
    public static boolean approach3(String first, String second) {
        return areAnagramsByFrequencyArray(first, second);
    }

    /**
     * Approach 4: Use a HashMap to count character frequencies.
     */
    public static boolean approach4(String first, String second) {
        if (!haveSameLength(first, second)) {
            return false;
        }

        Map<Character, Integer> charCountMap = new HashMap<>();

        for (char c : first.toCharArray()) {
            charCountMap.merge(c, 1, Integer::sum);
        }

        for (char c : second.toCharArray()) {
            Integer count = charCountMap.get(c);
            if (count == null || count == 0) {
                return false;
            }
            charCountMap.put(c, count - 1);
        }

        return charCountMap.values().stream().allMatch(count -> count == 0);
    }

    /**
     * Approach 5: Same logic as approach2/3 with a differently named array.
     */
    public static boolean approach5(String first, String second) {
        return areAnagramsByFrequencyArray(first, second);
    }

    private static boolean haveSameLength(String first, String second) {
        return first.length() == second.length();
    }

    private static boolean areAnagramsByFrequencyArray(String first, String second) {
        if (!haveSameLength(first, second)) {
            return false;
        }

        int[] charCount = buildFrequencyArray(first, second);
        return allZero(charCount);
    }

    /**
     * Builds a frequency array for characters assuming lowercase 'a' to 'z'.
     * Increments for characters in first and decrements for characters in second.
     */
    private static int[] buildFrequencyArray(String first, String second) {
        int[] charCount = new int[26];

        for (int i = 0; i < first.length(); i++) {
            charCount[first.charAt(i) - 'a']++;
            charCount[second.charAt(i) - 'a']--;
        }

        return charCount;
    }

    private static boolean allZero(int[] counts) {
        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }
}