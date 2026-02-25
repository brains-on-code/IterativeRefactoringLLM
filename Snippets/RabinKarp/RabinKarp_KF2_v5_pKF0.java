package com.thealgorithms.strings;

import java.util.Scanner;

public final class RabinKarp {

    private static final int ALPHABET_SIZE = 256;
    private static final int DEFAULT_PRIME = 101;

    private RabinKarp() {
        // Utility class
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String text = readLine(scanner, "Enter text:");
            String pattern = readLine(scanner, "Enter pattern:");
            searchPattern(text, pattern, DEFAULT_PRIME);
        }
    }

    private static String readLine(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    private static void searchPattern(String text, String pattern, int prime) {
        int patternLength = pattern.length();
        int textLength = text.length();

        if (isInvalidInput(patternLength, textLength)) {
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
                textHash = recomputeHash(
                    textHash,
                    text.charAt(startIndex),
                    text.charAt(startIndex + patternLength),
                    hashMultiplier,
                    prime
                );
            }
        }
    }

    private static boolean isInvalidInput(int patternLength, int textLength) {
        return patternLength == 0 || textLength == 0 || patternLength > textLength;
    }

    private static int computeHashMultiplier(int patternLength, int prime) {
        int hashMultiplier = 1;
        int limit = patternLength - 1;
        for (int i = 0; i < limit; i++) {
            hashMultiplier = (hashMultiplier * ALPHABET_SIZE) % prime;
        }
        return hashMultiplier;
    }

    private static int computeInitialHash(String input, int length, int prime) {
        int hash = 0;
        for (int i = 0; i < length; i++) {
            hash = (ALPHABET_SIZE * hash + input.charAt(i)) % prime;
        }
        return hash;
    }

    private static int recomputeHash(
        int currentHash,
        char outgoingChar,
        char incomingChar,
        int hashMultiplier,
        int prime
    ) {
        int adjustedHash = currentHash - outgoingChar * hashMultiplier;
        int newHash = (ALPHABET_SIZE * adjustedHash + incomingChar) % prime;
        if (newHash < 0) {
            newHash += prime;
        }
        return newHash;
    }

    private static boolean matchesAt(String text, String pattern, int startIndex) {
        int patternLength = pattern.length();
        int endIndex = startIndex + patternLength;
        for (int textIndex = startIndex, patternIndex = 0;
             textIndex < endIndex;
             textIndex++, patternIndex++) {

            if (text.charAt(textIndex) != pattern.charAt(patternIndex)) {
                return false;
            }
        }
        return true;
    }
}