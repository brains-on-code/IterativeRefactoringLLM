package com.thealgorithms.searches;

/**
 * Rabin-Karp string search implementation.
 *
 * Uses a rolling hash to find the first occurrence of a pattern in a given text.
 * Returns the starting index of the match, or -1 if the pattern is not found.
 */
public final class RabinKarpSearch {

    private RabinKarpSearch() {
        // Prevent instantiation of utility class
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
        int highestPower = computeHighestPower(patternLength, modulus);

        // Compute initial hash values for the pattern and the first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = computeNextHash(patternHash, pattern.charAt(i), modulus);
            windowHash = computeNextHash(windowHash, text.charAt(i), modulus);
        }

        for (int i = 0; i <= textLength - patternLength; i++) {
            if (patternHash == windowHash && matchesAt(text, pattern, i)) {
                return i;
            }

            if (i < textLength - patternLength) {
                windowHash = rollHash(
                        windowHash,
                        text.charAt(i),
                        text.charAt(i + patternLength),
                        highestPower,
                        modulus
                );
            }
        }

        return -1;
    }

    private static int computeHighestPower(int patternLength, int modulus) {
        int highestPower = 1;
        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * CHAR_SET_SIZE) % modulus;
        }
        return highestPower;
    }

    private static int computeNextHash(int currentHash, char nextChar, int modulus) {
        return (CHAR_SET_SIZE * currentHash + nextChar) % modulus;
    }

    private static int rollHash(
            int currentHash,
            char outgoingChar,
            char incomingChar,
            int highestPower,
            int modulus
    ) {
        int newHash =
                (CHAR_SET_SIZE * (currentHash - outgoingChar * highestPower) + incomingChar)
                        % modulus;

        if (newHash < 0) {
            newHash += modulus;
        }

        return newHash;
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