package com.thealgorithms.strings;

/**
 * Wikipedia: https://en.wikipedia.org/wiki/Longest_palindromic_substring#Manacher's_algorithm
 */
public final class Manacher {

    private Manacher() {
    }

    /**
     * Finds the longest palindromic substring using Manacher's Algorithm
     *
     * @param input The input string
     * @return The longest palindromic substring in {@code input}
     */
    public static String longestPalindrome(String input) {
        final String transformed = transformForManacher(input);
        int[] palindromeRadii = new int[transformed.length()];
        int currentCenter = 0;
        int currentRightBoundary = 0;
        int longestRadius = 0;
        int longestCenterIndex = 0;

        for (int index = 1; index < transformed.length() - 1; index++) {
            int mirrorIndex = 2 * currentCenter - index;

            if (index < currentRightBoundary) {
                palindromeRadii[index] =
                    Math.min(currentRightBoundary - index, palindromeRadii[mirrorIndex]);
            }

            while (transformed.charAt(index + 1 + palindromeRadii[index])
                == transformed.charAt(index - 1 - palindromeRadii[index])) {
                palindromeRadii[index]++;
            }

            if (index + palindromeRadii[index] > currentRightBoundary) {
                currentCenter = index;
                currentRightBoundary = index + palindromeRadii[index];
            }

            if (palindromeRadii[index] > longestRadius) {
                longestRadius = palindromeRadii[index];
                longestCenterIndex = index;
            }
        }

        final int startIndexInOriginal = (longestCenterIndex - longestRadius) / 2;
        return input.substring(startIndexInOriginal, startIndexInOriginal + longestRadius);
    }

    /**
     * Preprocesses the input string by inserting a special character ('#') between each character
     * and adding '^' at the start and '$' at the end to avoid boundary conditions.
     *
     * @param input The original string
     * @return The preprocessed string with additional characters
     */
    private static String transformForManacher(String input) {
        if (input.isEmpty()) {
            return "^$";
        }
        StringBuilder transformedBuilder = new StringBuilder("^");
        for (char character : input.toCharArray()) {
            transformedBuilder.append('#').append(character);
        }
        transformedBuilder.append("#$");
        return transformedBuilder.toString();
    }
}