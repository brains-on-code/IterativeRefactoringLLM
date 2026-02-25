package com.thealgorithms.searches;

// Implementation of Rabin-Karp Algorithm
public final class RabinKarpAlgorithm {

    private RabinKarpAlgorithm() {
    }

    private static final int ALPHABET_SIZE = 256;
    private static final int NOT_FOUND = -1;

    public static int search(String pattern, String text, int primeModulus) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int patternHash = 0;
        int currentWindowHash = 0;
        int highestBasePower = 1;

        // highestBasePower = (ALPHABET_SIZE^(patternLength-1)) % primeModulus
        for (int index = 0; index < patternLength - 1; index++) {
            highestBasePower = (highestBasePower * ALPHABET_SIZE) % primeModulus;
        }

        // Calculate the hash value of the pattern and the first window of text
        for (int index = 0; index < patternLength; index++) {
            patternHash =
                    (ALPHABET_SIZE * patternHash + pattern.charAt(index)) % primeModulus;
            currentWindowHash =
                    (ALPHABET_SIZE * currentWindowHash + text.charAt(index)) % primeModulus;
        }

        // Slide the pattern over text one by one
        for (int windowStartIndex = 0;
                windowStartIndex <= textLength - patternLength;
                windowStartIndex++) {

            // If the hash values match, then check characters one by one
            if (patternHash == currentWindowHash) {
                int patternIndex;
                for (patternIndex = 0; patternIndex < patternLength; patternIndex++) {
                    if (text.charAt(windowStartIndex + patternIndex)
                            != pattern.charAt(patternIndex)) {
                        break;
                    }
                }

                // If all characters match, return the starting index
                if (patternIndex == patternLength) {
                    return windowStartIndex;
                }
            }

            // Calculate hash value for next window of text:
            // Remove leading character, add trailing character
            if (windowStartIndex < textLength - patternLength) {
                currentWindowHash =
                        (ALPHABET_SIZE
                                        * (currentWindowHash
                                                - text.charAt(windowStartIndex) * highestBasePower)
                                + text.charAt(windowStartIndex + patternLength))
                                % primeModulus;

                // Handle negative hash values
                if (currentWindowHash < 0) {
                    currentWindowHash += primeModulus;
                }
            }
        }
        return NOT_FOUND;
    }
}