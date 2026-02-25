package com.thealgorithms.strings;

/**
 * Utility class for palindrome-related string operations.
 */
public final class LongestPalindromicSubstring {

    private LongestPalindromicSubstring() {
    }

    /**
     * Finds the longest palindromic substring in the given string using Manacher's algorithm.
     *
     * @param input the string to search within
     * @return the longest palindromic substring
     */
    public static String longestPalindromicSubstring(String input) {
        final String processedString = preprocessForManacher(input);
        int[] palindromeRadii = new int[processedString.length()];
        int center = 0;
        int rightBoundary = 0;
        int maxRadius = 0;
        int centerOfMaxPalindrome = 0;

        for (int currentIndex = 1; currentIndex < processedString.length() - 1; currentIndex++) {
            int mirrorIndex = 2 * center - currentIndex;

            if (currentIndex < rightBoundary) {
                palindromeRadii[currentIndex] =
                    Math.min(rightBoundary - currentIndex, palindromeRadii[mirrorIndex]);
            }

            while (processedString.charAt(currentIndex + 1 + palindromeRadii[currentIndex])
                == processedString.charAt(currentIndex - 1 - palindromeRadii[currentIndex])) {
                palindromeRadii[currentIndex]++;
            }

            if (currentIndex + palindromeRadii[currentIndex] > rightBoundary) {
                center = currentIndex;
                rightBoundary = currentIndex + palindromeRadii[currentIndex];
            }

            if (palindromeRadii[currentIndex] > maxRadius) {
                maxRadius = palindromeRadii[currentIndex];
                centerOfMaxPalindrome = currentIndex;
            }
        }

        final int startIndex = (centerOfMaxPalindrome - maxRadius) / 2;
        return input.substring(startIndex, startIndex + maxRadius);
    }

    /**
     * Transforms the input string for Manacher's algorithm by inserting
     * boundary characters.
     *
     * @param input the original string
     * @return the transformed string with boundaries
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