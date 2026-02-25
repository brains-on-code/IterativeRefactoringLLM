package com.thealgorithms.searches;

/**
 * Rabin-Karp string search algorithm.
 */
public final class RabinKarpAlgorithm {

    private static final int ALPHABET_SIZE = 256;
    private static final int NOT_FOUND = -1;

    private RabinKarpAlgorithm() {
        // Prevent instantiation
    }

    /**
     * Returns the index of the first occurrence of {@code pattern} in {@code text}
     * using the Rabin-Karp algorithm, or -1 if not found.
     *
     * @param pattern     the pattern to search for
     * @param text        the text to search within
     * @param primeNumber a prime number used as the modulus for hashing
     * @return the starting index of the first occurrence of {@code pattern}
     *         in {@code text}, or -1 if the pattern is not found
     */
    public static int search(String pattern, String text, int primeNumber) {
        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            return NOT_FOUND;
        }

        int patternHash = 0;
        int windowHash = 0;
        int highestPower = 1;

        // highestPower = (ALPHABET_SIZE^(patternLength - 1)) % primeNumber
        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * ALPHABET_SIZE) % primeNumber;
        }

        // Initial hash values for the pattern and the first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(i)) % primeNumber;
            windowHash = (ALPHABET_SIZE * windowHash + text.charAt(i)) % primeNumber;
        }

        for (int i = 0; i <= textLength - patternLength; i++) {

            // If hash values match, verify characters
            if (patternHash == windowHash && matchesAt(text, pattern, i)) {
                return i;
            }

            // Compute hash for the next window
            if (i < textLength - patternLength) {
                windowHash =
                    (ALPHABET_SIZE * (windowHash - text.charAt(i) * highestPower)
                        + text.charAt(i + patternLength)) % primeNumber;

                if (windowHash < 0) {
                    windowHash += primeNumber;
                }
            }
        }

        return NOT_FOUND;
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