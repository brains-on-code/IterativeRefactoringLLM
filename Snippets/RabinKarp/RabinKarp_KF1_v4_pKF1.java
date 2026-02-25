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

            searchPattern(text, pattern, DEFAULT_PRIME);
        }
    }

    private static void searchPattern(String text, String pattern, int prime) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int patternHash = 0;
        int windowHash = 0;
        int highestBasePower = 1;

        highestBasePower = (int) Math.pow(CHAR_SET_SIZE, patternLength - 1) % prime;

        for (int index = 0; index < patternLength; index++) {
            patternHash = (CHAR_SET_SIZE * patternHash + pattern.charAt(index)) % prime;
            windowHash = (CHAR_SET_SIZE * windowHash + text.charAt(index)) % prime;
        }

        for (int textIndex = 0; textIndex <= textLength - patternLength; textIndex++) {
            if (patternHash == windowHash) {
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
                windowHash =
                    (CHAR_SET_SIZE * (windowHash - text.charAt(textIndex) * highestBasePower)
                            + text.charAt(textIndex + patternLength))
                        % prime;

                if (windowHash < 0) {
                    windowHash += prime;
                }
            }
        }
    }
}