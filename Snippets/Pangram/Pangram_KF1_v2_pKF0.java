package com.thealgorithms.strings;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for pangram-related operations.
 */
public final class PangramUtils {

    private PangramUtils() {
        // Prevent instantiation
    }

    /**
     * Simple self-test for pangram methods.
     */
    public static void main(String[] args) {
        assert isPangramWithBooleanArray("The quick brown fox jumps over the lazy dog");
        assert !isPangramWithBooleanArray("The quick brown fox jumps over the azy dog");
        assert !isPangramWithBooleanArray("+-1234 This string is not alphabetical");
        assert !isPangramWithBooleanArray("\u0000/\\");
    }

    /**
     * Checks if the given string is a pangram using a HashSet.
     *
     * @param text the input string
     * @return true if the string is a pangram, false otherwise
     */
    public static boolean isPangramWithSet(String text) {
        if (text == null) {
            return false;
        }

        Set<Character> uniqueLetters = new HashSet<>();
        String normalized = text.trim().toLowerCase();

        for (int i = 0; i < normalized.length(); i++) {
            char ch = normalized.charAt(i);
            if (Character.isLetter(ch)) {
                uniqueLetters.add(ch);
            }
        }

        return uniqueLetters.size() == 26;
    }

    /**
     * Checks if the given string is a pangram using a boolean array.
     *
     * @param text the input string
     * @return true if the string is a pangram, false otherwise
     */
    public static boolean isPangramWithBooleanArray(String text) {
        if (text == null) {
            return false;
        }

        boolean[] seenLetters = new boolean[26];

        for (char ch : text.toCharArray()) {
            if (!Character.isLetter(ch)) {
                continue;
            }

            int index = Character.toLowerCase(ch) - 'a';
            if (index >= 0 && index < seenLetters.length) {
                seenLetters[index] = true;
            }
        }

        for (boolean seen : seenLetters) {
            if (!seen) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the given string is a pangram using indexOf for each letter.
     *
     * @param text the input string
     * @return true if the string is a pangram, false otherwise
     */
    public static boolean isPangramWithIndexOf(String text) {
        if (text == null || text.length() < 26) {
            return false;
        }

        String normalized = text.toLowerCase();

        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (normalized.indexOf(ch) == -1) {
                return false;
            }
        }

        return true;
    }
}