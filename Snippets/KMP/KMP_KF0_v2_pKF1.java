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
        kmpSearch(text, pattern);
    }

    /**
     * Finds and prints all starting indices in {@code text} where {@code pattern} occurs.
     */
    public static void kmpSearch(final String text, final String pattern) {
        final int textLength = text.length();
        final int patternLength = pattern.length();
        final int[] longestPrefixSuffix = computeLongestPrefixSuffix(pattern);

        int patternIndex = 0;

        for (int textIndex = 0; textIndex < textLength; textIndex++) {
            while (patternIndex > 0 && text.charAt(textIndex) != pattern.charAt(patternIndex)) {
                patternIndex = longestPrefixSuffix[patternIndex - 1];
            }

            if (text.charAt(textIndex) == pattern.charAt(patternIndex)) {
                patternIndex++;
            }

            if (patternIndex == patternLength) {
                System.out.println("Pattern starts at index: " + (textIndex + 1 - patternLength));
                patternIndex = longestPrefixSuffix[patternIndex - 1];
            }
        }
    }

    /**
     * Computes the longest prefix-suffix (LPS) array for the KMP algorithm.
     */
    private static int[] computeLongestPrefixSuffix(final String pattern) {
        final int patternLength = pattern.length();
        final int[] longestPrefixSuffix = new int[patternLength];

        longestPrefixSuffix[0] = 0;
        int currentLpsLength = 0;

        for (int i = 1; i < patternLength; i++) {
            while (currentLpsLength > 0 && pattern.charAt(currentLpsLength) != pattern.charAt(i)) {
                currentLpsLength = longestPrefixSuffix[currentLpsLength - 1];
            }

            if (pattern.charAt(currentLpsLength) == pattern.charAt(i)) {
                currentLpsLength++;
            }

            longestPrefixSuffix[i] = currentLpsLength;
        }

        return longestPrefixSuffix;
    }
}