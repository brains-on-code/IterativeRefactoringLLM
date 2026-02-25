package com.thealgorithms.strings;

import java.util.Scanner;

/**
 * An implementation of the Rabin-Karp string matching algorithm.
 * Program will simply end if there is no match.
 *
 * @author Prateek Kumar Oraon (https://github.com/prateekKrOraon)
 */
public final class RabinKarp {

    private RabinKarp() {
    }

    public static final int ALPHABET_SIZE = 256;
    public static Scanner scanner = null;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        System.out.println("Enter text:");
        String text = scanner.nextLine();

        System.out.println("Enter pattern:");
        String pattern = scanner.nextLine();

        int primeModulus = 101;
        searchPattern(text, pattern, primeModulus);
    }

    private static void searchPattern(String text, String pattern, int primeModulus) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int textHash = 0;
        int patternHash = 0;
        int hashMultiplier = 1;

        // Precompute (ALPHABET_SIZE^(patternLength-1)) % primeModulus
        hashMultiplier = (int) Math.pow(ALPHABET_SIZE, patternLength - 1) % primeModulus;

        // Initial hash values for pattern and first window of text
        for (int index = 0; index < patternLength; index++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(index)) % primeModulus;
            textHash = (ALPHABET_SIZE * textHash + text.charAt(index)) % primeModulus;
        }

        // Slide the pattern over text one by one
        for (int textIndex = 0; textIndex <= textLength - patternLength; textIndex++) {

            // If hash values match, then check characters one by one
            if (patternHash == textHash) {
                int patternIndex;
                for (patternIndex = 0; patternIndex < patternLength; patternIndex++) {
                    if (text.charAt(textIndex + patternIndex) != pattern.charAt(patternIndex)) {
                        break;
                    }
                }

                // If all characters match, pattern is found
                if (patternIndex == patternLength) {
                    System.out.println("Pattern found at index " + textIndex);
                }
            }

            // Calculate hash value for next window of text
            if (textIndex < textLength - patternLength) {
                textHash = (ALPHABET_SIZE * (textHash - text.charAt(textIndex) * hashMultiplier)
                        + text.charAt(textIndex + patternLength)) % primeModulus;

                // Convert negative hash value to positive
                if (textHash < 0) {
                    textHash += primeModulus;
                }
            }
        }
    }
}