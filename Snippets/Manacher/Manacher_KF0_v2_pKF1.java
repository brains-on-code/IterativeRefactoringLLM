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
        final String processedString = preprocessForManacher(input);
        int[] palindromeLengths = new int[processedString.length()];
        int center = 0;
        int rightBoundary = 0;
        int maxPalindromeLength = 0;
        int maxPalindromeCenter = 0;

        for (int currentIndex = 1; currentIndex < processedString.length() - 1; currentIndex++) {
            int mirrorIndex = 2 * center - currentIndex;

            if (currentIndex < rightBoundary) {
                palindromeLengths[currentIndex] =
                    Math.min(rightBoundary - currentIndex, palindromeLengths[mirrorIndex]);
            }

            while (processedString.charAt(currentIndex + 1 + palindromeLengths[currentIndex])
                == processedString.charAt(currentIndex - 1 - palindromeLengths[currentIndex])) {
                palindromeLengths[currentIndex]++;
            }

            if (currentIndex + palindromeLengths[currentIndex] > rightBoundary) {
                center = currentIndex;
                rightBoundary = currentIndex + palindromeLengths[currentIndex];
            }

            if (palindromeLengths[currentIndex] > maxPalindromeLength) {
                maxPalindromeLength = palindromeLengths[currentIndex];
                maxPalindromeCenter = currentIndex;
            }
        }

        final int startIndexInOriginal = (maxPalindromeCenter - maxPalindromeLength) / 2;
        return input.substring(startIndexInOriginal, startIndexInOriginal + maxPalindromeLength);
    }

    /**
     * Preprocesses the input string by inserting a special character ('#') between each character
     * and adding '^' at the start and '$' at the end to avoid boundary conditions.
     *
     * @param input The original string
     * @return The preprocessed string with additional characters
     */
    private static String preprocessForManacher(String input) {
        if (input.isEmpty()) {
            return "^$";
        }
        StringBuilder processedBuilder = new StringBuilder("^");
        for (char character : input.toCharArray()) {
            processedBuilder.append('#').append(character);
        }
        processedBuilder.append("#$");
        return processedBuilder.toString();
    }
}