package com.thealgorithms.strings;

/**
 * Knuth–Morris–Pratt (KMP) string matching algorithm.
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

        int matchedChars = 0; // number of characters currently matched in needle

        for (int i = 0; i < textLength; i++) {
            // While mismatch and we still have matched characters, fall back using prefix table
            while (matchedChars > 0 && haystack.charAt(i) != needle.charAt(matchedChars)) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            // If characters match, extend current match
            if (haystack.charAt(i) == needle.charAt(matchedChars)) {
                matchedChars++;
            }

            // Full pattern matched
            if (matchedChars == patternLength) {
                System.out.println("Pattern starts at index: " + (i + 1 - patternLength));
                // Continue searching for next match using longest proper prefix
                matchedChars = prefixTable[matchedChars - 1];
            }
        }
    }

    /**
     * Computes the prefix (failure) function for the KMP algorithm.
     *
     * <p>For a pattern string {@code pattern}, the returned array {@code prefixTable} is defined
     * such that {@code prefixTable[i]} is the length of the longest proper prefix of
     * {@code pattern[0..i]} that is also a suffix of {@code pattern[0..i]}.
     *
     * @param pattern the pattern string
     * @return the prefix function array for the given pattern
     */
    private static int[] computePrefixFunction(final String pattern) {
        final int length = pattern.length();
        final int[] prefixTable = new int[length];

        int matchedChars = 0; // length of current longest prefix-suffix

        for (int i = 1; i < length; i++) {
            // While mismatch, fall back to the previous longest prefix-suffix
            while (matchedChars > 0 && pattern.charAt(matchedChars) != pattern.charAt(i)) {
                matchedChars = prefixTable[matchedChars - 1];
            }

            // If characters match, extend current prefix-suffix
            if (pattern.charAt(matchedChars) == pattern.charAt(i)) {
                matchedChars++;
            }

            prefixTable[i] = matchedChars;
        }

        return prefixTable;
    }
}