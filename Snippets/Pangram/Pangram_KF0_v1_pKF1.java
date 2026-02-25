package com.thealgorithms.strings;

import java.util.HashSet;
import java.util.Set;

/**
 * Wikipedia: https://en.wikipedia.org/wiki/Pangram
 */
public final class Pangram {

    private Pangram() {
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
     * @param input The String to check
     * @return {@code true} if input is a Pangram, otherwise {@code false}
     */
    public static boolean isPangramUsingSet(String input) {
        Set<Character> uniqueLetters = new HashSet<>();
        String normalizedInput = input.trim().toLowerCase();

        for (int index = 0; index < normalizedInput.length(); index++) {
            char currentChar = normalizedInput.charAt(index);
            if (currentChar != ' ') {
                uniqueLetters.add(currentChar);
            }
        }

        return uniqueLetters.size() == 26;
    }

    /**
     * Checks if a String is considered a Pangram.
     *
     * @param input The String to check
     * @return {@code true} if input is a Pangram, otherwise {@code false}
     */
    public static boolean isPangram(String input) {
        boolean[] letterPresence = new boolean[26];

        for (char character : input.toCharArray()) {
            int letterIndex = character - (Character.isUpperCase(character) ? 'A' : 'a');
            if (letterIndex >= 0 && letterIndex < letterPresence.length) {
                letterPresence[letterIndex] = true;
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
     * Checks if a String is Pangram or not by checking if each alphabet is present.
     *
     * @param input The String to check
     * @return {@code true} if input is a Pangram, otherwise {@code false}
     */
    public static boolean isPangramByScanningAlphabet(String input) {
        if (input.length() < 26) {
            return false;
        }

        String normalizedInput = input.toLowerCase();

        for (char alphabetChar = 'a'; alphabetChar <= 'z'; alphabetChar++) {
            if (normalizedInput.indexOf(alphabetChar) == -1) {
                return false;
            }
        }

        return true;
    }
}