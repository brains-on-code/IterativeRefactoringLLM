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

    public static boolean isPangramUsingSet(String input) {
        if (input == null) {
            return false;
        }

        Set<Character> distinctLetters = new HashSet<>();
        String normalizedInput = input.trim().toLowerCase();

        for (int i = 0; i < normalizedInput.length(); i++) {
            char currentChar = normalizedInput.charAt(i);
            if (currentChar != ' ') {
                distinctLetters.add(currentChar);
            }
        }

        return distinctLetters.size() == ALPHABET_SIZE;
    }

    public static boolean isPangram(String input) {
        if (input == null) {
            return false;
        }

        boolean[] letterSeen = new boolean[ALPHABET_SIZE];

        for (char ch : input.toCharArray()) {
            char baseChar = Character.isUpperCase(ch) ? 'A' : 'a';
            int letterIndex = ch - baseChar;

            if (letterIndex >= 0 && letterIndex < ALPHABET_SIZE) {
                letterSeen[letterIndex] = true;
            }
        }

        for (boolean seen : letterSeen) {
            if (!seen) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPangramByScanningAlphabet(String input) {
        if (input == null || input.length() < ALPHABET_SIZE) {
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