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
        final int haystackLength = haystack.length();
        final int needleLength = needle.length();

        if (needleLength == 0 || haystackLength == 0 || needleLength > haystackLength) {
            return;
        }

        final int[] prefixTable = computePrefixFunction(needle);
        int matchedChars = 0;

        for (int i = 0; i < haystackLength; i++) {
            while (matchedChars > 0 && haystack.charAt(i) != needle.charAt(matchedChars)) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            if (haystack.charAt(i) == needle.charAt(matchedChars)) {
                matchedChars++;
            }

            if (matchedChars == needleLength) {
                System.out.println("Pattern starts at index: " + (i + 1 - needleLength));
                matchedChars = prefixTable[matchedChars - 1];
            }
        }
    }

    private static int[] computePrefixFunction(final String pattern) {
        final int length = pattern.length();
        final int[] prefixTable = new int[length];

        int matchedChars = 0;
        prefixTable[0] = 0;

        for (int i = 1; i < length; i++) {
            while (matchedChars > 0 && pattern.charAt(matchedChars) != pattern.charAt(i)) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            if (pattern.charAt(matchedChars) == pattern.charAt(i)) {
                matchedChars++;
            }

            prefixTable[i] = matchedChars;
        }

        return prefixTable;
    }
}