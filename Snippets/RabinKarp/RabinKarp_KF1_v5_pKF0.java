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

        if (!isSearchFeasible(patternLength, textLength)) {
            System.out.println("No valid match possible.");
            return;
        }

        int hashMultiplier = computeHashMultiplier(patternLength, prime);
        int patternHash = computeInitialHash(pattern, patternLength, prime);
        int textHash = computeInitialHash(text, patternLength, prime);

        int lastStartIndex = textLength - patternLength;
        for (int startIndex = 0; startIndex <= lastStartIndex; startIndex++) {
            if (patternHash == textHash && matchesAt(text, pattern, startIndex)) {
                System.out.println("Pattern found at index " + startIndex);
            }

            if (startIndex < lastStartIndex) {
                textHash = recalculateHash(
                    text,
                    startIndex,
                    patternLength,
                    textHash,
                    hashMultiplier,
                    prime
                );
            }
        }
    }

    private static boolean isSearchFeasible(int patternLength, int textLength) {
        return patternLength > 0 && textLength > 0 && patternLength <= textLength;
    }

    private static int computeHashMultiplier(int patternLength, int prime) {
        int multiplier = 1;
        int limit = patternLength - 1;
        for (int i = 0; i < limit; i++) {
            multiplier = (multiplier * CHAR_SET_SIZE) % prime;
        }
        return multiplier;
    }

    private static int computeInitialHash(String input, int length, int prime) {
        int hash = 0;
        for (int i = 0; i < length; i++) {
            hash = (CHAR_SET_SIZE * hash + input.charAt(i)) % prime;
        }
        return hash;
    }

    private static boolean matchesAt(String text, String pattern, int startIndex) {
        int patternLength = pattern.length();
        for (int offset = 0; offset < patternLength; offset++) {
            if (text.charAt(startIndex + offset) != pattern.charAt(offset)) {
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
        int leadingChar = text.charAt(currentIndex);
        int trailingChar = text.charAt(currentIndex + patternLength);

        int newHash = (CHAR_SET_SIZE * (currentHash - leadingChar * hashMultiplier) + trailingChar) % prime;

        if (newHash < 0) {
            newHash += prime;
        }
        return newHash;
    }
}