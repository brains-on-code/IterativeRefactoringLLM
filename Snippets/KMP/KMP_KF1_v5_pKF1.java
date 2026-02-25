package com.thealgorithms.strings;

/**
 * Knuth–Morris–Pratt (KMP) pattern searching implementation.
 */
public final class KMPMatcher {

    private KMPMatcher() {
    }

    public static void main(String[] args) {
        final String text = "AAAAABAAABA";
        final String pattern = "AAAA";
        searchPattern(text, pattern);
    }

    public static void searchPattern(final String text, final String pattern) {
        final int textLength = text.length();
        final int patternLength = pattern.length();
        final int[] prefixTable = buildPrefixTable(pattern);

        int patternPosition = 0;
        for (int textPosition = 0; textPosition < textLength; textPosition++) {
            while (patternPosition > 0 && text.charAt(textPosition) != pattern.charAt(patternPosition)) {
                patternPosition = prefixTable[patternPosition - 1];
            }

            if (text.charAt(textPosition) == pattern.charAt(patternPosition)) {
                patternPosition++;
            }

            if (patternPosition == patternLength) {
                System.out.println("Pattern starts: " + (textPosition + 1 - patternLength));
                patternPosition = prefixTable[patternPosition - 1];
            }
        }
    }

    private static int[] buildPrefixTable(final String pattern) {
        final int patternLength = pattern.length();
        final int[] prefixTable = new int[patternLength];
        prefixTable[0] = 0;

        int longestPrefixLength = 0;
        for (int patternPosition = 1; patternPosition < patternLength; patternPosition++) {
            while (longestPrefixLength > 0
                    && pattern.charAt(longestPrefixLength) != pattern.charAt(patternPosition)) {
                longestPrefixLength = prefixTable[longestPrefixLength - 1];
            }

            if (pattern.charAt(longestPrefixLength) == pattern.charAt(patternPosition)) {
                longestPrefixLength++;
            }

            prefixTable[patternPosition] = longestPrefixLength;
        }
        return prefixTable;
    }
}