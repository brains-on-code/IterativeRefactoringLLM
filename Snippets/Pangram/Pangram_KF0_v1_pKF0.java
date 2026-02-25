package com.thealgorithms.strings;

import java.util.HashSet;
import java.util.Set;

/**
 * Wikipedia: https://en.wikipedia.org/wiki/Pangram
 */
public final class Pangram {

    private static final int ALPHABET_COUNT = 26;

    private Pangram() {
        // Utility class; prevent instantiation
    }

    /**
     * Test code
     */
    public static void main(String[] args) {
        assert isPangram("The quick brown fox jumps over the lazy dog");
        assert !isPangram("The quick brown fox jumps over the azy dog"); // L is missing
        assert !isPangram("+-1234 This string is not alphabetical");
        assert !isPangram("\u0000/\\");
    }

    /**
     * Checks if a String is considered a Pangram using a Set.
     *
     * @param text The String to check
     * @return {@code true} if text is a Pangram, otherwise {@code false}
     */
    public static boolean isPangramUsingSet(String text) {
        if (text == null) {
            return false;
        }

        Set<Character> letters = new HashSet<>();
        for (char c : text.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                letters.add(c);
                if (letters.size() == ALPHABET_COUNT) {
                    return true;
                }
            }
        }
        return letters.size() == ALPHABET_COUNT;
    }

    /**
     * Checks if a String is considered a Pangram using a boolean array.
     *
     * @param text The String to check
     * @return {@code true} if text is a Pangram, otherwise {@code false}
     */
    public static boolean isPangram(String text) {
        if (text == null) {
            return false;
        }

        boolean[] lettersPresent = new boolean[ALPHABET_COUNT];
        int uniqueCount = 0;

        for (char c : text.toCharArray()) {
            char lower = Character.toLowerCase(c);
            if (lower < 'a' || lower > 'z') {
                continue;
            }

            int index = lower - 'a';
            if (!lettersPresent[index]) {
                lettersPresent[index] = true;
                uniqueCount++;
                if (uniqueCount == ALPHABET_COUNT) {
                    return true;
                }
            }
        }

        return uniqueCount == ALPHABET_COUNT;
    }

    /**
     * Checks if a String is Pangram or not by checking if each alphabet is present.
     *
     * @param text The String to check
     * @return {@code true} if text is a Pangram, otherwise {@code false}
     */
    public static boolean isPangram2(String text) {
        if (text == null || text.length() < ALPHABET_COUNT) {
            return false;
        }

        String lowerText = text.toLowerCase();
        for (char c = 'a'; c <= 'z'; c++) {
            if (lowerText.indexOf(c) == -1) {
                return false;
            }
        }
        return true;
    }
}