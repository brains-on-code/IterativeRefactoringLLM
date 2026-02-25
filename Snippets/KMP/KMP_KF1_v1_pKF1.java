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