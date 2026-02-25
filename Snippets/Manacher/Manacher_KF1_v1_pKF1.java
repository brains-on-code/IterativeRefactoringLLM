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
        final String processed = preprocessForManacher(input);
        int[] palindromeRadii = new int[processed.length()];
        int center = 0;
        int rightBoundary = 0;
        int maxRadius = 0;
        int centerIndexOfMax = 0;

        for (int i = 1; i < processed.length() - 1; i++) {
            int mirror = 2 * center - i;

            if (i < rightBoundary) {
                palindromeRadii[i] = Math.min(rightBoundary - i, palindromeRadii[mirror]);
            }

            while (processed.charAt(i + 1 + palindromeRadii[i]) == processed.charAt(i - 1 - palindromeRadii[i])) {
                palindromeRadii[i]++;
            }

            if (i + palindromeRadii[i] > rightBoundary) {
                center = i;
                rightBoundary = i + palindromeRadii[i];
            }

            if (palindromeRadii[i] > maxRadius) {
                maxRadius = palindromeRadii[i];
                centerIndexOfMax = i;
            }
        }

        final int startIndex = (centerIndexOfMax - maxRadius) / 2;
        return input.substring(startIndex, startIndex + maxRadius);
    }

    /**
     * Preprocesses the input string for Manacher's algorithm by inserting
     * boundary characters.
     *
     * @param input the original string
     * @return the transformed string with boundaries
     */
    private static String preprocessForManacher(String input) {
        if (input.isEmpty()) {
            return "^$";
        }
        StringBuilder builder = new StringBuilder("^");
        for (char ch : input.toCharArray()) {
            builder.append('#').append(ch);
        }
        builder.append("#$");
        return builder.toString();
    }
}