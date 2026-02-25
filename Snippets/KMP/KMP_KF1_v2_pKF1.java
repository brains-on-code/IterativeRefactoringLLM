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
        final int[] longestPrefixSuffix = computeLongestPrefixSuffixArray(pattern);

        int patternIndex = 0;
        for (int textIndex = 0; textIndex < textLength; textIndex++) {
            while (patternIndex > 0 && text.charAt(textIndex) != pattern.charAt(patternIndex)) {
                patternIndex = longestPrefixSuffix[patternIndex - 1];
            }

            if (text.charAt(textIndex) == pattern.charAt(patternIndex)) {
                patternIndex++;
            }

            if (patternIndex == patternLength) {
                System.out.println("Pattern starts: " + (textIndex + 1 - patternLength));
                patternIndex = longestPrefixSuffix[patternIndex - 1];
            }
        }
    }

    private static int[] computeLongestPrefixSuffixArray(final String pattern) {
        final int patternLength = pattern.length();
        final int[] longestPrefixSuffix = new int[patternLength];
        longestPrefixSuffix[0] = 0;

        int currentPrefixLength = 0;
        for (int patternIndex = 1; patternIndex < patternLength; patternIndex++) {
            while (currentPrefixLength > 0
                    && pattern.charAt(currentPrefixLength) != pattern.charAt(patternIndex)) {
                currentPrefixLength = longestPrefixSuffix[currentPrefixLength - 1];
            }

            if (pattern.charAt(currentPrefixLength) == pattern.charAt(patternIndex)) {
                currentPrefixLength++;
            }

            longestPrefixSuffix[patternIndex] = currentPrefixLength;
        }
        return longestPrefixSuffix;
    }
}