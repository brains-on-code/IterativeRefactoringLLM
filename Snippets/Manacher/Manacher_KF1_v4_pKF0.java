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
        final int[] palindromeRadii = new int[processedLength];

        int center = 0;
        int rightBoundary = 0;
        int maxRadius = 0;
        int maxCenter = 0;

        for (int i = 1; i < processedLength - 1; i++) {
            final int mirror = 2 * center - i;

            if (i < rightBoundary) {
                palindromeRadii[i] = Math.min(
                    rightBoundary - i,
                    palindromeRadii[mirror]
                );
            }

            while (processed.charAt(i + 1 + palindromeRadii[i])
                    == processed.charAt(i - 1 - palindromeRadii[i])) {
                palindromeRadii[i]++;
            }

            final int currentRightEdge = i + palindromeRadii[i];
            if (currentRightEdge > rightBoundary) {
                center = i;
                rightBoundary = currentRightEdge;
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

        StringBuilder builder = new StringBuilder(input.length() * 2 + 3);
        builder.append('^');
        for (char ch : input.toCharArray()) {
            builder.append('#').append(ch);
        }
        builder.append("#$");
        return builder.toString();
    }
}