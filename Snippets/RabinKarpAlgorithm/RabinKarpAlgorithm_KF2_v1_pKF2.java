package com.thealgorithms.searches;

public final class RabinKarpAlgorithm {

    private RabinKarpAlgorithm() {
        // Utility class; prevent instantiation
    }

    /** Size of the input alphabet (extended ASCII). */
    private static final int ALPHABET_SIZE = 256;

    /**
     * Searches for the first occurrence of {@code pattern} in {@code text} using the Rabin-Karp algorithm.
     *
     * @param pattern     the substring to search for
     * @param text        the text in which to search
     * @param primeNumber a prime number used as the modulus for hashing
     * @return the index of the first occurrence of {@code pattern} in {@code text},
     *         or -1 if the pattern is not found
     */
    public static int search(String pattern, String text, int primeNumber) {
        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            return -1;
        }

        int patternHash = 0;
        int windowHash = 0;
        int highestPower = 1; // ALPHABET_SIZE^(patternLength-1) % primeNumber

        // Precompute highestPower = (ALPHABET_SIZE^(patternLength-1)) % primeNumber
        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * ALPHABET_SIZE) % primeNumber;
        }

        // Compute initial hash values for pattern and first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(i)) % primeNumber;
            windowHash = (ALPHABET_SIZE * windowHash + text.charAt(i)) % primeNumber;
        }

        // Slide the pattern over text one character at a time
        for (int i = 0; i <= textLength - patternLength; i++) {

            // If hash values match, verify characters one by one
            if (patternHash == windowHash) {
                int j;
                for (j = 0; j < patternLength; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }
                if (j == patternLength) {
                    return i; // Match found at index i
                }
            }

            // Compute hash for next window: remove leading char, add trailing char
            if (i < textLength - patternLength) {
                windowHash =
                    (ALPHABET_SIZE * (windowHash - text.charAt(i) * highestPower)
                        + text.charAt(i + patternLength)) % primeNumber;

                // Ensure positive hash value
                if (windowHash < 0) {
                    windowHash += primeNumber;
                }
            }
        }

        return -1; // No match found
    }
}