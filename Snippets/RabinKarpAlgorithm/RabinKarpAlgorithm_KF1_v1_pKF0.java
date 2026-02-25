package com.thealgorithms.searches;

/**
 * Rabin-Karp string search implementation.
 * Returns the index of the first occurrence of the pattern in the text,
 * or -1 if the pattern is not found.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    private static final int RADIX = 256;

    /**
     * Searches for the first occurrence of {@code pattern} in {@code text}
     * using the Rabin-Karp algorithm with the given modulus.
     *
     * @param pattern the pattern to search for
     * @param text    the text in which to search
     * @param modulus the modulus used for hashing
     * @return the starting index of the first occurrence of pattern in text,
     *         or -1 if not found
     */
    public static int method1(String pattern, String text, int modulus) {
        if (pattern == null || text == null) {
            return -1;
        }

        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || patternLength > textLength || modulus <= 0) {
            return -1;
        }

        int resultIndex = -1;
        int patternHash = 0;
        int textHash = 0;
        int highestRadixPower = 1; // RADIX^(patternLength-1) % modulus

        // Precompute highestRadixPower = RADIX^(patternLength-1) % modulus
        for (int i = 0; i < patternLength - 1; i++) {
            highestRadixPower = (highestRadixPower * RADIX) % modulus;
        }

        // Compute initial hash values for pattern and first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (RADIX * patternHash + pattern.charAt(i)) % modulus;
            textHash = (RADIX * textHash + text.charAt(i)) % modulus;
        }

        // Slide the pattern over text one character at a time
        for (int i = 0; i <= textLength - patternLength; i++) {
            // If hash values match, check characters one by one
            if (patternHash == textHash) {
                int j;
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

            // Compute hash for next window of text:
            // Remove leading character and add trailing character
            if (i < textLength - patternLength) {
                textHash =
                    (RADIX * (textHash - text.charAt(i) * highestRadixPower)
                        + text.charAt(i + patternLength)) % modulus;

                if (textHash < 0) {
                    textHash += modulus;
                }
            }
        }

        return resultIndex;
    }
}