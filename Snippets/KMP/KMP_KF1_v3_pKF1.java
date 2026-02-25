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
        final int[] lpsArray = computeLpsArray(pattern);

        int patternPosition = 0;
        for (int textPosition = 0; textPosition < textLength; textPosition++) {
            while (patternPosition > 0 && text.charAt(textPosition) != pattern.charAt(patternPosition)) {
                patternPosition = lpsArray[patternPosition - 1];
            }

            if (text.charAt(textPosition) == pattern.charAt(patternPosition)) {
                patternPosition++;
            }

            if (patternPosition == patternLength) {
                System.out.println("Pattern starts: " + (textPosition + 1 - patternLength));
                patternPosition = lpsArray[patternPosition - 1];
            }
        }
    }

    private static int[] computeLpsArray(final String pattern) {
        final int patternLength = pattern.length();
        final int[] lpsArray = new int[patternLength];
        lpsArray[0] = 0;

        int currentLpsLength = 0;
        for (int patternPosition = 1; patternPosition < patternLength; patternPosition++) {
            while (currentLpsLength > 0
                    && pattern.charAt(currentLpsLength) != pattern.charAt(patternPosition)) {
                currentLpsLength = lpsArray[currentLpsLength - 1];
            }

            if (pattern.charAt(currentLpsLength) == pattern.charAt(patternPosition)) {
                currentLpsLength++;
            }

            lpsArray[patternPosition] = currentLpsLength;
        }
        return lpsArray;
    }
}