package com.thealgorithms.strings;

public final class KMP {

    private KMP() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        String text = "AAAAABAAABA";
        String pattern = "AAAA";
        kmpMatcher(text, pattern);
    }

    public static void kmpMatcher(String text, String pattern) {
        if (text == null || pattern == null) {
            return;
        }

        int textLength = text.length();
        int patternLength = pattern.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            return;
        }

        int[] prefixTable = buildPrefixTable(pattern);
        int matchedLength = 0;

        for (int textIndex = 0; textIndex < textLength; textIndex++) {
            char textChar = text.charAt(textIndex);

            matchedLength = adjustMatchedLength(textChar, pattern, prefixTable, matchedLength);

            if (textChar == pattern.charAt(matchedLength)) {
                matchedLength++;
            }

            if (matchedLength == patternLength) {
                int matchStartIndex = textIndex + 1 - patternLength;
                System.out.println("Pattern starts at index: " + matchStartIndex);
                matchedLength = prefixTable[matchedLength - 1];
            }
        }
    }

    private static int[] buildPrefixTable(String pattern) {
        int patternLength = pattern.length();
        int[] prefixTable = new int[patternLength];

        int matchedLength = 0;
        prefixTable[0] = 0;

        for (int patternIndex = 1; patternIndex < patternLength; patternIndex++) {
            char currentChar = pattern.charAt(patternIndex);

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

    private static int adjustMatchedLength(char textChar, String pattern, int[] prefixTable, int matchedLength) {
        while (matchedLength > 0 && textChar != pattern.charAt(matchedLength)) {
            matchedLength = prefixTable[matchedLength - 1];
        }
        return matchedLength;
    }
}