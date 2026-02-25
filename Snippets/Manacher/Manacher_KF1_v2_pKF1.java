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
        final String transformedInput = transformForManacher(input);
        int[] palindromeLengths = new int[transformedInput.length()];
        int currentCenter = 0;
        int currentRightBoundary = 0;
        int longestPalindromeRadius = 0;
        int centerOfLongestPalindrome = 0;

        for (int currentIndex = 1; currentIndex < transformedInput.length() - 1; currentIndex++) {
            int mirrorIndex = 2 * currentCenter - currentIndex;

            if (currentIndex < currentRightBoundary) {
                palindromeLengths[currentIndex] =
                    Math.min(currentRightBoundary - currentIndex, palindromeLengths[mirrorIndex]);
            }

            while (transformedInput.charAt(currentIndex + 1 + palindromeLengths[currentIndex])
                == transformedInput.charAt(currentIndex - 1 - palindromeLengths[currentIndex])) {
                palindromeLengths[currentIndex]++;
            }

            if (currentIndex + palindromeLengths[currentIndex] > currentRightBoundary) {
                currentCenter = currentIndex;
                currentRightBoundary = currentIndex + palindromeLengths[currentIndex];
            }

            if (palindromeLengths[currentIndex] > longestPalindromeRadius) {
                longestPalindromeRadius = palindromeLengths[currentIndex];
                centerOfLongestPalindrome = currentIndex;
            }
        }

        final int startIndex = (centerOfLongestPalindrome - longestPalindromeRadius) / 2;
        return input.substring(startIndex, startIndex + longestPalindromeRadius);
    }

    /**
     * Transforms the input string for Manacher's algorithm by inserting
     * boundary characters.
     *
     * @param input the original string
     * @return the transformed string with boundaries
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