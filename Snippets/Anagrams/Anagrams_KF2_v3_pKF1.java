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

        char[] firstCharacters = first.toCharArray();
        char[] secondCharacters = second.toCharArray();

        Arrays.sort(firstCharacters);
        Arrays.sort(secondCharacters);

        return Arrays.equals(firstCharacters, secondCharacters);
    }

    public static boolean areAnagramsCountArray1(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        int[] characterCounts = new int[ALPHABET_SIZE];

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

        int[] characterCounts = new int[ALPHABET_SIZE];

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

        Map<Character, Integer> characterCounts = new HashMap<>();

        for (char character : first.toCharArray()) {
            characterCounts.put(character, characterCounts.getOrDefault(character, 0) + 1);
        }

        for (char character : second.toCharArray()) {
            Integer currentCount = characterCounts.get(character);
            if (currentCount == null || currentCount == 0) {
                return false;
            }
            characterCounts.put(character, currentCount - 1);
        }

        return characterCounts.values().stream().allMatch(count -> count == 0);
    }

    public static boolean areAnagramsCountArray3(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }

        int[] characterCounts = new int[ALPHABET_SIZE];

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
}