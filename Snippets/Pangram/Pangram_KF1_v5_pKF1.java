package com.thealgorithms.strings;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for checking whether a string is a pangram.
 */
public final class PangramChecker {

    private static final int ALPHABET_SIZE = 26;
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

        Set<Character> distinctLetters = new HashSet<>();
        String normalizedText = text.trim().toLowerCase();

        for (int index = 0; index < normalizedText.length(); index++) {
            char currentCharacter = normalizedText.charAt(index);
            if (currentCharacter >= FIRST_LOWERCASE_LETTER && currentCharacter <= LAST_LOWERCASE_LETTER) {
                distinctLetters.add(currentCharacter);
            }
        }

        return distinctLetters.size() == ALPHABET_SIZE;
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

        boolean[] seenLetters = new boolean[ALPHABET_SIZE];

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char lowercaseCharacter = Character.toLowerCase(character);
                int alphabetIndex = lowercaseCharacter - FIRST_LOWERCASE_LETTER;
                if (alphabetIndex >= 0 && alphabetIndex < seenLetters.length) {
                    seenLetters[alphabetIndex] = true;
                }
            }
        }

        for (boolean letterSeen : seenLetters) {
            if (!letterSeen) {
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
        if (text == null || text.length() < ALPHABET_SIZE) {
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