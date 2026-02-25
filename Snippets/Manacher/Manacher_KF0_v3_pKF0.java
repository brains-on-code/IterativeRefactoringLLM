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
        final int processedLength = processed.length();
        int[] palindromeRadii = new int[processedLength];

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
        return input.substring(startIndexInOriginal, startIndexInOriginal + maxRadius);
    }

    /**
     * Expands the palindrome around the given center index in the processed string.
     *
     * @param processed       The preprocessed string
     * @param palindromeRadii Array holding palindrome radii for each index
     * @param centerIndex     The current center index to expand around
     */
    private static void expandAroundCenter(String processed, int[] palindromeRadii, int centerIndex) {
        while (processed.charAt(centerIndex + 1 + palindromeRadii[centerIndex])
                == processed.charAt(centerIndex - 1 - palindromeRadii[centerIndex])) {
            palindromeRadii[centerIndex]++;
        }
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
        for (char character : input.toCharArray()) {
            builder.append('#').append(character);
        }
        builder.append("#$");
        return builder.toString();
    }
}