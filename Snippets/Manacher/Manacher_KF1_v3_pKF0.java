package com.thealgorithms.strings;

/**
 * Utility class for palindrome-related string operations.
 */
public final class LongestPalindromeFinder {

    private LongestPalindromeFinder() {
        // Prevent instantiation
    }

    /**
     * Finds the longest palindromic substring in the given string using Manacher's algorithm.
     *
     * @param input the input string
     * @return the longest palindromic substring
     */
    public static String findLongestPalindromicSubstring(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        final String processed = preprocessForManacher(input);
        final int processedLength = processed.length();
        int[] palindromeRadii = new int[processedLength];

        int currentCenter = 0;
        int currentRightBoundary = 0;
        int maxRadius = 0;
        int maxCenter = 0;

        for (int i = 1; i < processedLength - 1; i++) {
            int mirrorIndex = 2 * currentCenter - i;

            if (i < currentRightBoundary) {
                palindromeRadii[i] = Math.min(
                    currentRightBoundary - i,
                    palindromeRadii[mirrorIndex]
                );
            }

            while (processed.charAt(i + 1 + palindromeRadii[i])
                    == processed.charAt(i - 1 - palindromeRadii[i])) {
                palindromeRadii[i]++;
            }

            int rightEdge = i + palindromeRadii[i];
            if (rightEdge > currentRightBoundary) {
                currentCenter = i;
                currentRightBoundary = rightEdge;
            }

            if (palindromeRadii[i] > maxRadius) {
                maxRadius = palindromeRadii[i];
                maxCenter = i;
            }
        }

        final int startIndex = (maxCenter - maxRadius) / 2;
        return input.substring(startIndex, startIndex + maxRadius);
    }

    /**
     * Preprocesses the string by inserting boundary characters to handle even-length palindromes.
     *
     * @param input the original string
     * @return the transformed string with boundaries
     */
    private static String preprocessForManacher(String input) {
        if (input == null || input.isEmpty()) {
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