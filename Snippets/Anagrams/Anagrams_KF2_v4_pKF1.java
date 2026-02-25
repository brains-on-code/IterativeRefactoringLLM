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

        char[] firstWordChars = firstWord.toCharArray();
        char[] secondWordChars = secondWord.toCharArray();

        Arrays.sort(firstWordChars);
        Arrays.sort(secondWordChars);

        return Arrays.equals(firstWordChars, secondWordChars);
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

        Map<Character, Integer> letterFrequencies = new HashMap<>();

        for (char character : firstWord.toCharArray()) {
            letterFrequencies.put(character, letterFrequencies.getOrDefault(character, 0) + 1);
        }

        for (char character : secondWord.toCharArray()) {
            Integer currentFrequency = letterFrequencies.get(character);
            if (currentFrequency == null || currentFrequency == 0) {
                return false;
            }
            letterFrequencies.put(character, currentFrequency - 1);
        }

        return letterFrequencies.values().stream().allMatch(frequency -> frequency == 0);
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