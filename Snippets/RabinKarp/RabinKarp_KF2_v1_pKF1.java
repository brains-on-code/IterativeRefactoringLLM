package com.thealgorithms.strings;

import java.util.Scanner;

public final class RabinKarp {

    private RabinKarp() {
    }

    public static final int ALPHABET_SIZE = 256;
    public static Scanner scanner = null;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Enter String");
        String text = scanner.nextLine();
        System.out.println("Enter pattern");
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

        hashMultiplier = (int) Math.pow(ALPHABET_SIZE, patternLength - 1) % primeModulus;

        for (int index = 0; index < patternLength; index++) {
            patternHash = (ALPHABET_SIZE * patternHash + pattern.charAt(index)) % primeModulus;
            textHash = (ALPHABET_SIZE * textHash + text.charAt(index)) % primeModulus;
        }

        for (int textIndex = 0; textIndex <= textLength - patternLength; textIndex++) {
            if (patternHash == textHash) {
                int matchIndex;
                for (matchIndex = 0; matchIndex < patternLength; matchIndex++) {
                    if (text.charAt(textIndex + matchIndex) != pattern.charAt(matchIndex)) {
                        break;
                    }
                }

                if (matchIndex == patternLength) {
                    System.out.println("Pattern found at index " + textIndex);
                }
            }

            if (textIndex < textLength - patternLength) {
                textHash =
                    (ALPHABET_SIZE * (textHash - text.charAt(textIndex) * hashMultiplier)
                            + text.charAt(textIndex + patternLength))
                        % primeModulus;

                if (textHash < 0) {
                    textHash += primeModulus;
                }
            }
        }
    }
}