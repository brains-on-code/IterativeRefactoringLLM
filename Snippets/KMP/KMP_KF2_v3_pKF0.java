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

    public static void kmpMatcher(final String text, final String pattern) {
        if (text == null || pattern == null) {
            return;
        }

        final int textLength = text.length();
        final int patternLength = pattern.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            return;
        }

        final int[] prefixTable = buildPrefixTable(pattern);
        int matchedLength = 0;

        for (int textIndex = 0; textIndex < textLength; textIndex++) {
            final char textChar = text.charAt(textIndex);

            while (matchedLength > 0 && textChar != pattern.charAt(matchedLength)) {
                matchedLength = prefixTable[matchedLength - 1];
            }

            if (textChar == pattern.charAt(matchedLength)) {
                matchedLength++;
            }

            if (matchedLength == patternLength) {
                final int matchStartIndex = textIndex + 1 - patternLength;
                System.out.println("Pattern starts at index: " + matchStartIndex);
                matchedLength = prefixTable[matchedLength - 1];
            }
        }
    }

    private static int[] buildPrefixTable(final String pattern) {
        final int patternLength = pattern.length();
        final int[] prefixTable = new int[patternLength];

        int matchedLength = 0;
        prefixTable[0] = 0;

        for (int patternIndex = 1; patternIndex < patternLength; patternIndex++) {
            final char currentChar = pattern.charAt(patternIndex);

            while (matchedLength > 0 && pattern.charAt(matchedLength) != currentChar) {
                matchedLength = prefixTable[matchedLength - 1];
            }

            if (pattern.charAt(matchedLength) == currentChar) {
                matchedLength++;
            }

            prefixTable[patternIndex] = matchedLength;
        }

        return prefixTable;
    }
}