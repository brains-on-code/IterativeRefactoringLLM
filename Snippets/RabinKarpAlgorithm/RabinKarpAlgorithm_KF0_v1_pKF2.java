package com.thealgorithms.searches;

/**
 * Implementation of the Rabin-Karp string search algorithm.
 */
public final class RabinKarpAlgorithm {

    private static final int ALPHABET_SIZE = 256;

    private RabinKarpAlgorithm() {
        // Utility class; prevent instantiation
    }

    /**
     * Searches for the first occurrence of {@code pattern} in {@code text}
     * using the Rabin-Karp algorithm.
     *
     * @param pattern     the pattern to search for
     * @param text        the text to search within
     * @param primeNumber a prime number used as the modulus for hashing
     * @return the starting index of the first occurrence of {@code pattern}
     *         in {@code text}, or -1 if the pattern is not found
     */
    public static int search(String pattern, String text, int primeNumber) {
        int notFoundIndex = -1;
        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            return notFoundIndex;
        }

        int patternHash = 0;
        int windowHash = 0;
        int highestPower = 1;

        // highestPower = (ALPHABET_SIZE^(patternLength - 1)) % primeNumber
        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * ALPHABET_SIZE) % primeNumber;
        }

        // Compute initial hash values for the pattern and the first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(i)) % primeNumber;
            windowHash = (ALPHABET_SIZE * windowHash + text.charAt(i)) % primeNumber;
        }

        // Slide the pattern over the text
        for (int i = 0; i <= textLength - patternLength; i++) {

            // If hash values match, verify characters one by one
            if (patternHash == windowHash) {
                int j;
                for (j = 0; j < patternLength; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }

                // All characters matched
                if (j == patternLength) {
                    return i;
                }
            }

            // Compute hash for the next window:
            // remove leading character, add trailing character
            if (i < textLength - patternLength) {
                windowHash =
                    (ALPHABET_SIZE * (windowHash - text.charAt(i) * highestPower)
                        + text.charAt(i + patternLength)) % primeNumber;

                // Ensure non-negative hash value
                if (windowHash < 0) {
                    windowHash += primeNumber;
                }
            }
        }

        return notFoundIndex;
    }
}