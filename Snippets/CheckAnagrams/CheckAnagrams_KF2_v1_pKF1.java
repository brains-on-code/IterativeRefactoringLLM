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

        Map<Character, Integer> characterCounts = new HashMap<>();

        for (int i = 0; i < normalizedFirst.length(); i++) {
            char character = normalizedFirst.charAt(i);
            int count = characterCounts.getOrDefault(character, 0);
            characterCounts.put(character, count + 1);
        }

        for (int i = 0; i < normalizedSecond.length(); i++) {
            char character = normalizedSecond.charAt(i);
            if (!characterCounts.containsKey(character)) {
                return false;
            }
            characterCounts.put(character, characterCounts.get(character) - 1);
        }

        for (int count : characterCounts.values()) {
            if (count != 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean areAnagramsUnicode(String first, String second) {
        int[] characterFrequencies = new int[128];

        for (char character : first.toCharArray()) {
            characterFrequencies[character]++;
        }

        for (char character : second.toCharArray()) {
            characterFrequencies[character]--;
        }

        for (int frequency : characterFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean areAnagramsOptimized(String first, String second) {
        int[] characterFrequencies = new int[26];

        for (char character : first.toCharArray()) {
            validateLowercaseLetter(character);
            characterFrequencies[character - 'a']++;
        }

        for (char character : second.toCharArray()) {
            validateLowercaseLetter(character);
            characterFrequencies[character - 'a']--;
        }

        for (int frequency : characterFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }

    private static void validateLowercaseLetter(char character) {
        int index = character - 'a';
        if (index < 0 || index >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}