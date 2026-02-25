package com.thealgorithms.strings;

import java.util.HashSet;

/**
 * Utility class for pangram-related string operations.
 *
 * A pangram is a sentence that contains every letter of the alphabet at least once.
 */
public final class PangramUtils {

    private static final int ALPHABET_COUNT = 26;

    private PangramUtils() {
        // Prevent instantiation
    }

    /**
     * Simple self-test for {@link #isPangramWithBooleanArray(String)}.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        assert isPangramWithBooleanArray("The quick brown fox jumps over the lazy dog");
        assert !isPangramWithBooleanArray("The quick brown fox jumps over the azy dog");
        assert !isPangramWithBooleanArray("+-1234 This string is not alphabetical");
        assert !isPangramWithBooleanArray("\u0000/\\");
    }

    /**
     * Checks if the given string is a pangram using a {@link HashSet}.
     *
     * @param text the input string to check
     * @return {@code true} if the string is a pangram, {@code false} otherwise
     */
    public static boolean isPangramWithSet(String text) {
        if (text == null) {
            return false;
        }

        HashSet<Character> uniqueLetters = new HashSet<>();
        String normalized = text.trim().toLowerCase();

        for (int i = 0; i < normalized.length(); i++) {
            char ch = normalized.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                uniqueLetters.add(ch);
            }
        }

        return uniqueLetters.size() == ALPHABET_COUNT;
    }

    /**
     * Checks if the given string is a pangram using a boolean array.
     *
     * @param text the input string to check
     * @return {@code true} if the string is a pangram, {@code false} otherwise
     */
    public static boolean isPangramWithBooleanArray(String text) {
        if (text == null) {
            return false;
        }

        boolean[] seen = new boolean[ALPHABET_COUNT];

        for (char ch : text.toLowerCase().toCharArray()) {
            if (ch >= 'a' && ch <= 'z') {
                seen[ch - 'a'] = true;
            }
        }

        for (boolean present : seen) {
            if (!present) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given string is a pangram using {@link String#indexOf(char)}.
     *
     * @param text the input string to check
     * @return {@code true} if the string is a pangram, {@code false} otherwise
     */
    public static boolean isPangramWithIndexOf(String text) {
        if (text == null || text.length() < ALPHABET_COUNT) {
            return false;
        }

        String normalized = text.toLowerCase();

        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (normalized.indexOf(ch) == -1) {
                return false;
            }
        }

        return true;
    }
}