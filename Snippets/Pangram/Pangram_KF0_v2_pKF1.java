package com.thealgorithms.strings;

import java.util.HashSet;
import java.util.Set;

/**
 * Wikipedia: https://en.wikipedia.org/wiki/Pangram
 */
public final class Pangram {

    private static final int ALPHABET_COUNT = 26;
    private static final char FIRST_LOWERCASE_LETTER = 'a';
    private static final char FIRST_UPPERCASE_LETTER = 'A';

    private Pangram() {
    }

    public static void main(String[] args) {
        assert isPangram("The quick brown fox jumps over the lazy dog");
        assert !isPangram("The quick brown fox jumps over the azy dog"); // L is missing
        assert !isPangram("+-1234 This string is not alphabetical");
        assert !isPangram("\u0000/\\");
    }

    /**
     * Checks if a String is considered a Pangram using a Set.
     *
     * @param input The String to check
     * @return {@code true} if input is a Pangram, otherwise {@code false}
     */
    public static boolean isPangramUsingSet(String input) {
        Set<Character> uniqueLetters = new HashSet<>();
        String normalizedInput = input.trim().toLowerCase();

        for (int index = 0; index < normalizedInput.length(); index++) {
            char currentCharacter = normalizedInput.charAt(index);
            if (currentCharacter != ' ') {
                uniqueLetters.add(currentCharacter);
            }
        }

        return uniqueLetters.size() == ALPHABET_COUNT;
    }

    /**
     * Checks if a String is considered a Pangram.
     *
     * @param input The String to check
     * @return {@code true} if input is a Pangram, otherwise {@code false}
     */
    public static boolean isPangram(String input) {
        boolean[] letterFound = new boolean[ALPHABET_COUNT];

        for (char character : input.toCharArray()) {
            int letterIndex = character - (Character.isUpperCase(character) ? FIRST_UPPERCASE_LETTER : FIRST_LOWERCASE_LETTER);
            if (letterIndex >= 0 && letterIndex < letterFound.length) {
                letterFound[letterIndex] = true;
            }
        }

        for (boolean isLetterPresent : letterFound) {
            if (!isLetterPresent) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a String is Pangram or not by checking if each alphabet is present.
     *
     * @param input The String to check
     * @return {@code true} if input is a Pangram, otherwise {@code false}
     */
    public static boolean isPangramByScanningAlphabet(String input) {
        if (input.length() < ALPHABET_COUNT) {
            return false;
        }

        String normalizedInput = input.toLowerCase();

        for (char currentLetter = FIRST_LOWERCASE_LETTER; currentLetter <= 'z'; currentLetter++) {
            if (normalizedInput.indexOf(currentLetter) == -1) {
                return false;
            }
        }

        return true;
    }
}