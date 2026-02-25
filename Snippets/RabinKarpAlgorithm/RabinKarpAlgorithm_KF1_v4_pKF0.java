package com.thealgorithms.searches;

/**
 * Rabin-Karp string search implementation.
 * Returns the index of the first occurrence of the pattern in the text,
 * or -1 if the pattern is not found.
 */
public final class RabinKarpSearch {

    private static final int RADIX = 256;

    private RabinKarpSearch() {
        // Utility class; prevent instantiation
    }

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
        if (!isValidInput(pattern, text, modulus)) {
            return -1;
        }

        int patternLength = pattern.length();
        int textLength = text.length();

        if (!isSearchFeasible(patternLength, textLength)) {
            return -1;
        }

        int highestRadixPower = computeHighestRadixPower(patternLength, modulus);
        int patternHash = 0;
        int windowHash = 0;

        // Compute initial hash values for pattern and first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = computeNextHash(patternHash, pattern.charAt(i), modulus);
            windowHash = computeNextHash(windowHash, text.charAt(i), modulus);
        }

        // Slide the pattern over text one character at a time
        for (int i = 0; i <= textLength - patternLength; i++) {
            if (patternHash == windowHash && matchesAt(text, pattern, i)) {
                return i;
            }

            if (hasMoreWindows(i, textLength, patternLength)) {
                windowHash = rollHash(
                    windowHash,
                    text.charAt(i),
                    text.charAt(i + patternLength),
                    highestRadixPower,
                    modulus
                );
            }
        }

        return -1;
    }

    private static boolean isValidInput(String pattern, String text, int modulus) {
        return pattern != null && text != null && modulus > 0;
    }

    private static boolean isSearchFeasible(int patternLength, int textLength) {
        return patternLength > 0 && patternLength <= textLength;
    }

    private static boolean hasMoreWindows(int currentIndex, int textLength, int patternLength) {
        return currentIndex < textLength - patternLength;
    }

    private static int computeHighestRadixPower(int patternLength, int modulus) {
        int highestRadixPower = 1; // RADIX^(patternLength-1) % modulus
        for (int i = 0; i < patternLength - 1; i++) {
            highestRadixPower = (highestRadixPower * RADIX) % modulus;
        }
        return highestRadixPower;
    }

    private static int computeNextHash(int currentHash, char nextChar, int modulus) {
        return (RADIX * currentHash + nextChar) % modulus;
    }

    private static int rollHash(
        int currentHash,
        char outgoingChar,
        char incomingChar,
        int highestRadixPower,
        int modulus
    ) {
        int newHash = currentHash - outgoingChar * highestRadixPower;
        newHash = (RADIX * newHash + incomingChar) % modulus;
        if (newHash < 0) {
            newHash += modulus;
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