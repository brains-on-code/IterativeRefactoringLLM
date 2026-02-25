package com.thealgorithms.strings;

/**
 * Wikipedia: https://en.wikipedia.org/wiki/Longest_palindromic_substring#Manacher's_algorithm
 */
public final class Manacher {

    private Manacher() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds the longest palindromic substring using Manacher's Algorithm.
     *
     * @param input The input string
     * @return The longest palindromic substring in {@code input}
     */
    public static String longestPalindrome(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        final String processed = preprocess(input);
        final int length = processed.length();
        int[] palindromeRadii = new int[length];

        int center = 0;
        int rightBoundary = 0;
        int maxRadius = 0;
        int maxCenter = 0;

        for (int i = 1; i < length - 1; i++) {
            int mirror = 2 * center - i;

            if (i < rightBoundary) {
                palindromeRadii[i] = Math.min(rightBoundary - i, palindromeRadii[mirror]);
            }

            while (processed.charAt(i + 1 + palindromeRadii[i]) == processed.charAt(i - 1 - palindromeRadii[i])) {
                palindromeRadii[i]++;
            }

            int currentRight = i + palindromeRadii[i];
            if (currentRight > rightBoundary) {
                center = i;
                rightBoundary = currentRight;
            }

            if (palindromeRadii[i] > maxRadius) {
                maxRadius = palindromeRadii[i];
                maxCenter = i;
            }
        }

        int start = (maxCenter - maxRadius) / 2;
        return input.substring(start, start + maxRadius);
    }

    /**
     * Preprocesses the input string by inserting a special character ('#') between each character
     * and adding '^' at the start and '$' at the end to avoid boundary conditions.
     *
     * @param input The original string
     * @return The preprocessed string with additional characters
     */
    private static String preprocess(String input) {
        if (input == null || input.isEmpty()) {
            return "^$";
        }

        StringBuilder builder = new StringBuilder("^");
        for (char c : input.toCharArray()) {
            builder.append('#').append(c);
        }
        builder.append("#$");
        return builder.toString();
    }
}