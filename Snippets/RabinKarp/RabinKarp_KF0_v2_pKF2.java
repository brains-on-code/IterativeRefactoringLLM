package com.thealgorithms.strings;

import java.util.Scanner;

/**
 * Rabin-Karp string matching algorithm.
 *
 * Reads a text and a pattern from standard input and prints all starting
 * indices where the pattern is found in the text.
 */
public final class RabinKarp {

    private static final int ALPHABET_SIZE = 256;
    private static final int DEFAULT_PRIME = 101;

    private RabinKarp() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter String");
            String text = scanner.nextLine();

            System.out.println("Enter pattern");
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
     * @param prime   a prime number used for hashing
     */
    private static void search(String text, String pattern, int prime) {
        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            return;
        }

        int patternHash = 0;
        int textHash = 0;
        int hashMultiplier = 1;

        // hashMultiplier = (ALPHABET_SIZE^(patternLength - 1)) % prime
        for (int i = 0; i < patternLength - 1; i++) {
            hashMultiplier = (hashMultiplier * ALPHABET_SIZE) % prime;
        }

        // Initial hash values for the pattern and the first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(i)) % prime;
            textHash = (ALPHABET_SIZE * textHash + text.charAt(i)) % prime;
        }

        for (int i = 0; i <= textLength - patternLength; i++) {

            if (patternHash == textHash && matchesAt(text, pattern, i)) {
                System.out.println("Pattern found at index " + i);
            }

            if (i < textLength - patternLength) {
                textHash = (ALPHABET_SIZE * (textHash - text.charAt(i) * hashMultiplier)
                        + text.charAt(i + patternLength)) % prime;

                if (textHash < 0) {
                    textHash += prime;
                }
            }
        }
    }

    /**
     * Returns true if {@code pattern} matches {@code text} starting at
     * {@code startIndex}.
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