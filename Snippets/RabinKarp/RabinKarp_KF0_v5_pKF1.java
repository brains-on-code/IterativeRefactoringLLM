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

    private RabinKarp() {
    }

    public static void main(String[] args) {
        try (Scanner inputScanner = new Scanner(System.in)) {
            System.out.println("Enter text:");
            String text = inputScanner.nextLine();

            System.out.println("Enter pattern:");
            String pattern = inputScanner.nextLine();

            int primeModulus = 101;
            searchPattern(text, pattern, primeModulus);
        }
    }

    private static void searchPattern(String text, String pattern, int primeModulus) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int rollingTextHash = 0;
        int patternHash = 0;
        int highestBasePowerMod = (int) Math.pow(ALPHABET_SIZE, patternLength - 1) % primeModulus;

        for (int index = 0; index < patternLength; index++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(index)) % primeModulus;
            rollingTextHash = (ALPHABET_SIZE * rollingTextHash + text.charAt(index)) % primeModulus;
        }

        for (int textStartIndex = 0; textStartIndex <= textLength - patternLength; textStartIndex++) {

            if (patternHash == rollingTextHash) {
                int patternIndex;
                for (patternIndex = 0; patternIndex < patternLength; patternIndex++) {
                    if (text.charAt(textStartIndex + patternIndex) != pattern.charAt(patternIndex)) {
                        break;
                    }
                }

                if (patternIndex == patternLength) {
                    System.out.println("Pattern found at index " + textStartIndex);
                }
            }

            if (textStartIndex < textLength - patternLength) {
                rollingTextHash =
                        (ALPHABET_SIZE * (rollingTextHash - text.charAt(textStartIndex) * highestBasePowerMod)
                                + text.charAt(textStartIndex + patternLength))
                                % primeModulus;

                if (rollingTextHash < 0) {
                    rollingTextHash += primeModulus;
                }
            }
        }
    }
}