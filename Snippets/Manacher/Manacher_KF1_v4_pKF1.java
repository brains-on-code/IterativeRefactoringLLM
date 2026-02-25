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
        int longestPalindromeLength = 0;
        int centerOfLongestPalindrome = 0;

        for (int position = 1; position < transformedInput.length() - 1; position++) {
            int mirroredPosition = 2 * currentCenter - position;

            if (position < currentRightBoundary) {
                palindromeLengths[position] =
                    Math.min(currentRightBoundary - position, palindromeLengths[mirroredPosition]);
            }

            while (transformedInput.charAt(position + 1 + palindromeLengths[position])
                == transformedInput.charAt(position - 1 - palindromeLengths[position])) {
                palindromeLengths[position]++;
            }

            if (position + palindromeLengths[position] > currentRightBoundary) {
                currentCenter = position;
                currentRightBoundary = position + palindromeLengths[position];
            }

            if (palindromeLengths[position] > longestPalindromeLength) {
                longestPalindromeLength = palindromeLengths[position];
                centerOfLongestPalindrome = position;
            }
        }

        final int startIndex = (centerOfLongestPalindrome - longestPalindromeLength) / 2;
        return input.substring(startIndex, startIndex + longestPalindromeLength);
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