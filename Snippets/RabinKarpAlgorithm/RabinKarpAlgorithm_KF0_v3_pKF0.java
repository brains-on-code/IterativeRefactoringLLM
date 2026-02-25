package com.thealgorithms.searches;

// Implementation of Rabin-Karp Algorithm
public final class RabinKarpAlgorithm {

    private static final int ALPHABET_SIZE = 256;

    private RabinKarpAlgorithm() {
        // Utility class
    }

    public static int search(String pattern, String text, int primeNumber) {
        validateInput(pattern, text, primeNumber);

        int patternLength = pattern.length();
        int textLength = text.length();

        if (isTrivialNoMatch(patternLength, textLength)) {
            return -1;
        }

        int highestPower = computeHighestPower(patternLength, primeNumber);
        int patternHash = 0;
        int windowHash = 0;

        for (int i = 0; i < patternLength; i++) {
            patternHash = computeNextHash(patternHash, pattern.charAt(i), primeNumber);
            windowHash = computeNextHash(windowHash, text.charAt(i), primeNumber);
        }

        int lastStartIndex = textLength - patternLength;

        for (int startIndex = 0; startIndex <= lastStartIndex; startIndex++) {
            if (patternHash == windowHash && matchesAt(text, pattern, startIndex)) {
                return startIndex;
            }

            if (startIndex < lastStartIndex) {
                windowHash = recomputeWindowHash(
                    windowHash,
                    text.charAt(startIndex),
                    text.charAt(startIndex + patternLength),
                    highestPower,
                    primeNumber
                );
            }
        }

        return -1;
    }

    private static void validateInput(String pattern, String text, int primeNumber) {
        if (pattern == null || text == null) {
            throw new IllegalArgumentException("Pattern and text must not be null");
        }
        if (primeNumber <= 0) {
            throw new IllegalArgumentException("Prime number must be positive");
        }
    }

    private static boolean isTrivialNoMatch(int patternLength, int textLength) {
        return patternLength == 0 || textLength == 0 || patternLength > textLength;
    }

    private static int computeHighestPower(int patternLength, int primeNumber) {
        int highestPower = 1; // ALPHABET_SIZE^(patternLength-1) % primeNumber
        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * ALPHABET_SIZE) % primeNumber;
        }
        return highestPower;
    }

    private static int computeNextHash(int currentHash, char nextChar, int primeNumber) {
        return (ALPHABET_SIZE * currentHash + nextChar) % primeNumber;
    }

    private static int recomputeWindowHash(
        int currentHash,
        char outgoingChar,
        char incomingChar,
        int highestPower,
        int primeNumber
    ) {
        int newHash =
            (ALPHABET_SIZE * (currentHash - outgoingChar * highestPower) + incomingChar) % primeNumber;
        if (newHash < 0) {
            newHash += primeNumber;
        }
        return newHash;
    }

    private static boolean matchesAt(String text, String pattern, int startIndex) {
        int patternLength = pattern.length();
        for (int offset = 0; offset < patternLength; offset++) {
            if (text.charAt(startIndex + offset) != pattern.charAt(offset)) {
                return false;
            }
        }
        return true;
    }
}