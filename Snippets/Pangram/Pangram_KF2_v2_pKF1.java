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

    public static boolean isPangramUsingSet(String text) {
        if (text == null) {
            return false;
        }

        Set<Character> distinctLetters = new HashSet<>();
        String normalizedText = text.trim().toLowerCase();

        for (int i = 0; i < normalizedText.length(); i++) {
            char currentCharacter = normalizedText.charAt(i);
            if (currentCharacter != ' ') {
                distinctLetters.add(currentCharacter);
            }
        }

        return distinctLetters.size() == ALPHABET_COUNT;
    }

    public static boolean isPangram(String text) {
        if (text == null) {
            return false;
        }

        boolean[] letterFound = new boolean[ALPHABET_COUNT];

        for (char character : text.toCharArray()) {
            char alphabetStart = Character.isUpperCase(character) ? 'A' : 'a';
            int alphabetIndex = character - alphabetStart;

            if (alphabetIndex >= 0 && alphabetIndex < ALPHABET_COUNT) {
                letterFound[alphabetIndex] = true;
            }
        }

        for (boolean found : letterFound) {
            if (!found) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPangramByScanningAlphabet(String text) {
        if (text == null || text.length() < ALPHABET_COUNT) {
            return false;
        }

        String normalizedText = text.toLowerCase();

        for (char letter = 'a'; letter <= 'z'; letter++) {
            if (normalizedText.indexOf(letter) == -1) {
                return false;
            }
        }

        return true;
    }
}