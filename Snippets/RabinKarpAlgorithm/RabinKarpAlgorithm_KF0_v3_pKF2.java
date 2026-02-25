package com.thealgorithms.searches;

/**
 * Rabin-Karp string search algorithm.
 */
public final class RabinKarpAlgorithm {

    private static final int ALPHABET_SIZE = 256;
    private static final int NOT_FOUND = -1;

    private RabinKarpAlgorithm() {
        // Utility class; prevent instantiation
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

        highestPower = computeHighestPower(patternLength, primeNumber);

        int[] initialHashes = computeInitialHashes(pattern, text, patternLength, primeNumber);
        patternHash = initialHashes[0];
        windowHash = initialHashes[1];

        for (int i = 0; i <= textLength - patternLength; i++) {
            if (patternHash == windowHash && matchesAt(text, pattern, i)) {
                return i;
            }

            if (i < textLength - patternLength) {
                windowHash = recomputeWindowHash(
                    windowHash,
                    text.charAt(i),
                    text.charAt(i + patternLength),
                    highestPower,
                    primeNumber
                );
            }
        }

        return NOT_FOUND;
    }

    private static int computeHighestPower(int patternLength, int primeNumber) {
        int highestPower = 1;
        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * ALPHABET_SIZE) % primeNumber;
        }
        return highestPower;
    }

    private static int[] computeInitialHashes(
        String pattern,
        String text,
        int patternLength,
        int primeNumber
    ) {
        int patternHash = 0;
        int windowHash = 0;

        for (int i = 0; i < patternLength; i++) {
            patternHash =
                (ALPHABET_SIZE * patternHash + pattern.charAt(i)) % primeNumber;
            windowHash =
                (ALPHABET_SIZE * windowHash + text.charAt(i)) % primeNumber;
        }

        return new int[] {patternHash, windowHash};
    }

    private static int recomputeWindowHash(
        int currentHash,
        char outgoingChar,
        char incomingChar,
        int highestPower,
        int primeNumber
    ) {
        int newHash =
            (ALPHABET_SIZE * (currentHash - outgoingChar * highestPower)
                + incomingChar) % primeNumber;

        if (newHash < 0) {
            newHash += primeNumber;
        }

        return newHash;
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