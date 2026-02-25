package com.thealgorithms.strings;

/**
 * Implementation of the Knuth–Morris–Pratt (KMP) string matching algorithm.
 *
 * <p>Usage example: see the {@link #main(String[])} method.
 */
public final class KMP {

    private KMP() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        final String haystack = "AAAAABAAABA";
        final String needle = "AAAA";
        kmpMatcher(haystack, needle);
    }

    /**
     * Finds and prints all starting indices in {@code haystack} where {@code needle} occurs.
     *
     * @param haystack the text to search within
     * @param needle   the pattern to search for
     */
    public static void kmpMatcher(final String haystack, final String needle) {
        final int textLength = haystack.length();
        final int patternLength = needle.length();
        final int[] prefixTable = computePrefixFunction(needle);

        int matchedChars = 0; // number of characters matched so far

        for (int i = 0; i < textLength; i++) {
            while (matchedChars > 0 && haystack.charAt(i) != needle.charAt(matchedChars)) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            if (haystack.charAt(i) == needle.charAt(matchedChars)) {
                matchedChars++;
            }

            if (matchedChars == patternLength) {
                System.out.println("Pattern starts at index: " + (i + 1 - patternLength));
                matchedChars = prefixTable[matchedChars - 1];
            }
        }
    }

    /**
     * Computes the prefix function (also known as the failure function) for the KMP algorithm.
     *
     * <p>The prefix function for a pattern string {@code p} is an array {@code pi} where
     * {@code pi[i]} is the length of the longest proper prefix of the substring
     * {@code p[0..i]} which is also a suffix of this substring.
     *
     * @param pattern the pattern string
     * @return the prefix function array for the given pattern
     */
    private static int[] computePrefixFunction(final String pattern) {
        final int length = pattern.length();
        final int[] prefixTable = new int[length];

        prefixTable[0] = 0;
        int matchedChars = 0;

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