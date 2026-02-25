package com.thealgorithms.strings;

/**
 * Demonstrates the Knuth–Morris–Pratt (KMP) pattern searching algorithm.
 *
 * <p>Searches for all occurrences of a pattern within a given text and prints
 * the starting indices of each match.</p>
 */
public final class KmpSearch {

    private KmpSearch() {
        // Prevent instantiation of utility class
    }

    /**
     * Example entry point demonstrating KMP search.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        final String text = "AAAAABAAABA";
        final String pattern = "AAAA";
        search(text, pattern);
    }

    /**
     * Performs KMP search of {@code pattern} within {@code text} and prints
     * the starting index of each match.
     *
     * @param text    the text to search within
     * @param pattern the pattern to search for
     */
    public static void search(final String text, final String pattern) {
        final int textLength = text.length();
        final int patternLength = pattern.length();
        final int[] lps = computeLpsArray(pattern);

        int patternIndex = 0;

        for (int textIndex = 0; textIndex < textLength; textIndex++) {
            while (patternIndex > 0 && text.charAt(textIndex) != pattern.charAt(patternIndex)) {
                patternIndex = lps[patternIndex - 1];
            }

            if (text.charAt(textIndex) == pattern.charAt(patternIndex)) {
                patternIndex++;
            }

            if (patternIndex == patternLength) {
                System.out.println("Pattern starts: " + (textIndex + 1 - patternLength));
                patternIndex = lps[patternIndex - 1];
            }
        }
    }

    /**
     * Computes the Longest Proper Prefix which is also Suffix (LPS) array
     * for the given pattern. This array is used by the KMP algorithm to
     * skip unnecessary character comparisons.
     *
     * @param pattern the pattern for which to compute the LPS array
     * @return the LPS array
     */
    private static int[] computeLpsArray(final String pattern) {
        final int length = pattern.length();
        final int[] lps = new int[length];

        lps[0] = 0;
        int prefixLength = 0;

        for (int i = 1; i < length; i++) {
            while (prefixLength > 0 && pattern.charAt(prefixLength) != pattern.charAt(i)) {
                prefixLength = lps[prefixLength - 1];
            }

            if (pattern.charAt(prefixLength) == pattern.charAt(i)) {
                prefixLength++;
            }

            lps[i] = prefixLength;
        }

        return lps;
    }
}