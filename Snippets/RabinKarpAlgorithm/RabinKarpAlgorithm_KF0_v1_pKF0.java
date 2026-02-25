package com.thealgorithms.searches;

// Implementation of Rabin Karp Algorithm
public final class RabinKarpAlgorithm {

    private static final int ALPHABET_SIZE = 256;

    private RabinKarpAlgorithm() {
        // Utility class
    }

    public static int search(String pattern, String text, int primeNumber) {
        if (pattern == null || text == null || primeNumber <= 0) {
            throw new IllegalArgumentException("Invalid input to Rabin-Karp search");
        }

        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            return -1;
        }

        int patternHash = 0;
        int windowHash = 0;
        int highestPower = 1; // ALPHABET_SIZE^(patternLength-1) % primeNumber

        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * ALPHABET_SIZE) % primeNumber;
        }

        for (int i = 0; i < patternLength; i++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(i)) % primeNumber;
            windowHash = (ALPHABET_SIZE * windowHash + text.charAt(i)) % primeNumber;
        }

        for (int i = 0; i <= textLength - patternLength; i++) {
            if (patternHash == windowHash && matchesAt(text, pattern, i)) {
                return i;
            }

            if (i < textLength - patternLength) {
                windowHash = (ALPHABET_SIZE * (windowHash - text.charAt(i) * highestPower)
                        + text.charAt(i + patternLength)) % primeNumber;

                if (windowHash < 0) {
                    windowHash += primeNumber;
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