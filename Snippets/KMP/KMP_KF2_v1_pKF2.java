package com.thealgorithms.strings;

public final class KMP {

    private KMP() {
        // Utility class; prevent instantiation
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
        int matchedChars = 0; // number of characters matched so far

        for (int i = 0; i < textLength; i++) {
            // While mismatch occurs, fall back using prefix table
            while (matchedChars > 0 && text.charAt(i) != pattern.charAt(matchedChars)) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            // If characters match, extend current match
            if (text.charAt(i) == pattern.charAt(matchedChars)) {
                matchedChars++;
            }

            // Full pattern matched
            if (matchedChars == patternLength) {
                System.out.println("Pattern starts at index: " + (i + 1 - patternLength));
                matchedChars = prefixTable[matchedChars - 1]; // continue searching
            }
        }
    }

    /**
     * Builds the prefix function (also known as the failure function) for the
     * KMP algorithm. For each position i, prefixTable[i] stores the length of
     * the longest proper prefix of the pattern that is also a suffix ending at i.
     *
     * @param pattern the pattern for which to build the prefix table
     * @return the prefix table array
     */
    private static int[] computePrefixFunction(final String pattern) {
        final int length = pattern.length();
        final int[] prefixTable = new int[length];

        int matchedChars = 0; // length of current longest prefix-suffix

        for (int i = 1; i < length; i++) {
            // While mismatch occurs, fall back using prefix table
            while (matchedChars > 0 && pattern.charAt(matchedChars) != pattern.charAt(i)) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            // If characters match, extend current prefix-suffix
            if (pattern.charAt(matchedChars) == pattern.charAt(i)) {
                matchedChars++;
            }

            prefixTable[i] = matchedChars;
        }

        return prefixTable;
    }
}