package com.thealgorithms.strings;

import java.util.Scanner;

/**
 * Rabin-Karp string matching algorithm implementation.
 *
 * Reads a text and a pattern from standard input and prints all starting
 * indices where the pattern is found in the text.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    private static final int CHAR_SET_SIZE = 256; // Number of possible characters (extended ASCII)
    private static final int DEFAULT_PRIME = 101; // Default prime number for hashing

    private static Scanner scanner = null;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        System.out.println("Enter String");
        String text = scanner.nextLine();

        System.out.println("Enter pattern");
        String pattern = scanner.nextLine();

        rabinKarpSearch(text, pattern, DEFAULT_PRIME);
    }

    /**
     * Performs Rabin-Karp pattern searching.
     *
     * @param text   the text in which to search
     * @param pattern the pattern to search for
     * @param prime   a prime number used for hashing
     */
    private static void rabinKarpSearch(String text, String pattern, int prime) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int patternHash = 0; // Hash value for pattern
        int textHash = 0;    // Hash value for current text window
        int hashMultiplier = 1; // The value of CHAR_SET_SIZE^(patternLength-1) % prime

        // Precompute hashMultiplier = (CHAR_SET_SIZE^(patternLength-1)) % prime
        hashMultiplier = (int) Math.pow(CHAR_SET_SIZE, patternLength - 1) % prime;

        // Calculate initial hash values for pattern and first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (CHAR_SET_SIZE * patternHash + pattern.charAt(i)) % prime;
            textHash = (CHAR_SET_SIZE * textHash + text.charAt(i)) % prime;
        }

        // Slide the pattern over text one character at a time
        for (int i = 0; i <= textLength - patternLength; i++) {
            // If hash values match, check characters one by one to confirm
            if (patternHash == textHash) {
                int j;
                for (j = 0; j < patternLength; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }

                // If all characters match, pattern is found at index i
                if (j == patternLength) {
                    System.out.println("Pattern found at index " + i);
                }
            }

            // Compute hash for next window of text:
            // Remove leading character and add trailing character
            if (i < textLength - patternLength) {
                textHash = (CHAR_SET_SIZE * (textHash - text.charAt(i) * hashMultiplier)
                        + text.charAt(i + patternLength)) % prime;

                // We might get a negative value of textHash, convert it to positive
                if (textHash < 0) {
                    textHash += prime;
                }
            }
        }
    }
}