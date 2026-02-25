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
        final int[] prefixTable = computePrefixTable(pattern);

        int matchedChars = 0;

        for (int textIndex = 0; textIndex < textLength; textIndex++) {
            while (matchedChars > 0 && text.charAt(textIndex) != pattern.charAt(matchedChars)) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            if (text.charAt(textIndex) == pattern.charAt(matchedChars)) {
                matchedChars++;
            }

            if (matchedChars == patternLength) {
                System.out.println("Pattern starts at index: " + (textIndex + 1 - patternLength));
                matchedChars = prefixTable[matchedChars - 1];
            }
        }
    }

    /**
     * Computes the prefix table (also known as the failure function) for the KMP algorithm.
     */
    private static int[] computePrefixTable(final String pattern) {
        final int patternLength = pattern.length();
        final int[] prefixTable = new int[patternLength];

        prefixTable[0] = 0;
        int matchedPrefixLength = 0;

        for (int patternIndex = 1; patternIndex < patternLength; patternIndex++) {
            while (matchedPrefixLength > 0 && pattern.charAt(matchedPrefixLength) != pattern.charAt(patternIndex)) {
                matchedPrefixLength = prefixTable[matchedPrefixLength - 1];
            }

            if (pattern.charAt(matchedPrefixLength) == pattern.charAt(patternIndex)) {
                matchedPrefixLength++;
            }

            prefixTable[patternIndex] = matchedPrefixLength;
        }

        return prefixTable;
    }
}