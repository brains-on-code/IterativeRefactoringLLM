package com.thealgorithms.searches;

/**
 * Rabin-Karp string search implementation.
 *
 * Finds the first occurrence of a pattern in a given text using
 * a rolling hash. Returns the starting index of the match, or -1
 * if the pattern is not found.
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
        int highestPower = 1; // CHAR_SET_SIZE^(patternLength - 1) % modulus

        // Precompute highestPower = (CHAR_SET_SIZE^(patternLength - 1)) % modulus
        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * CHAR_SET_SIZE) % modulus;
        }

        // Compute initial hash values for pattern and first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (CHAR_SET_SIZE * patternHash + pattern.charAt(i)) % modulus;
            windowHash = (CHAR_SET_SIZE * windowHash + text.charAt(i)) % modulus;
        }

        // Slide the pattern over text one character at a time
        for (int i = 0; i <= textLength - patternLength; i++) {
            // If hash values match, verify characters one by one
            if (patternHash == windowHash) {
                int j = 0;
                while (j < patternLength && text.charAt(i + j) == pattern.charAt(j)) {
                    j++;
                }
                if (j == patternLength) {
                    return i;
                }
            }

            // Compute hash for next window of text:
            // remove leading character and add trailing character
            if (i < textLength - patternLength) {
                windowHash =
                        (CHAR_SET_SIZE * (windowHash - text.charAt(i) * highestPower)
                                        + text.charAt(i + patternLength))
                                % modulus;

                // Ensure positive hash value
                if (windowHash < 0) {
                    windowHash += modulus;
                }
            }
        }

        return -1;
    }
}