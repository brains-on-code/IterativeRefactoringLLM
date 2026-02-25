package com.thealgorithms.searches;

/**
 * Rabin-Karp string search implementation.
 * Returns the index of the first occurrence of the pattern in the text,
 * or -1 if the pattern is not found.
 */
public final class RabinKarpSearch {

    private RabinKarpSearch() {
        // Utility class; prevent instantiation
    }

    private static final int RADIX = 256;

    /**
     * Searches for the first occurrence of {@code pattern} in {@code text}
     * using the Rabin-Karp algorithm with the given modulus.
     *
     * @param pattern the pattern to search for
     * @param text    the text in which to search
     * @param modulus the modulus used for hashing
     * @return the starting index of the first occurrence of pattern in text,
     *         or -1 if not found
     */
    public static int search(String pattern, String text, int modulus) {
        if (pattern == null || text == null || modulus <= 0) {
            return -1;
        }

        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || patternLength > textLength) {
            return -1;
        }

        int patternHash = 0;
        int windowHash = 0;
        int highestRadixPower = 1; // RADIX^(patternLength-1) % modulus

        // Precompute highestRadixPower = RADIX^(patternLength-1) % modulus
        for (int i = 0; i < patternLength - 1; i++) {
            highestRadixPower = (highestRadixPower * RADIX) % modulus;
        }

        // Compute initial hash values for pattern and first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (RADIX * patternHash + pattern.charAt(i)) % modulus;
            windowHash = (RADIX * windowHash + text.charAt(i)) % modulus;
        }

        // Slide the pattern over text one character at a time
        for (int i = 0; i <= textLength - patternLength; i++) {
            // If hash values match, verify characters one by one
            if (patternHash == windowHash && matchesAt(text, pattern, i)) {
                return i;
            }

            // Compute hash for next window of text:
            // Remove leading character and add trailing character
            if (i < textLength - patternLength) {
                windowHash =
                    (RADIX * (windowHash - text.charAt(i) * highestRadixPower)
                        + text.charAt(i + patternLength)) % modulus;

                if (windowHash < 0) {
                    windowHash += modulus;
                }
            }
        }

        return -1;
    }

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