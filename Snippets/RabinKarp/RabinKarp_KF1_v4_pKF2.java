package com.thealgorithms.strings;

import java.util.Scanner;

/**
 * Rabin-Karp string matching algorithm.
 *
 * Reads a text and a pattern from standard input and prints all starting
 * indices where the pattern is found in the text.
 */
public final class RabinKarpSearch {

    /** Size of the input character set (extended ASCII). */
    private static final int CHAR_SET_SIZE = 256;

    /** Default prime number used for hashing. */
    private static final int DEFAULT_PRIME = 101;

    private RabinKarpSearch() {
        // Prevent instantiation.
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter text:");
            String text = scanner.nextLine();

            System.out.println("Enter pattern:");
            String pattern = scanner.nextLine();

            rabinKarpSearch(text, pattern, DEFAULT_PRIME);
        }
    }

    /**
     * Performs Rabin-Karp pattern searching.
     *
     * @param text    the text in which to search
     * @param pattern the pattern to search for
     * @param prime   a prime number used for hashing
     */
    private static void rabinKarpSearch(String text, String pattern, int prime) {
        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            return;
        }

        int patternHash = 0;
        int textHash = 0;

        int hashMultiplier = (int) Math.pow(CHAR_SET_SIZE, patternLength - 1) % prime;

        for (int i = 0; i < patternLength; i++) {
            patternHash = (CHAR_SET_SIZE * patternHash + pattern.charAt(i)) % prime;
            textHash = (CHAR_SET_SIZE * textHash + text.charAt(i)) % prime;
        }

        for (int i = 0; i <= textLength - patternLength; i++) {
            if (patternHash == textHash && matchesAt(text, pattern, i)) {
                System.out.println("Pattern found at index " + i);
            }

            if (i < textLength - patternLength) {
                textHash = (CHAR_SET_SIZE * (textHash - text.charAt(i) * hashMultiplier)
                        + text.charAt(i + patternLength)) % prime;

                if (textHash < 0) {
                    textHash += prime;
                }
            }
        }
    }

    /**
     * Checks if the pattern matches the text starting at the given index.
     *
     * @param text       the full text
     * @param pattern    the pattern to match
     * @param startIndex the starting index in the text
     * @return true if the pattern matches at startIndex, false otherwise
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