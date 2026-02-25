package com.thealgorithms.searches;

public final class RabinKarpSearch {
    private RabinKarpSearch() {
    }

    private static final int CHAR_SET_SIZE = 256;

    public static int search(String pattern, String text, int primeModulus) {

        int resultIndex = -1;
        int patternLength = pattern.length();
        int textLength = text.length();
        int patternHash = 0;
        int textHash = 0;
        int highestPower = 1;

        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * CHAR_SET_SIZE) % primeModulus;
        }

        for (int i = 0; i < patternLength; i++) {
            patternHash = (CHAR_SET_SIZE * patternHash + pattern.charAt(i)) % primeModulus;
            textHash = (CHAR_SET_SIZE * textHash + text.charAt(i)) % primeModulus;
        }

        for (int i = 0; i <= textLength - patternLength; i++) {

            int j = 0;
            if (patternHash == textHash) {
                for (j = 0; j < patternLength; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }

                if (j == patternLength) {
                    resultIndex = i;
                    return resultIndex;
                }
            }

            if (i < textLength - patternLength) {
                textHash = (CHAR_SET_SIZE * (textHash - text.charAt(i) * highestPower)
                        + text.charAt(i + patternLength)) % primeModulus;

                if (textHash < 0) {
                    textHash = textHash + primeModulus;
                }
            }
        }
        return resultIndex;
    }
}