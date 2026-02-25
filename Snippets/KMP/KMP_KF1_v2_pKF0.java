package com.thealgorithms.strings;

/**
 * Demonstrates Knuth–Morris–Pratt (KMP) pattern searching.
 */
public final class KmpSearch {

    private KmpSearch() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        final String text = "AAAAABAAABA";
        final String pattern = "AAAA";
        searchPattern(text, pattern);
    }

    /**
     * Searches for all occurrences of {@code pattern} in {@code text} using the KMP algorithm
     * and prints the starting indices of each match.
     *
     * @param text    the text to search within
     * @param pattern the pattern to search for
     */
    public static void searchPattern(final String text, final String pattern) {
        if (text == null || pattern == null || pattern.isEmpty()) {
            return;
        }

        final int textLength = text.length();
        final int patternLength = pattern.length();
        final int[] lps = computeLpsArray(pattern);

        int patternIndex = 0;

        for (int textIndex = 0; textIndex < textLength; textIndex++) {
            char textChar = text.charAt(textIndex);

            while (patternIndex > 0 && textChar != pattern.charAt(patternIndex)) {
                patternIndex = lps[patternIndex - 1];
            }

            if (textChar == pattern.charAt(patternIndex)) {
                patternIndex++;
            }

            if (patternIndex == patternLength) {
                int matchStartIndex = textIndex + 1 - patternLength;
                System.out.println("Pattern starts at index: " + matchStartIndex);
                patternIndex = lps[patternIndex - 1];
            }
        }
    }

    /**
     * Computes the Longest Proper Prefix which is also Suffix (LPS) array for the given pattern.
     *
     * @param pattern the pattern for which to compute the LPS array
     * @return the LPS array
     */
    private static int[] computeLpsArray(final String pattern) {
        final int length = pattern.length();
        final int[] lps = new int[length];

        int longestPrefixSuffixLength = 0;
        lps[0] = 0;

        for (int i = 1; i < length; i++) {
            char currentChar = pattern.charAt(i);

            while (longestPrefixSuffixLength > 0
                    && pattern.charAt(longestPrefixSuffixLength) != currentChar) {
                longestPrefixSuffixLength = lps[longestPrefixSuffixLength - 1];
            }

            if (pattern.charAt(longestPrefixSuffixLength) == currentChar) {
                longestPrefixSuffixLength++;
            }

            lps[i] = longestPrefixSuffixLength;
        }

        return lps;
    }
}