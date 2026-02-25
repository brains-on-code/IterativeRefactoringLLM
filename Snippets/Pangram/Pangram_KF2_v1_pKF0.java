package com.thealgorithms.strings;

import java.util.HashSet;
import java.util.Set;

public final class Pangram {

    private static final int ALPHABET_COUNT = 26;

    private Pangram() {
        // Utility class; prevent instantiation
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

        Set<Character> letters = new HashSet<>();
        String normalized = input.trim().toLowerCase();

        for (char ch : normalized.toCharArray()) {
            if (Character.isLetter(ch)) {
                letters.add(ch);
            }
        }

        return letters.size() == ALPHABET_COUNT;
    }

    public static boolean isPangram(String input) {
        if (input == null) {
            return false;
        }

        boolean[] lettersPresent = new boolean[ALPHABET_COUNT];

        for (char ch : input.toCharArray()) {
            if (!Character.isLetter(ch)) {
                continue;
            }

            char lower = Character.toLowerCase(ch);
            int index = lower - 'a';

            if (index >= 0 && index < ALPHABET_COUNT) {
                lettersPresent[index] = true;
            }
        }

        for (boolean present : lettersPresent) {
            if (!present) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPangram2(String input) {
        if (input == null) {
            return false;
        }

        String normalized = input.toLowerCase();
        if (normalized.length() < ALPHABET_COUNT) {
            return false;
        }

        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (normalized.indexOf(ch) == -1) {
                return false;
            }
        }

        return true;
    }
}