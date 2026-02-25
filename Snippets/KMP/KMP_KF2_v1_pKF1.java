package com.thealgorithms.strings;

public final class KMP {

    private KMP() {
    }

    public static void main(String[] args) {
        final String text = "AAAAABAAABA";
        final String pattern = "AAAA";
        kmpSearch(text, pattern);
    }

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

    private static int[] computePrefixTable(final String pattern) {
        final int patternLength = pattern.length();
        final int[] prefixTable = new int[patternLength];
        prefixTable[0] = 0;
        int matchedChars = 0;

        for (int patternIndex = 1; patternIndex < patternLength; patternIndex++) {
            while (matchedChars > 0 && pattern.charAt(matchedChars) != pattern.charAt(patternIndex)) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            if (pattern.charAt(matchedChars) == pattern.charAt(patternIndex)) {
                matchedChars++;
            }

            prefixTable[patternIndex] = matchedChars;
        }

        return prefixTable;
    }
}