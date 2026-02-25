package com.thealgorithms.searches;

/**
 * Rabin-Karp string search implementation.
 *
 * Uses a rolling hash to find the first occurrence of a pattern in a given text.
 * Returns the starting index of the match, or -1 if the pattern is not found.
 */
public final class RabinKarpSearch {

    private RabinKarpSearch() {
        // Utility class; prevent instantiation
    }

    /** Number of possible characters (extended ASCII). */
    private static final int CHAR_SET_SIZE = 256;

    /**
     * Searches for the first occurrence of {@code pattern} in {@code text}
     * using the Rabin-Karp algorithm with the given modulus.
     *
     * @param pattern the pattern to search for
     * @param text the text in which to search
     * @param modulus the modulus used for hashing (typically a prime)
     * @return the index of the first occurrence of {@code pattern} in {@code text},
     *         or -1 if the pattern is not found
     */
    public static int search(String pattern, String text, int modulus) {
        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            return -1;
        }

        int patternHash = 0;
        int windowHash = 0;

        // highestPower = (CHAR_SET_SIZE^(patternLength - 1)) % modulus
        int highestPower = 1;
        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * CHAR_SET_SIZE) % modulus;
        }

        // Compute initial hash values for the pattern and the first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (CHAR_SET_SIZE * patternHash + pattern.charAt(i)) % modulus;
            windowHash = (CHAR_SET_SIZE * windowHash + text.charAt(i)) % modulus;
        }

        for (int i = 0; i <= textLength - patternLength; i++) {
            // If hash values match, verify the characters
            if (patternHash == windowHash && matchesAt(text, pattern, i)) {
                return i;
            }

            // Roll the hash to the next window:
            // remove the leading character and add the next trailing character
            if (i < textLength - patternLength) {
                windowHash =
                        (CHAR_SET_SIZE * (windowHash - text.charAt(i) * highestPower)
                                        + text.charAt(i + patternLength))
                                % modulus;

                if (windowHash < 0) {
                    windowHash += modulus;
                }
            }
        }

        return -1;
    }

    /**
     * Checks whether {@code pattern} matches {@code text} starting at {@code startIndex}.
     */
    private static boolean matchesAt(String text, String pattern, int startIndex) {
        int patternLength = pattern.length();
        for (int j = 0; j < patternLength; j++) {
            if (text.charAt(startIndex + j) != pattern.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}