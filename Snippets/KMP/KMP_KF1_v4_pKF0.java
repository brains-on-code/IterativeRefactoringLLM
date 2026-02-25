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
            final char textChar = text.charAt(textIndex);

            patternIndex = adjustPatternIndex(pattern, lps, patternIndex, textChar);

            if (isPatternCharMatch(pattern, patternIndex, textChar)) {
                patternIndex++;
            }

            if (patternIndex == patternLength) {
                final int matchStartIndex = textIndex + 1 - patternLength;
                System.out.println("Pattern starts at index: " + matchStartIndex);
                patternIndex = lps[patternIndex - 1];
            }
        }
    }

    private static int adjustPatternIndex(
        final String pattern,
        final int[] lps,
        int patternIndex,
        final char textChar
    ) {
        while (patternIndex > 0 && textChar != pattern.charAt(patternIndex)) {
            patternIndex = lps[patternIndex - 1];
        }
        return patternIndex;
    }

    private static boolean isPatternCharMatch(
        final String pattern,
        final int patternIndex,
        final char textChar
    ) {
        return patternIndex < pattern.length() && textChar == pattern.charAt(patternIndex);
    }

    /**
     * Computes the Longest Proper Prefix which is also Suffix (LPS) array for the given pattern.
     *
     * @param pattern the pattern for which to compute the LPS array
     * @return the LPS array
     */
    private static int[] computeLpsArray(final String pattern) {
        final int patternLength = pattern.length();
        final int[] lps = new int[patternLength];

        int prefixLength = 0;
        lps[0] = 0;

        for (int index = 1; index < patternLength; index++) {
            final char currentChar = pattern.charAt(index);

            prefixLength = adjustPrefixLength(pattern, lps, prefixLength, currentChar);

            if (pattern.charAt(prefixLength) == currentChar) {
                prefixLength++;
            }

            lps[index] = prefixLength;
        }

        return lps;
    }

    private static int adjustPrefixLength(
        final String pattern,
        final int[] lps,
        int prefixLength,
        final char currentChar
    ) {
        while (prefixLength > 0 && pattern.charAt(prefixLength) != currentChar) {
            prefixLength = lps[prefixLength - 1];
        }
        return prefixLength;
    }
}