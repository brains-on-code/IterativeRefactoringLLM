package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class Anagrams {

    private static final int ALPHABET_SIZE = 26;

    private Anagrams() {
    }

    public static boolean areAnagramsSorted(String firstWord, String secondWord) {
        if (firstWord.length() != secondWord.length()) {
            return false;
        }

        char[] firstChars = firstWord.toCharArray();
        char[] secondChars = secondWord.toCharArray();

        Arrays.sort(firstChars);
        Arrays.sort(secondChars);

        return Arrays.equals(firstChars, secondChars);
    }

    public static boolean areAnagramsCountArray1(String firstWord, String secondWord) {
        if (firstWord.length() != secondWord.length()) {
            return false;
        }

        int[] letterFrequencies = new int[ALPHABET_SIZE];

        for (int i = 0; i < firstWord.length(); i++) {
            letterFrequencies[firstWord.charAt(i) - 'a']++;
            letterFrequencies[secondWord.charAt(i) - 'a']--;
        }

        for (int frequency : letterFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean areAnagramsCountArray2(String firstWord, String secondWord) {
        if (firstWord.length() != secondWord.length()) {
            return false;
        }

        int[] letterFrequencies = new int[ALPHABET_SIZE];

        for (int i = 0; i < firstWord.length(); i++) {
            letterFrequencies[firstWord.charAt(i) - 'a']++;
            letterFrequencies[secondWord.charAt(i) - 'a']--;
        }

        for (int frequency : letterFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean areAnagramsHashMap(String firstWord, String secondWord) {
        if (firstWord.length() != secondWord.length()) {
            return false;
        }

        Map<Character, Integer> characterCounts = new HashMap<>();

        for (char character : firstWord.toCharArray()) {
            characterCounts.put(character, characterCounts.getOrDefault(character, 0) + 1);
        }

        for (char character : secondWord.toCharArray()) {
            Integer currentCount = characterCounts.get(character);
            if (currentCount == null || currentCount == 0) {
                return false;
            }
            characterCounts.put(character, currentCount - 1);
        }

        return characterCounts.values().stream().allMatch(count -> count == 0);
    }

    public static boolean areAnagramsCountArray3(String firstWord, String secondWord) {
        if (firstWord.length() != secondWord.length()) {
            return false;
        }

        int[] letterFrequencies = new int[ALPHABET_SIZE];

        for (int i = 0; i < firstWord.length(); i++) {
            letterFrequencies[firstWord.charAt(i) - 'a']++;
            letterFrequencies[secondWord.charAt(i) - 'a']--;
        }

        for (int frequency : letterFrequencies) {
            if (frequency != 0) {
                return false;
            }
        }

        return true;
    }
}