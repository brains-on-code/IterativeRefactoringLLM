package com.thealgorithms.strings;

import java.util.HashSet;

/**
 * Utility class for pangram-related string operations.
 *
 * A pangram is a sentence that contains every letter of the alphabet at least once.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Simple self-test for the pangram checker {@link #method3(String)}.
     *
     * @param args ignored
     */
    public static void method1(String[] args) {
        assert method3("The quick brown fox jumps over the lazy dog");
        assert !method3("The quick brown fox jumps over the azy dog");
        assert !method3("+-1234 This string is not alphabetical");
        assert !method3("\u0000/\\");
    }

    /**
     * Checks if the given string is a pangram using a {@link HashSet}.
     *
     * This method:
     * <ul>
     *     <li>Trims the input</li>
     *     <li>Converts it to lowercase</li>
     *     <li>Ignores spaces</li>
     *     <li>Counts distinct alphabetic characters</li>
     * </ul>
     *
     * @param text the input string to check
     * @return {@code true} if the string is a pangram, {@code false} otherwise
     */
    public static boolean method2(String text) {
        HashSet<Character> uniqueChars = new HashSet<>();
        text = text.trim().toLowerCase();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch != ' ') {
                uniqueChars.add(ch);
            }
        }

        return uniqueChars.size() == 26;
    }

    /**
     * Checks if the given string is a pangram using a boolean array.
     *
     * This method:
     * <ul>
     *     <li>Iterates over each character</li>
     *     <li>Maps letters to indices 0–25</li>
     *     <li>Marks presence of each letter</li>
     * </ul>
     *
     * @param text the input string to check
     * @return {@code true} if the string is a pangram, {@code false} otherwise
     */
    public static boolean method3(String text) {
        boolean[] seen = new boolean[26];

        for (char ch : text.toCharArray()) {
            int index = ch - (Character.isUpperCase(ch) ? 'A' : 'a');
            if (index >= 0 && index < seen.length) {
                seen[index] = true;
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
     * This method:
     * <ul>
     *     <li>Returns early if the string is shorter than 26 characters</li>
     *     <li>Converts the string to lowercase</li>
     *     <li>Verifies that each letter 'a'–'z' appears at least once</li>
     * </ul>
     *
     * @param text the input string to check
     * @return {@code true} if the string is a pangram, {@code false} otherwise
     */
    public static boolean method4(String text) {
        if (text.length() < 26) {
            return false;
        }

        text = text.toLowerCase();

        for (char ch = 'a'; ch <= 'z'; ch++) {
            if (text.indexOf(ch) == -1) {
                return false;
            }
        }

        return true;
    }
}