package com.thealgorithms.strings;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for checking whether a string is a pangram.
 */
public final class PangramChecker {

    private static final int LETTERS_IN_ALPHABET = 26;
    private static final char FIRST_LOWERCASE_LETTER = 'a';
    private static final char LAST_LOWERCASE_LETTER = 'z';

    private PangramChecker() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        assert isPangramUsingBooleanArray("The quick brown fox jumps over the lazy dog");
        assert !isPangramUsingBooleanArray("The quick brown fox jumps over the azy dog");
        assert !isPangramUsingBooleanArray("+-1234 This string is not alphabetical");
        assert !isPangramUsingBooleanArray("\u0000/\\");
    }

    /**
     * Checks if the given string is a pangram using a Set of characters.
     *
     * @param text the string to check
     * @return true if the string is a pangram, false otherwise
     */
    public static boolean isPangramUsingSet(String text) {
        if (text == null) {
            return false;
        }

        Set<Character> uniqueLetters = new HashSet<>();
        String normalizedText = text.trim().toLowerCase();

        for (int i = 0; i < normalizedText.length(); i++) {
            char currentChar = normalizedText.charAt(i);
            if (currentChar >= FIRST_LOWERCASE_LETTER && currentChar <= LAST_LOWERCASE_LETTER) {
                uniqueLetters.add(currentChar);
            }
        }

        return uniqueLetters.size() == LETTERS_IN_ALPHABET;
    }

    /**
     * Checks if the given string is a pangram using a boolean array to track seen letters.
     *
     * @param text the string to check
     * @return true if the string is a pangram, false otherwise
     */
    public static boolean isPangramUsingBooleanArray(String text) {
        if (text == null) {
            return false;
        }

        boolean[] letterPresence = new boolean[LETTERS_IN_ALPHABET];

        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char lowercaseChar = Character.toLowerCase(ch);
                int letterIndex = lowercaseChar - FIRST_LOWERCASE_LETTER;
                if (letterIndex >= 0 && letterIndex < letterPresence.length) {
                    letterPresence[letterIndex] = true;
                }
            }
        }

        for (boolean isPresent : letterPresence) {
            if (!isPresent) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the given string is a pangram using indexOf for each alphabet letter.
     *
     * @param text the string to check
     * @return true if the string is a pangram, false otherwise
     */
    public static boolean isPangramUsingIndexOf(String text) {
        if (text == null || text.length() < LETTERS_IN_ALPHABET) {
            return false;
        }

        String normalizedText = text.toLowerCase();

        for (char letter = FIRST_LOWERCASE_LETTER; letter <= LAST_LOWERCASE_LETTER; letter++) {
            if (normalizedText.indexOf(letter) == -1) {
                return false;
            }
        }

        return true;
    }
}