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
        final String text = "AAAAABAAABA";
        final String pattern = "AAAA";
        kmpMatcher(text, pattern);
    }

    /**
     * Finds and prints all starting indices in {@code text} where {@code pattern} occurs.
     *
     * @param text    the text to search within
     * @param pattern the pattern to search for
     */
    public static void kmpMatcher(final String text, final String pattern) {
        if (text == null || pattern == null || pattern.isEmpty()) {
            return;
        }

        final int textLength = text.length();
        final int patternLength = pattern.length();
        final int[] prefixTable = computePrefixFunction(pattern);

        int patternIndex = 0; // number of characters matched in pattern

        for (int textIndex = 0; textIndex < textLength; textIndex++) {
            char textChar = text.charAt(textIndex);

            while (patternIndex > 0 && textChar != pattern.charAt(patternIndex)) {
                patternIndex = prefixTable[patternIndex - 1];
            }

            if (textChar == pattern.charAt(patternIndex)) {
                patternIndex++;
            }

            if (patternIndex == patternLength) {
                int matchStartIndex = textIndex + 1 - patternLength;
                System.out.println("Pattern starts: " + matchStartIndex);
                patternIndex = prefixTable[patternIndex - 1];
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
        final int patternLength = pattern.length();
        final int[] prefixTable = new int[patternLength];

        int prefixLength = 0;
        prefixTable[0] = 0;

        for (int i = 1; i < patternLength; i++) {
            char currentChar = pattern.charAt(i);

            while (prefixLength > 0 && pattern.charAt(prefixLength) != currentChar) {
                prefixLength = prefixTable[prefixLength - 1];
            }

            if (pattern.charAt(prefixLength) == currentChar) {
                prefixLength++;
            }

            prefixTable[i] = prefixLength;
        }

        return prefixTable;
    }
}