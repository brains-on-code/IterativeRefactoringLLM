package com.thealgorithms.strings;

/**
 * Demonstrates Knuth–Morris–Pratt (KMP) pattern searching.
 */
public final class Class1 {

    private Class1() {
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
        final int textLength = text.length();
        final int patternLength = pattern.length();
        final int[] lps = computeLpsArray(pattern);

        int patternIndex = 0; // index for pattern[]

        for (int textIndex = 0; textIndex < textLength; textIndex++) {
            while (patternIndex > 0 && text.charAt(textIndex) != pattern.charAt(patternIndex)) {
                patternIndex = lps[patternIndex - 1];
            }

            if (text.charAt(textIndex) == pattern.charAt(patternIndex)) {
                patternIndex++;
            }

            if (patternIndex == patternLength) {
                System.out.println("Pattern starts at index: " + (textIndex + 1 - patternLength));
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

        int len = 0; // length of the previous longest prefix suffix
        lps[0] = 0;

        for (int i = 1; i < length; i++) {
            while (len > 0 && pattern.charAt(len) != pattern.charAt(i)) {
                len = lps[len - 1];
            }

            if (pattern.charAt(len) == pattern.charAt(i)) {
                len++;
            }

            lps[i] = len;
        }

        return lps;
    }
}