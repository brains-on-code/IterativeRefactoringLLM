package com.thealgorithms.strings;

/**
 * Implementation of Knuth–Morris–Pratt algorithm.
 * Usage: see the main function for an example.
 */
public final class KMP {

    private KMP() {
    }

    public static void main(String[] args) {
        final String text = "AAAAABAAABA";   // This is the full string
        final String pattern = "AAAA";       // This is the substring that we want to find
        searchPattern(text, pattern);
    }

    /**
     * Finds and prints all starting indices in {@code text} where {@code pattern} occurs.
     */
    public static void searchPattern(final String text, final String pattern) {
        final int textLength = text.length();
        final int patternLength = pattern.length();
        final int[] longestPrefixSuffix = buildLongestPrefixSuffixArray(pattern);

        int patternPosition = 0;

        for (int textPosition = 0; textPosition < textLength; textPosition++) {
            while (patternPosition > 0 && text.charAt(textPosition) != pattern.charAt(patternPosition)) {
                patternPosition = longestPrefixSuffix[patternPosition - 1];
            }

            if (text.charAt(textPosition) == pattern.charAt(patternPosition)) {
                patternPosition++;
            }

            if (patternPosition == patternLength) {
                System.out.println("Pattern starts at index: " + (textPosition + 1 - patternLength));
                patternPosition = longestPrefixSuffix[patternPosition - 1];
            }
        }
    }

    /**
     * Builds the longest prefix-suffix (LPS) array for the KMP algorithm.
     */
    private static int[] buildLongestPrefixSuffixArray(final String pattern) {
        final int patternLength = pattern.length();
        final int[] longestPrefixSuffix = new int[patternLength];

        longestPrefixSuffix[0] = 0;
        int currentPrefixLength = 0;

        for (int patternPosition = 1; patternPosition < patternLength; patternPosition++) {
            while (currentPrefixLength > 0
                    && pattern.charAt(currentPrefixLength) != pattern.charAt(patternPosition)) {
                currentPrefixLength = longestPrefixSuffix[currentPrefixLength - 1];
            }

            if (pattern.charAt(currentPrefixLength) == pattern.charAt(patternPosition)) {
                currentPrefixLength++;
            }

            longestPrefixSuffix[patternPosition] = currentPrefixLength;
        }

        return longestPrefixSuffix;
    }
}