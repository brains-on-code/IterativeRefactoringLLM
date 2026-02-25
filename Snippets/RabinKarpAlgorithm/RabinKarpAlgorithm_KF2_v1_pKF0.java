package com.thealgorithms.searches;

public final class RabinKarpAlgorithm {

    private static final int ALPHABET_SIZE = 256;

    private RabinKarpAlgorithm() {
        // Utility class; prevent instantiation
    }

    public static int search(String pattern, String text, int primeNumber) {
        if (pattern == null || text == null || primeNumber <= 0) {
            throw new IllegalArgumentException("Invalid input to Rabin-Karp search.");
        }

        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            return -1;
        }

        int patternHash = 0;
        int textHash = 0;
        int highestPower = 1;

        // Precompute (ALPHABET_SIZE^(patternLength-1)) % primeNumber
        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * ALPHABET_SIZE) % primeNumber;
        }

        // Initial hash values for pattern and first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(i)) % primeNumber;
            textHash = (ALPHABET_SIZE * textHash + text.charAt(i)) % primeNumber;
        }

        // Slide the pattern over text
        for (int i = 0; i <= textLength - patternLength; i++) {
            // If hash values match, check characters one by one
            if (patternHash == textHash && matchesAt(text, pattern, i)) {
                return i;
            }

            // Compute hash for next window
            if (i < textLength - patternLength) {
                textHash = (ALPHABET_SIZE * (textHash - text.charAt(i) * highestPower)
                        + text.charAt(i + patternLength)) % primeNumber;

                if (textHash < 0) {
                    textHash += primeNumber;
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