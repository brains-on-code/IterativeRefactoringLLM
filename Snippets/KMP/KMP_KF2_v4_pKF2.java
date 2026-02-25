package com.thealgorithms.strings;

public final class KMP {

    private KMP() {
        // Prevent instantiation of utility class
    }

    public static void main(String[] args) {
        final String text = "AAAAABAAABA";
        final String pattern = "AAAA";
        kmpMatcher(text, pattern);
    }

    /**
     * Searches for all occurrences of {@code pattern} in {@code text} using the
     * Knuth–Morris–Pratt (KMP) algorithm and prints the starting indices.
     *
     * @param text    the text to search within
     * @param pattern the pattern to search for
     */
    public static void kmpMatcher(final String text, final String pattern) {
        final int textLength = text.length();
        final int patternLength = pattern.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            return;
        }

        final int[] prefixTable = computePrefixFunction(pattern);
        int matchedChars = 0;

        for (int i = 0; i < textLength; i++) {
            // Adjust matchedChars until current characters match or no prefix remains
            while (matchedChars > 0 && text.charAt(i) != pattern.charAt(matchedChars)) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            // If characters match, extend current match
            if (text.charAt(i) == pattern.charAt(matchedChars)) {
                matchedChars++;
            }

            // Full pattern matched; report index and continue searching
            if (matchedChars == patternLength) {
                System.out.println("Pattern starts at index: " + (i + 1 - patternLength));
                matchedChars = prefixTable[matchedChars - 1];
            }
        }
    }

    /**
     * Builds the prefix (failure) function for the KMP algorithm.
     * For each position {@code i}, {@code prefixTable[i]} stores the length of
     * the longest proper prefix of the pattern that is also a suffix ending at {@code i}.
     *
     * @param pattern the pattern for which to build the prefix table
     * @return the prefix table array
     */
    private static int[] computePrefixFunction(final String pattern) {
        final int length = pattern.length();
        final int[] prefixTable = new int[length];

        int matchedChars = 0;

        for (int i = 1; i < length; i++) {
            // Adjust matchedChars until current characters match or no prefix remains
            while (matchedChars > 0 && pattern.charAt(matchedChars) != pattern.charAt(i)) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            // If characters match, extend current prefix
            if (pattern.charAt(matchedChars) == pattern.charAt(i)) {
                matchedChars++;
            }

            prefixTable[i] = matchedChars;
        }

        return prefixTable;
    }
}