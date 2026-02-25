package com.thealgorithms.strings;

import java.util.HashSet;
import java.util.Set;

public final class Pangram {

    private static final int ALPHABET_SIZE = 26;

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

        Set<Character> uniqueLetters = new HashSet<>();
        String normalizedText = text.trim().toLowerCase();

        for (int index = 0; index < normalizedText.length(); index++) {
            char currentCharacter = normalizedText.charAt(index);
            if (currentCharacter != ' ') {
                uniqueLetters.add(currentCharacter);
            }
        }

        return uniqueLetters.size() == ALPHABET_SIZE;
    }

    public static boolean isPangram(String text) {
        if (text == null) {
            return false;
        }

        boolean[] letterPresence = new boolean[ALPHABET_SIZE];

        for (char character : text.toCharArray()) {
            char baseCharacter = Character.isUpperCase(character) ? 'A' : 'a';
            int letterIndex = character - baseCharacter;

            if (letterIndex >= 0 && letterIndex < ALPHABET_SIZE) {
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

    public static boolean isPangramByScanningAlphabet(String text) {
        if (text == null || text.length() < ALPHABET_SIZE) {
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