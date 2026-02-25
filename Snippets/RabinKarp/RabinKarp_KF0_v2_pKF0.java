package com.thealgorithms.strings;

import java.util.Scanner;

/**
 * An implementation of the Rabin-Karp string matching algorithm.
 * Program will simply end if there is no match.
 *
 * @author Prateek Kumar Oraon (https://github.com/prateekKrOraon)
 */
public final class RabinKarp {

    private static final int ALPHABET_SIZE = 256;
    private static final int DEFAULT_PRIME = 101;

    private RabinKarp() {
        // Utility class
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter String");
            String text = scanner.nextLine();

            System.out.println("Enter pattern");
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
        int hashMultiplier = computeHashMultiplier(patternLength, prime);

        patternHash = computeInitialHash(pattern, patternLength, prime);
        textHash = computeInitialHash(text, patternLength, prime);

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

    private static int computeHashMultiplier(int patternLength, int prime) {
        int hashMultiplier = 1;
        for (int i = 0; i < patternLength - 1; i++) {
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
        int oldHash,
        char outgoingChar,
        char incomingChar,
        int hashMultiplier,
        int prime
    ) {
        int newHash = (ALPHABET_SIZE * (oldHash - outgoingChar * hashMultiplier) + incomingChar) % prime;
        if (newHash < 0) {
            newHash += prime;
        }
        return newHash;
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
}