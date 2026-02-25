package com.thealgorithms.strings;

import java.util.HashSet;

/**
 * Utility class for checking whether strings are pangrams.
 *
 * <p>A pangram is a sentence that contains every letter of the alphabet at least once.
 * See: https://en.wikipedia.org/wiki/Pangram
 */
public final class Pangram {

    private Pangram() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        assert isPangram("The quick brown fox jumps over the lazy dog");
        assert !isPangram("The quick brown fox jumps over the azy dog"); // 'l' is missing
        assert !isPangram("+-1234 This string is not alphabetical");
        assert !isPangram("\u0000/\\");
    }

    /**
     * Checks if a string is a pangram using a {@link HashSet} to track distinct letters.
     *
     * @param s the string to check
     * @return {@code true} if {@code s} is a pangram, otherwise {@code false}
     */
    public static boolean isPangramUsingSet(String s) {
        HashSet<Character> letters = new HashSet<>();
        String normalized = s.trim().toLowerCase();

        for (int i = 0; i < normalized.length(); i++) {
            char ch = normalized.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                letters.add(ch);
            }
        }

        return letters.size() == 26;
    }

    /**
     * Checks if a string is a pangram by tracking presence of each alphabet letter.
     *
     * @param s the string to check
     * @return {@code true} if {@code s} is a pangram, otherwise {@code false}
     */
    public static boolean isPangram(String s) {
        boolean[] lettersPresent = new boolean[26];

        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                char lower = Character.toLowerCase(c);
                int index = lower - 'a';
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
     * Checks if a string is a pangram by verifying that each alphabet letter appears at least once.
     *
     * @param s the string to check
     * @return {@code true} if {@code s} is a pangram, otherwise {@code false}
     */
    public static boolean isPangram2(String s) {
        if (s.length() < 26) {
            return false;
        }

        String normalized = s.toLowerCase();

        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (normalized.indexOf(ch) == -1) {
                return false;
            }
        }

        return true;
    }
}