package com.thealgorithms.strings;

import java.util.Scanner;

/**
 * Rabin-Karp string search implementation.
 */
public final class RabinKarpSearch {

    private static final int CHAR_SET_SIZE = 256;
    private static final int DEFAULT_PRIME = 101;

    private RabinKarpSearch() {
        // Utility class
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

    private static void search(String text, String pattern, int prime) {
        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            System.out.println("No valid match possible.");
            return;
        }

        int patternHash = 0;
        int textHash = 0;

        // The value of CHAR_SET_SIZE^(patternLength-1) % prime
        int hashMultiplier = (int) Math.pow(CHAR_SET_SIZE, patternLength - 1) % prime;

        // Calculate initial hash values for pattern and first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (CHAR_SET_SIZE * patternHash + pattern.charAt(i)) % prime;
            textHash = (CHAR_SET_SIZE * textHash + text.charAt(i)) % prime;
        }

        // Slide the pattern over text one by one
        for (int i = 0; i <= textLength - patternLength; i++) {
            // Check the hash values of current window of text and pattern
            if (patternHash == textHash && matchesAt(text, pattern, i)) {
                System.out.println("Pattern found at index " + i);
            }

            // Calculate hash value for next window of text:
            // Remove leading character, add trailing character
            if (i < textLength - patternLength) {
                textHash = recalculateHash(text, i, patternLength, textHash, hashMultiplier, prime);
            }
        }
    }

    private static boolean matchesAt(String text, String pattern, int startIndex) {
        int patternLength = pattern.length();
        for (int j = 0; j < patternLength; j++) {
            if (text.charAt(startIndex + j) != pattern.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    private static int recalculateHash(
            String text,
            int currentIndex,
            int patternLength,
            int currentHash,
            int hashMultiplier,
            int prime
    ) {
        int newHash = (CHAR_SET_SIZE * (currentHash - text.charAt(currentIndex) * hashMultiplier)
                + text.charAt(currentIndex + patternLength)) % prime;

        if (newHash < 0) {
            newHash += prime;
        }
        return newHash;
    }
}