package com.thealgorithms.strings;

public final class KMP {

    private KMP() {
    }

    public static void main(String[] args) {
        final String text = "AAAAABAAABA";
        final String pattern = "AAAA";
        searchPattern(text, pattern);
    }

    public static void searchPattern(final String text, final String pattern) {
        final int textLength = text.length();
        final int patternLength = pattern.length();
        final int[] lps = buildLongestPrefixSuffixArray(pattern);
        int patternPosition = 0;

        for (int textPosition = 0; textPosition < textLength; textPosition++) {
            while (patternPosition > 0 && text.charAt(textPosition) != pattern.charAt(patternPosition)) {
                patternPosition = lps[patternPosition - 1];
            }

            if (text.charAt(textPosition) == pattern.charAt(patternPosition)) {
                patternPosition++;
            }

            if (patternPosition == patternLength) {
                System.out.println("Pattern starts at index: " + (textPosition + 1 - patternLength));
                patternPosition = lps[patternPosition - 1];
            }
        }
    }

    private static int[] buildLongestPrefixSuffixArray(final String pattern) {
        final int patternLength = pattern.length();
        final int[] lps = new int[patternLength];
        lps[0] = 0;
        int currentLength = 0;

        for (int patternPosition = 1; patternPosition < patternLength; patternPosition++) {
            while (currentLength > 0 && pattern.charAt(currentLength) != pattern.charAt(patternPosition)) {
                currentLength = lps[currentLength - 1];
            }

            if (pattern.charAt(currentLength) == pattern.charAt(patternPosition)) {
                currentLength++;
            }

            lps[patternPosition] = currentLength;
        }

        return lps;
    }
}