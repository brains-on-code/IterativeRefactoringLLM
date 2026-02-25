package com.thealgorithms.strings;

/**
 * Implementation of the Knuth–Morris–Pratt (KMP) string matching algorithm.
 * Usage: see the main method for an example.
 */
public final class KMP {

    private KMP() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        final String haystack = "AAAAABAAABA"; // Full string
        final String needle = "AAAA";         // Substring to find
        kmpMatcher(haystack, needle);
    }

    /**
     * Finds and prints all starting indices in {@code haystack} where {@code needle} occurs.
     *
     * @param haystack the text to search within
     * @param needle   the pattern to search for
     */
    public static void kmpMatcher(final String haystack, final String needle) {
        if (haystack == null || needle == null || needle.isEmpty()) {
            return;
        }

        final int textLength = haystack.length();
        final int patternLength = needle.length();
        final int[] prefixTable = computePrefixFunction(needle);

        int matchedChars = 0;

        for (int i = 0; i < textLength; i++) {
            while (matchedChars > 0 && haystack.charAt(i) != needle.charAt(matchedChars)) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            if (haystack.charAt(i) == needle.charAt(matchedChars)) {
                matchedChars++;
            }

            if (matchedChars == patternLength) {
                System.out.println("Pattern starts: " + (i + 1 - patternLength));
                matchedChars = prefixTable[matchedChars - 1];
            }
        }
    }

    /**
     * Computes the prefix function (also known as the failure function) for the KMP algorithm.
     *
     * @param pattern the pattern for which to compute the prefix function
     * @return an array where each index contains the length of the longest proper prefix
     *         which is also a suffix for the substring ending at that index
     */
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