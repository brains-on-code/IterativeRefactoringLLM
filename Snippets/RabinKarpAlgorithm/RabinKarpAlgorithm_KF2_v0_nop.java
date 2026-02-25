package com.thealgorithms.searches;


public final class RabinKarpAlgorithm {
    private RabinKarpAlgorithm() {
    }

    private static final int ALPHABET_SIZE = 256;

    public static int search(String pattern, String text, int primeNumber) {

        int index = -1;
        int patternLength = pattern.length();
        int textLength = text.length();
        int hashForPattern = 0;
        int hashForText = 0;
        int h = 1;

        for (int i = 0; i < patternLength - 1; i++) {
            h = (h * ALPHABET_SIZE) % primeNumber;
        }

        for (int i = 0; i < patternLength; i++) {
            hashForPattern = (ALPHABET_SIZE * hashForPattern + pattern.charAt(i)) % primeNumber;
            hashForText = (ALPHABET_SIZE * hashForText + text.charAt(i)) % primeNumber;
        }

        for (int i = 0; i <= textLength - patternLength; i++) {


            int j = 0;
            if (hashForPattern == hashForText) {

                for (j = 0; j < patternLength; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }

                if (j == patternLength) {
                    index = i;
                    return index;
                }
            }

            if (i < textLength - patternLength) {
                hashForText = (ALPHABET_SIZE * (hashForText - text.charAt(i) * h) + text.charAt(i + patternLength)) % primeNumber;

                if (hashForText < 0) {
                    hashForText = (hashForText + primeNumber);
                }
            }
        }
        return index;
    }
}
