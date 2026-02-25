package com.thealgorithms.strings;

import java.util.HashSet;

/**
 * Utility class for pangram-related operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Simple self-test for pangram methods.
     */
    public static void method1(String[] args) {
        assert method3("The quick brown fox jumps over the lazy dog");
        assert !method3("The quick brown fox jumps over the azy dog");
        assert !method3("+-1234 This string is not alphabetical");
        assert !method3("\u0000/\\");
    }

    /**
     * Checks if the given string is a pangram using a HashSet.
     *
     * @param text the input string
     * @return true if the string is a pangram, false otherwise
     */
    public static boolean method2(String text) {
        if (text == null) {
            return false;
        }

        HashSet<Character> uniqueLetters = new HashSet<>();
        String normalized = text.trim().toLowerCase();

        for (int i = 0; i < normalized.length(); i++) {
            char ch = normalized.charAt(i);
            if (ch != ' ') {
                uniqueLetters.add(ch);
            }
        }

        return uniqueLetters.size() == 26;
    }

    /**
     * Checks if the given string is a pangram using a boolean array.
     *
     * @param text the input string
     * @return true if the string is a pangram, false otherwise
     */
    public static boolean method3(String text) {
        if (text == null) {
            return false;
        }

        boolean[] seenLetters = new boolean[26];

        for (char ch : text.toCharArray()) {
            if (!Character.isLetter(ch)) {
                continue;
            }

            char lower = Character.toLowerCase(ch);
            int index = lower - 'a';

            if (index >= 0 && index < seenLetters.length) {
                seenLetters[index] = true;
            }
        }

        for (boolean seen : seenLetters) {
            if (!seen) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the given string is a pangram using indexOf for each letter.
     *
     * @param text the input string
     * @return true if the string is a pangram, false otherwise
     */
    public static boolean method4(String text) {
        if (text == null || text.length() < 26) {
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