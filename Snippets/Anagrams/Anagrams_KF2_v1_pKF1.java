package com.thealgorithms.strings;

import java.util.Arrays;
import java.util.HashMap;

public final class Anagrams {

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
        int[] characterCounts = new int[26];
        for (int index = 0; index < first.length(); index++) {
            characterCounts[first.charAt(index) - 'a']++;
            characterCounts[second.charAt(index) - 'a']--;
        }
        for (int count : characterCounts) {
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
        int[] characterCounts = new int[26];
        for (int index = 0; index < first.length(); index++) {
            characterCounts[first.charAt(index) - 'a']++;
            characterCounts[second.charAt(index) - 'a']--;
        }
        for (int count : characterCounts) {
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
        HashMap<Character, Integer> characterCountMap = new HashMap<>();
        for (char character : first.toCharArray()) {
            characterCountMap.put(character, characterCountMap.getOrDefault(character, 0) + 1);
        }
        for (char character : second.toCharArray()) {
            if (!characterCountMap.containsKey(character) || characterCountMap.get(character) == 0) {
                return false;
            }
            characterCountMap.put(character, characterCountMap.get(character) - 1);
        }
        return characterCountMap.values().stream().allMatch(count -> count == 0);
    }

    public static boolean areAnagramsCountArray3(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }
        int[] characterFrequencies = new int[26];
        for (int index = 0; index < first.length(); index++) {
            characterFrequencies[first.charAt(index) - 'a']++;
            characterFrequencies[second.charAt(index) - 'a']--;
        }
        for (int count : characterFrequencies) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }
}