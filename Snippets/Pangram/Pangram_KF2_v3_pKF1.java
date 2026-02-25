package com.thealgorithms.strings;

import java.util.HashSet;
import java.util.Set;

public final class Pangram {

    private static final int ALPHABET_COUNT = 26;

    private Pangram() {
    }

    public static void main(String[] args) {
        assert isPangram("The quick brown fox jumps over the lazy dog");
        assert !isPangram("The quick brown fox jumps over the azy dog");
        assert !isPangram("+-1234 This string is not alphabetical");
        assert !isPangram("\u0000/\\");
    }

    public static boolean isPangramUsingSet(String input) {
        if (input == null) {
            return false;
        }

        Set<Character> uniqueCharacters = new HashSet<>();
        String normalizedInput = input.trim().toLowerCase();

        for (int index = 0; index < normalizedInput.length(); index++) {
            char currentCharacter = normalizedInput.charAt(index);
            if (currentCharacter != ' ') {
                uniqueCharacters.add(currentCharacter);
            }
        }

        return uniqueCharacters.size() == ALPHABET_COUNT;
    }

    public static boolean isPangram(String input) {
        if (input == null) {
            return false;
        }

        boolean[] alphabetPresence = new boolean[ALPHABET_COUNT];

        for (char character : input.toCharArray()) {
            char alphabetBase = Character.isUpperCase(character) ? 'A' : 'a';
            int alphabetIndex = character - alphabetBase;

            if (alphabetIndex >= 0 && alphabetIndex < ALPHABET_COUNT) {
                alphabetPresence[alphabetIndex] = true;
            }
        }

        for (boolean isPresent : alphabetPresence) {
            if (!isPresent) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPangramByScanningAlphabet(String input) {
        if (input == null || input.length() < ALPHABET_COUNT) {
            return false;
        }

        String normalizedInput = input.toLowerCase();

        for (char letter = 'a'; letter <= 'z'; letter++) {
            if (normalizedInput.indexOf(letter) == -1) {
                return false;
            }
        }

        return true;
    }
}