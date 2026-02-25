package com.thealgorithms.strings;

import java.util.Scanner;

public final class RabinKarp {

    private RabinKarp() {
    }

    private static final int ALPHABET_SIZE = 256;
    private static final int DEFAULT_PRIME_MODULUS = 101;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter text:");
            String text = scanner.nextLine();

            System.out.println("Enter pattern:");
            String pattern = scanner.nextLine();

            searchPattern(text, pattern, DEFAULT_PRIME_MODULUS);
        }
    }

    private static void searchPattern(String text, String pattern, int primeModulus) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int currentTextHash = 0;
        int patternHash = 0;
        int highestBasePowerMod = 1;

        highestBasePowerMod = (int) Math.pow(ALPHABET_SIZE, patternLength - 1) % primeModulus;

        for (int i = 0; i < patternLength; i++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(i)) % primeModulus;
            currentTextHash = (ALPHABET_SIZE * currentTextHash + text.charAt(i)) % primeModulus;
        }

        for (int textIndex = 0; textIndex <= textLength - patternLength; textIndex++) {
            if (patternHash == currentTextHash) {
                int patternIndex;
                for (patternIndex = 0; patternIndex < patternLength; patternIndex++) {
                    if (text.charAt(textIndex + patternIndex) != pattern.charAt(patternIndex)) {
                        break;
                    }
                }

                if (patternIndex == patternLength) {
                    System.out.println("Pattern found at index " + textIndex);
                }
            }

            if (textIndex < textLength - patternLength) {
                currentTextHash =
                    (ALPHABET_SIZE * (currentTextHash - text.charAt(textIndex) * highestBasePowerMod)
                            + text.charAt(textIndex + patternLength))
                        % primeModulus;

                if (currentTextHash < 0) {
                    currentTextHash += primeModulus;
                }
            }
        }
    }
}