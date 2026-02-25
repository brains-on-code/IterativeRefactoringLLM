package com.thealgorithms.strings;

/**
 * Wikipedia: https://en.wikipedia.org/wiki/Longest_palindromic_substring#Manacher's_algorithm
 */
public final class Manacher {

    private static final char BOUNDARY_START = '^';
    private static final char BOUNDARY_END = '$';
    private static final char SEPARATOR = '#';

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
        final int processedLength = processed.length();
        final int[] palindromeRadii = new int[processedLength];

        int center = 0;
        int rightBoundary = 0;
        int maxRadius = 0;
        int maxCenter = 0;

        for (int currentIndex = 1; currentIndex < processedLength - 1; currentIndex++) {
            int mirrorIndex = 2 * center - currentIndex;

            if (currentIndex < rightBoundary) {
                palindromeRadii[currentIndex] =
                    Math.min(rightBoundary - currentIndex, palindromeRadii[mirrorIndex]);
            }

            expandAroundCenter(processed, palindromeRadii, currentIndex);

            int currentRightBoundary = currentIndex + palindromeRadii[currentIndex];
            if (currentRightBoundary > rightBoundary) {
                center = currentIndex;
                rightBoundary = currentRightBoundary;
            }

            if (palindromeRadii[currentIndex] > maxRadius) {
                maxRadius = palindromeRadii[currentIndex];
                maxCenter = currentIndex;
            }
        }

        int startIndexInOriginal = (maxCenter - maxRadius) / 2;
        int endIndexInOriginal = startIndexInOriginal + maxRadius;
        return input.substring(startIndexInOriginal, endIndexInOriginal);
    }

    /**
     * Expands the palindrome around the given center index in the processed string.
     *
     * @param processed       The preprocessed string
     * @param palindromeRadii Array holding palindrome radii for each index
     * @param centerIndex     The current center index to expand around
     */
    private static void expandAroundCenter(String processed, int[] palindromeRadii, int centerIndex) {
        int radius = palindromeRadii[centerIndex];

        while (processed.charAt(centerIndex + 1 + radius)
                == processed.charAt(centerIndex - 1 - radius)) {
            radius++;
        }

        palindromeRadii[centerIndex] = radius;
    }

    /**
     * Preprocesses the input string by inserting a special character between each character
     * and adding boundary characters at the start and end to avoid boundary conditions.
     *
     * @param input The original string
     * @return The preprocessed string with additional characters
     */
    private static String preprocess(String input) {
        if (input == null || input.isEmpty()) {
            return new StringBuilder(3)
                .append(BOUNDARY_START)
                .append(BOUNDARY_END)
                .toString();
        }

        StringBuilder builder = new StringBuilder(input.length() * 2 + 3);
        builder.append(BOUNDARY_START);

        for (int i = 0; i < input.length(); i++) {
            builder.append(SEPARATOR).append(input.charAt(i));
        }

        builder.append(SEPARATOR).append(BOUNDARY_END);
        return builder.toString();
    }
}