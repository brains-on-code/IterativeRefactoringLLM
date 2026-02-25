package com.thealgorithms.searches;

public final class RabinKarpAlgorithm {

    private RabinKarpAlgorithm() {
        // Prevent instantiation
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

        // highestPower = (ALPHABET_SIZE^(patternLength - 1)) % primeNumber
        int highestPower = 1;
        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * ALPHABET_SIZE) % primeNumber;
        }

        // Initial hash values for the pattern and the first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(i)) % primeNumber;
            windowHash = (ALPHABET_SIZE * windowHash + text.charAt(i)) % primeNumber;
        }

        // Slide the pattern over the text
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

            // Compute hash for the next window of text
            if (i < textLength - patternLength) {
                windowHash =
                    (ALPHABET_SIZE * (windowHash - text.charAt(i) * highestPower)
                        + text.charAt(i + patternLength)) % primeNumber;

                // Ensure the hash value is positive
                if (windowHash < 0) {
                    windowHash += primeNumber;
                }
            }
        }

        return -1;
    }
}