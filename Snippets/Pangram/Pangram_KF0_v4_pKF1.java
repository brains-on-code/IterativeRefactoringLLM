package com.thealgorithms.strings;

import java.util.HashSet;
import java.util.Set;

/**
 * Wikipedia: https://en.wikipedia.org/wiki/Pangram
 */
public final class Pangram {

    private static final int ALPHABET_SIZE = 26;
    private static final char LOWERCASE_A = 'a';
    private static final char UPPERCASE_A = 'A';
    private static final char LOWERCASE_Z = 'z';

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

        return uniqueLetters.size() == ALPHABET_SIZE;
    }

    /**
     * Checks if a String is considered a Pangram.
     *
     * @param input The String to check
     * @return {@code true} if input is a Pangram, otherwise {@code false}
     */
    public static boolean isPangram(String input) {
        boolean[] letterPresence = new boolean[ALPHABET_SIZE];

        for (char character : input.toCharArray()) {
            int alphabetIndex =
                character - (Character.isUpperCase(character) ? UPPERCASE_A : LOWERCASE_A);
            if (alphabetIndex >= 0 && alphabetIndex < letterPresence.length) {
                letterPresence[alphabetIndex] = true;
            }
        }

        for (boolean isLetterPresent : letterPresence) {
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
        if (input.length() < ALPHABET_SIZE) {
            return false;
        }

        String normalizedInput = input.toLowerCase();

        for (char currentLetter = LOWERCASE_A; currentLetter <= LOWERCASE_Z; currentLetter++) {
            if (normalizedInput.indexOf(currentLetter) == -1) {
                return false;
            }
        }

        return true;
    }
}