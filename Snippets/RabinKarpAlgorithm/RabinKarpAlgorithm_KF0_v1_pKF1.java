package com.thealgorithms.searches;

// Implementation of Rabin Karp Algorithm

public final class RabinKarpAlgorithm {
    private RabinKarpAlgorithm() {
    }

    private static final int ALPHABET_SIZE = 256;

    public static int search(String pattern, String text, int primeModulus) {

        int foundIndex = -1; // -1 represents "not found"
        int patternLength = pattern.length();
        int textLength = text.length();
        int patternHash = 0;
        int windowHash = 0;
        int highestPower = 1;

        // highestPower = (ALPHABET_SIZE^(patternLength-1)) % primeModulus
        for (int i = 0; i < patternLength - 1; i++) {
            highestPower = (highestPower * ALPHABET_SIZE) % primeModulus;
        }

        // Calculate the hash value of the pattern and the first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(i)) % primeModulus;
            windowHash = (ALPHABET_SIZE * windowHash + text.charAt(i)) % primeModulus;
        }

        // Slide the pattern over text one by one
        for (int windowStart = 0; windowStart <= textLength - patternLength; windowStart++) {

            // If the hash values match, then check characters one by one
            if (patternHash == windowHash) {
                int matchIndex;
                for (matchIndex = 0; matchIndex < patternLength; matchIndex++) {
                    if (text.charAt(windowStart + matchIndex) != pattern.charAt(matchIndex)) {
                        break;
                    }
                }

                // If all characters match, return the starting index
                if (matchIndex == patternLength) {
                    foundIndex = windowStart;
                    return foundIndex;
                }
            }

            // Calculate hash value for next window of text:
            // Remove leading character, add trailing character
            if (windowStart < textLength - patternLength) {
                windowHash =
                        (ALPHABET_SIZE * (windowHash - text.charAt(windowStart) * highestPower)
                                + text.charAt(windowStart + patternLength))
                                % primeModulus;

                // Handle negative hash values
                if (windowHash < 0) {
                    windowHash += primeModulus;
                }
            }
        }
        return foundIndex; // return -1 if pattern is not found
    }
}
// This code is contributed by nuclode