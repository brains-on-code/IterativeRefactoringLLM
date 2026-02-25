package com.thealgorithms.searches;

public final class RabinKarpSearch {

    private RabinKarpSearch() {
    }

    private static final int RADIX = 256;

    public static int search(String pattern, String text, int primeModulus) {
        int firstMatchIndex = -1;

        int patternLength = pattern.length();
        int textLength = text.length();

        int patternHash = 0;
        int currentWindowHash = 0;
        int highestRadixPower = 1;

        for (int i = 0; i < patternLength - 1; i++) {
            highestRadixPower = (highestRadixPower * RADIX) % primeModulus;
        }

        for (int i = 0; i < patternLength; i++) {
            patternHash = (RADIX * patternHash + pattern.charAt(i)) % primeModulus;
            currentWindowHash = (RADIX * currentWindowHash + text.charAt(i)) % primeModulus;
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
                currentWindowHash = (RADIX * (currentWindowHash - text.charAt(windowStartIndex) * highestRadixPower)
                        + text.charAt(windowStartIndex + patternLength)) % primeModulus;

                if (currentWindowHash < 0) {
                    currentWindowHash += primeModulus;
                }
            }
        }

        return firstMatchIndex;
    }
}