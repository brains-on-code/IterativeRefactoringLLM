package com.thealgorithms.strings;

import java.util.Scanner;

/**
 * Rabin-Karp string matching algorithm.
 *
 * Reads a text and a pattern from standard input and prints all starting
 * indices where the pattern is found in the text.
 */
public final class RabinKarp {

    /** Size of the input alphabet (extended ASCII). */
    private static final int ALPHABET_SIZE = 256;

    /** Default prime modulus used for hashing. */
    private static final int DEFAULT_PRIME = 101;

    private RabinKarp() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter text:");
            String text = scanner.nextLine();

            System.out.println("Enter pattern:");
            String pattern = scanner.nextLine();

            search(text, pattern, DEFAULT_PRIME);
        }
    }

    /**
     * Searches for all occurrences of {@code pattern} in {@code text} using the
     * Rabin-Karp algorithm and prints their starting indices.
     *
     * @param text    the text to search within
     * @param pattern the pattern to search for
     * @param prime   a prime number used as the modulus in hashing
     */
    private static void search(String text, String pattern, int prime) {
        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            return;
        }

        int patternHash = 0;
        int textHash = 0;

        // Precompute (ALPHABET_SIZE^(patternLength - 1)) % prime.
        int hashMultiplier = 1;
        for (int i = 0; i < patternLength - 1; i++) {
            hashMultiplier = (hashMultiplier * ALPHABET_SIZE) % prime;
        }

        // Compute initial hash values for the pattern and the first window of text.
        for (int i = 0; i < patternLength; i++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(i)) % prime;
            textHash = (ALPHABET_SIZE * textHash + text.charAt(i)) % prime;
        }

        // Slide the pattern over the text one character at a time.
        for (int i = 0; i <= textLength - patternLength; i++) {

            // If hash values match, verify characters one by one.
            if (patternHash == textHash && matchesAt(text, pattern, i)) {
                System.out.println("Pattern found at index " + i);
            }

            // Compute hash for the next window of text.
            if (i < textLength - patternLength) {
                textHash =
                    (ALPHABET_SIZE * (textHash - text.charAt(i) * hashMultiplier)
                        + text.charAt(i + patternLength)) %
                    prime;

                // Ensure positive hash value.
                if (textHash < 0) {
                    textHash += prime;
                }
            }
        }
    }

    /**
     * Checks whether {@code pattern} matches {@code text} starting at
     * {@code startIndex}.
     *
     * @param text       the text to check within
     * @param pattern    the pattern to compare
     * @param startIndex the starting index in {@code text}
     * @return {@code true} if the pattern matches at {@code startIndex},
     *         {@code false} otherwise
     */
    private static boolean matchesAt(String text, String pattern, int startIndex) {
        int patternLength = pattern.length();
        for (int j = 0; j < patternLength; j++) {
            if (text.charAt(startIndex + j) != pattern.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}