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
        final int[] longestPrefixSuffix = buildLongestPrefixSuffixArray(pattern);
        int patternIndex = 0;

        for (int textIndex = 0; textIndex < textLength; textIndex++) {
            while (patternIndex > 0 && text.charAt(textIndex) != pattern.charAt(patternIndex)) {
                patternIndex = longestPrefixSuffix[patternIndex - 1];
            }

            if (text.charAt(textIndex) == pattern.charAt(patternIndex)) {
                patternIndex++;
            }

            if (patternIndex == patternLength) {
                System.out.println("Pattern starts at index: " + (textIndex + 1 - patternLength));
                patternIndex = longestPrefixSuffix[patternIndex - 1];
            }
        }
    }

    private static int[] buildLongestPrefixSuffixArray(final String pattern) {
        final int patternLength = pattern.length();
        final int[] longestPrefixSuffix = new int[patternLength];
        longestPrefixSuffix[0] = 0;
        int lengthOfCurrentLPS = 0;

        for (int i = 1; i < patternLength; i++) {
            while (lengthOfCurrentLPS > 0 && pattern.charAt(lengthOfCurrentLPS) != pattern.charAt(i)) {
                lengthOfCurrentLPS = longestPrefixSuffix[lengthOfCurrentLPS - 1];
            }

            if (pattern.charAt(lengthOfCurrentLPS) == pattern.charAt(i)) {
                lengthOfCurrentLPS++;
            }

            longestPrefixSuffix[i] = lengthOfCurrentLPS;
        }

        return longestPrefixSuffix;
    }
}