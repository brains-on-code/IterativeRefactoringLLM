package com.thealgorithms.searches;

public final class RabinKarpSearch {

    private RabinKarpSearch() {
    }

    private static final int CHAR_SET_SIZE = 256;

    public static int search(String pattern, String text, int primeModulus) {
        int firstMatchIndex = -1;

        int patternLength = pattern.length();
        int textLength = text.length();

        int patternHash = 0;
        int windowHash = 0;
        int highestBasePower = 1;

        for (int i = 0; i < patternLength - 1; i++) {
            highestBasePower = (highestBasePower * CHAR_SET_SIZE) % primeModulus;
        }

        for (int i = 0; i < patternLength; i++) {
            patternHash = (CHAR_SET_SIZE * patternHash + pattern.charAt(i)) % primeModulus;
            windowHash = (CHAR_SET_SIZE * windowHash + text.charAt(i)) % primeModulus;
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
                    firstMatchIndex = windowStart;
                    return firstMatchIndex;
                }
            }

            if (windowStart < textLength - patternLength) {
                windowHash = (CHAR_SET_SIZE * (windowHash - text.charAt(windowStart) * highestBasePower)
                        + text.charAt(windowStart + patternLength)) % primeModulus;

                if (windowHash < 0) {
                    windowHash += primeModulus;
                }
            }
        }

        return firstMatchIndex;
    }
}