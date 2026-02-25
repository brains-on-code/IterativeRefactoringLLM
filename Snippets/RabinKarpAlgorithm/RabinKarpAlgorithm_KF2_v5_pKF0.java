package com.thealgorithms.searches;

public final class RabinKarpAlgorithm {

    private static final int ALPHABET_SIZE = 256;

    private RabinKarpAlgorithm() {
        // Utility class; prevent instantiation
    }

    public static int search(String pattern, String text, int primeNumber) {
        validateInput(pattern, text, primeNumber);

        int patternLength = pattern.length();
        int textLength = text.length();

        if (isTrivialNoMatch(patternLength, textLength)) {
            return -1;
        }

        int highestPower = computeHighestPower(patternLength, primeNumber);
        int patternHash = computeInitialHash(pattern, patternLength, primeNumber);
        int textHash = computeInitialHash(text, patternLength, primeNumber);

        int lastStartIndex = textLength - patternLength;
        for (int startIndex = 0; startIndex <= lastStartIndex; startIndex++) {
            if (patternHash == textHash && matchesAt(text, pattern, startIndex)) {
                return startIndex;
            }

            if (startIndex < lastStartIndex) {
                textHash = recomputeHash(
                    textHash,
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
            throw new IllegalArgumentException("Pattern and text must not be null.");
        }
        if (primeNumber <= 0) {
            throw new IllegalArgumentException("Prime number must be positive.");
        }
    }

    private static boolean isTrivialNoMatch(int patternLength, int textLength) {
        return patternLength == 0 || textLength == 0 || patternLength > textLength;
    }

    private static int computeHighestPower(int patternLength, int primeNumber) {
        int highestPower = 1;
        int iterations = patternLength - 1;

        for (int i = 0; i < iterations; i++) {
            highestPower = (highestPower * ALPHABET_SIZE) % primeNumber;
        }

        return highestPower;
    }

    private static int computeInitialHash(String input, int length, int primeNumber) {
        int hash = 0;

        for (int i = 0; i < length; i++) {
            hash = (ALPHABET_SIZE * hash + input.charAt(i)) % primeNumber;
        }

        return hash;
    }

    private static int recomputeHash(
        int currentHash,
        char outgoingChar,
        char incomingChar,
        int highestPower,
        int primeNumber
    ) {
        int outgoingContribution = outgoingChar * highestPower;
        int newHash = (ALPHABET_SIZE * (currentHash - outgoingContribution) + incomingChar) % primeNumber;

        if (newHash < 0) {
            newHash += primeNumber;
        }

        return newHash;
    }

    private static boolean matchesAt(String text, String pattern, int startIndex) {
        int patternLength = pattern.length();
        int endIndex = startIndex + patternLength;

        for (int textIndex = startIndex, patternIndex = 0;
             textIndex < endIndex;
             textIndex++, patternIndex++) {

            if (text.charAt(textIndex) != pattern.charAt(patternIndex)) {
                return false;
            }
        }

        return true;
    }
}