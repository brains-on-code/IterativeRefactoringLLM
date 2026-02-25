package com.thealgorithms.strings;

import java.util.Scanner;

public final class RabinKarp {

    private static final int ALPHABET_SIZE = 256;
    private static final int DEFAULT_PRIME = 101;

    private RabinKarp() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter text:");
            String text = scanner.nextLine();

            System.out.println("Enter pattern:");
            String pattern = scanner.nextLine();

            searchPattern(text, pattern, DEFAULT_PRIME);
        }
    }

    private static void searchPattern(String text, String pattern, int prime) {
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

        // Initial hash values for pattern and first window of text
        for (int i = 0; i < patternLength; i++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(i)) % prime;
            textHash = (ALPHABET_SIZE * textHash + text.charAt(i)) % prime;
        }

        for (int i = 0; i <= textLength - patternLength; i++) {
            if (patternHash == textHash && matchesAt(text, pattern, i)) {
                System.out.println("Pattern found at index " + i);
            }

            if (i < textLength - patternLength) {
                textHash = recomputeHash(
                        textHash,
                        text.charAt(i),
                        text.charAt(i + patternLength),
                        hashMultiplier,
                        prime
                );
            }
        }
    }

    private static int recomputeHash(
            int currentHash,
            char outgoingChar,
            char incomingChar,
            int hashMultiplier,
            int prime
    ) {
        int newHash = (ALPHABET_SIZE * (currentHash - outgoingChar * hashMultiplier) + incomingChar) % prime;
        if (newHash < 0) {
            newHash += prime;
        }
        return newHash;
    }

    private static boolean matchesAt(String text, String pattern, int startIndex) {
        for (int j = 0; j < pattern.length(); j++) {
            if (text.charAt(startIndex + j) != pattern.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}