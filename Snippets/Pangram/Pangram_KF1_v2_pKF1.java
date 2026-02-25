package com.thealgorithms.strings;

import java.util.HashSet;

/**
 * Utility class for checking whether a string is a pangram.
 */
public final class PangramChecker {

    private PangramChecker() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        assert isPangramUsingBooleanArray("The quick brown fox jumps over the lazy dog");
        assert !isPangramUsingBooleanArray("The quick brown fox jumps over the azy dog");
        assert !isPangramUsingBooleanArray("+-1234 This string is not alphabetical");
        assert !isPangramUsingBooleanArray("\u0000/\\");
    }

    /**
     * Checks if the given string is a pangram using a Set of characters.
     *
     * @param input the string to check
     * @return true if the string is a pangram, false otherwise
     */
    public static boolean isPangramUsingSet(String input) {
        if (input == null) {
            return false;
        }

        HashSet<Character> uniqueLetters = new HashSet<>();
        String normalizedInput = input.trim().toLowerCase();

        for (int i = 0; i < normalizedInput.length(); i++) {
            char currentChar = normalizedInput.charAt(i);
            if (currentChar >= 'a' && currentChar <= 'z') {
                uniqueLetters.add(currentChar);
            }
        }

        return uniqueLetters.size() == 26;
    }

    /**
     * Checks if the given string is a pangram using a boolean array to track seen letters.
     *
     * @param input the string to check
     * @return true if the string is a pangram, false otherwise
     */
    public static boolean isPangramUsingBooleanArray(String input) {
        if (input == null) {
            return false;
        }

        boolean[] letterSeen = new boolean[26];

        for (char character : input.toCharArray()) {
            if (Character.isLetter(character)) {
                char lowerCaseChar = Character.toLowerCase(character);
                int index = lowerCaseChar - 'a';
                if (index >= 0 && index < letterSeen.length) {
                    letterSeen[index] = true;
                }
            }
        }

        for (boolean hasBeenSeen : letterSeen) {
            if (!hasBeenSeen) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the given string is a pangram using indexOf for each alphabet letter.
     *
     * @param input the string to check
     * @return true if the string is a pangram, false otherwise
     */
    public static boolean isPangramUsingIndexOf(String input) {
        if (input == null || input.length() < 26) {
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