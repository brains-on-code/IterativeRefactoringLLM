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
        int currentWindowHash = 0;
        int highestBasePower = 1;

        for (int i = 0; i < patternLength - 1; i++) {
            highestBasePower = (highestBasePower * CHAR_SET_SIZE) % primeModulus;
        }

        for (int i = 0; i < patternLength; i++) {
            patternHash = (CHAR_SET_SIZE * patternHash + pattern.charAt(i)) % primeModulus;
            currentWindowHash = (CHAR_SET_SIZE * currentWindowHash + text.charAt(i)) % primeModulus;
        }

        for (int windowStartIndex = 0; windowStartIndex <= textLength - patternLength; windowStartIndex++) {

            if (patternHash == currentWindowHash) {
                int patternIndex;
                for (patternIndex = 0; patternIndex < patternLength; patternIndex++) {
                    if (text.charAt(windowStartIndex + patternIndex) != pattern.charAt(patternIndex)) {
                        break;
                    }
                }

                if (patternIndex == patternLength) {
                    firstMatchIndex = windowStartIndex;
                    return firstMatchIndex;
                }
            }

            if (windowStartIndex < textLength - patternLength) {
                currentWindowHash = (CHAR_SET_SIZE * (currentWindowHash - text.charAt(windowStartIndex) * highestBasePower)
                        + text.charAt(windowStartIndex + patternLength)) % primeModulus;

                if (currentWindowHash < 0) {
                    currentWindowHash += primeModulus;
                }
            }
        }

        return firstMatchIndex;
    }
}