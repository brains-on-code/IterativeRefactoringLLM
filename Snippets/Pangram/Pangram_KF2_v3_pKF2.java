package com.thealgorithms.strings;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for checking whether a string is a pangram.
 *
 * <p>A pangram is a sentence containing every letter of the English alphabet
 * at least once (case-insensitive, non-letter characters are ignored).</p>
 */
public final class Pangram {

    private static final int ALPHABET_COUNT = 26;
    private static final char FIRST_LOWERCASE_LETTER = 'a';

    private Pangram() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        assert isPangram("The quick brown fox jumps over the lazy dog");
        assert !isPangram("The quick brown fox jumps over the azy dog");
        assert !isPangram("+-1234 This string is not alphabetical");
        assert !isPangram("\u0000/\\");
    }

    /**
     * Checks if the given string is a pangram using a {@link Set}.
     *
     * @param text the input string to check
     * @return {@code true} if the string is a pangram, {@code false} otherwise
     */
    public static boolean isPangramUsingSet(String text) {
        if (text == null) {
            return false;
        }

        Set<Character> letters = new HashSet<>();
        String normalized = text.trim().toLowerCase();

        for (int i = 0; i < normalized.length(); i++) {
            char current = normalized.charAt(i);
            if (current >= 'a' && current <= 'z') {
                letters.add(current);
            }
        }

        return letters.size() == ALPHABET_COUNT;
    }

    /**
     * Checks if the given string is a pangram using a boolean array to track seen letters.
     *
     * @param text the input string to check
     * @return {@code true} if the string is a pangram, {@code false} otherwise
     */
    public static boolean isPangram(String text) {
        if (text == null) {
            return false;
        }

        boolean[] lettersPresent = new boolean[ALPHABET_COUNT];

        for (char c : text.toCharArray()) {
            if (!Character.isLetter(c)) {
                continue;
            }

            char lower = Character.toLowerCase(c);
            int index = lower - FIRST_LOWERCASE_LETTER;

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

    /**
     * Checks if the given string is a pangram by verifying that each letter from 'a' to 'z'
     * appears at least once.
     *
     * @param text the input string to check
     * @return {@code true} if the string is a pangram, {@code false} otherwise
     */
    public static boolean isPangram2(String text) {
        if (text == null || text.length() < ALPHABET_COUNT) {
            return false;
        }

        String normalized = text.toLowerCase();

        for (char letter = 'a'; letter <= 'z'; letter++) {
            if (normalized.indexOf(letter) == -1) {
                return false;
            }
        }

        return true;
    }
}