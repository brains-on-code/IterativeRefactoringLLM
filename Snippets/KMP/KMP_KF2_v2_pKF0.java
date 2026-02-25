package com.thealgorithms.strings;

public final class KMP {

    private KMP() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        final String haystack = "AAAAABAAABA";
        final String needle = "AAAA";
        kmpMatcher(haystack, needle);
    }

    public static void kmpMatcher(final String haystack, final String needle) {
        if (haystack == null || needle == null) {
            return;
        }

        final int haystackLength = haystack.length();
        final int needleLength = needle.length();

        if (needleLength == 0 || haystackLength == 0 || needleLength > haystackLength) {
            return;
        }

        final int[] prefixTable = buildPrefixTable(needle);
        int matchedChars = 0;

        for (int i = 0; i < haystackLength; i++) {
            char haystackChar = haystack.charAt(i);

            while (matchedChars > 0 && haystackChar != needle.charAt(matchedChars)) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            if (haystackChar == needle.charAt(matchedChars)) {
                matchedChars++;
            }

            if (matchedChars == needleLength) {
                int matchStartIndex = i + 1 - needleLength;
                System.out.println("Pattern starts at index: " + matchStartIndex);
                matchedChars = prefixTable[matchedChars - 1];
            }
        }
    }

    private static int[] buildPrefixTable(final String pattern) {
        final int length = pattern.length();
        final int[] prefixTable = new int[length];

        int matchedChars = 0;
        prefixTable[0] = 0;

        for (int i = 1; i < length; i++) {
            char currentChar = pattern.charAt(i);

            while (matchedChars > 0 && pattern.charAt(matchedChars) != currentChar) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            if (pattern.charAt(matchedChars) == currentChar) {
                matchedChars++;
            }

            prefixTable[i] = matchedChars;
        }

        return prefixTable;
    }
}