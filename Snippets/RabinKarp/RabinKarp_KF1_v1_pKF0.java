package com.thealgorithms.strings;

import java.util.Scanner;

/**
 * Rabin-Karp string search implementation.
 */
public final class RabinKarpSearch {

    private RabinKarpSearch() {
        // Utility class
    }

    private static final int CHAR_SET_SIZE = 256;
    private static final int DEFAULT_PRIME = 101;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter text:");
            String text = scanner.nextLine();

            System.out.println("Enter pattern:");
            String pattern = scanner.nextLine();

            search(text, pattern, DEFAULT_PRIME);
        }
    }

    private static void search(String text, String pattern, int prime) {
        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            System.out.println("No valid match possible.");
            return;
        }

        int patternHash = 0;
        int textHash = 0;
        int hashMultiplier = 1;

        // The value of CHAR_SET_SIZE^(patternLength-1) % prime
        hashMultiplier = (int) Math.pow(CHAR_SET_SIZE, patternLength - 1) % prime;

        // Calculate initial hash values for pattern and first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (CHAR_SET_SIZE * patternHash + pattern.charAt(i)) % prime;
            textHash = (CHAR_SET_SIZE * textHash + text.charAt(i)) % prime;
        }

        // Slide the pattern over text one by one
        for (int i = 0; i <= textLength - patternLength; i++) {
            // Check the hash values of current window of text and pattern
            if (patternHash == textHash) {
                // If the hash values match, then only check characters one by one
                int j;
                for (j = 0; j < patternLength; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }

                // If patternHash == textHash and pattern[0...patternLength-1] = text[i...i+patternLength-1]
                if (j == patternLength) {
                    System.out.println("Pattern found at index " + i);
                }
            }

            // Calculate hash value for next window of text:
            // Remove leading character, add trailing character
            if (i < textLength - patternLength) {
                textHash = (CHAR_SET_SIZE * (textHash - text.charAt(i) * hashMultiplier)
                        + text.charAt(i + patternLength)) % prime;

                // We might get negative value of textHash, converting it to positive
                if (textHash < 0) {
                    textHash += prime;
                }
            }
        }
    }
}