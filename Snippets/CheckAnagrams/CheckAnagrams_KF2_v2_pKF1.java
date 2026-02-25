package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

public final class CheckAnagrams {

    private CheckAnagrams() {
    }

    public static boolean areAnagrams(String firstString, String secondString) {
        String normalizedFirstString = firstString.toLowerCase();
        String normalizedSecondString = secondString.toLowerCase();

        if (normalizedFirstString.length() != normalizedSecondString.length()) {
            return false;
        }

        Map<Character, Integer> characterFrequencyMap = new HashMap<>();

        for (int index = 0; index < normalizedFirstString.length(); index++) {
            char currentCharacter = normalizedFirstString.charAt(index);
            int updatedCount = characterFrequencyMap.getOrDefault(currentCharacter, 0) + 1;
            characterFrequencyMap.put(currentCharacter, updatedCount);
        }

        for (int index = 0; index < normalizedSecondString.length(); index++) {
            char currentCharacter = normalizedSecondString.charAt(index);
            if (!characterFrequencyMap.containsKey(currentCharacter)) {
                return false;
            }
            int updatedCount = characterFrequencyMap.get(currentCharacter) - 1;
            characterFrequencyMap.put(currentCharacter, updatedCount);
        }

        for (int frequency : characterFrequencyMap.values()) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean areAnagramsUnicode(String firstString, String secondString) {
        int[] characterFrequencies = new int[128];

        for (char currentCharacter : firstString.toCharArray()) {
            characterFrequencies[currentCharacter]++;
        }

        for (char currentCharacter : secondString.toCharArray()) {
            characterFrequencies[currentCharacter]--;
        }

        for (int frequency : characterFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean areAnagramsOptimized(String firstString, String secondString) {
        int[] characterFrequencies = new int[26];

        for (char currentCharacter : firstString.toCharArray()) {
            validateLowercaseLetter(currentCharacter);
            characterFrequencies[currentCharacter - 'a']++;
        }

        for (char currentCharacter : secondString.toCharArray()) {
            validateLowercaseLetter(currentCharacter);
            characterFrequencies[currentCharacter - 'a']--;
        }

        for (int frequency : characterFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }

    private static void validateLowercaseLetter(char character) {
        int alphabetIndex = character - 'a';
        if (alphabetIndex < 0 || alphabetIndex >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}