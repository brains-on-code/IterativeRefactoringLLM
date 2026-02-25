package com.thealgorithms.searches;

public final class RabinKarpAlgorithm {

    private RabinKarpAlgorithm() {
    }

    private static final int ALPHABET_SIZE = 256;

    public static int search(String pattern, String text, int primeModulus) {
        int matchIndex = -1;
        int patternLength = pattern.length();
        int textLength = text.length();
        int patternHash = 0;
        int windowHash = 0;
        int highestPower = 1;

        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * ALPHABET_SIZE) % primeModulus;
        }

        for (int i = 0; i < patternLength; i++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(i)) % primeModulus;
            windowHash = (ALPHABET_SIZE * windowHash + text.charAt(i)) % primeModulus;
        }

        for (int windowStart = 0; windowStart <= textLength - patternLength; windowStart++) {
            if (patternHash == windowHash) {
                int patternIndex;
                for (patternIndex = 0; patternIndex < patternLength; patternIndex++) {
                    if (text.charAt(windowStart + patternIndex) != pattern.charAt(patternIndex)) {
                        break;
                    }
                }

                if (patternIndex == patternLength) {
                    matchIndex = windowStart;
                    return matchIndex;
                }
            }

            if (windowStart < textLength - patternLength) {
                windowHash =
                    (ALPHABET_SIZE * (windowHash - text.charAt(windowStart) * highestPower)
                        + text.charAt(windowStart + patternLength))
                        % primeModulus;

                if (windowHash < 0) {
                    windowHash += primeModulus;
                }
            }
        }

        return matchIndex;
    }
}