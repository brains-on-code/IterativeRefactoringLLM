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
        final String transformedInput = transformInputForManacher(input);
        int[] palindromeRadii = new int[transformedInput.length()];
        int currentCenter = 0;
        int currentRightBoundary = 0;
        int longestRadius = 0;
        int centerOfLongest = 0;

        for (int position = 1; position < transformedInput.length() - 1; position++) {
            int mirrorPosition = 2 * currentCenter - position;

            if (position < currentRightBoundary) {
                palindromeRadii[position] =
                    Math.min(currentRightBoundary - position, palindromeRadii[mirrorPosition]);
            }

            while (transformedInput.charAt(position + 1 + palindromeRadii[position])
                == transformedInput.charAt(position - 1 - palindromeRadii[position])) {
                palindromeRadii[position]++;
            }

            if (position + palindromeRadii[position] > currentRightBoundary) {
                currentCenter = position;
                currentRightBoundary = position + palindromeRadii[position];
            }

            if (palindromeRadii[position] > longestRadius) {
                longestRadius = palindromeRadii[position];
                centerOfLongest = position;
            }
        }

        final int startIndexInOriginal = (centerOfLongest - longestRadius) / 2;
        return input.substring(startIndexInOriginal, startIndexInOriginal + longestRadius);
    }

    /**
     * Transforms the input string by inserting a special character ('#') between each character
     * and adding '^' at the start and '$' at the end to avoid boundary conditions.
     *
     * @param input The original string
     * @return The transformed string with additional characters
     */
    private static String transformInputForManacher(String input) {
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