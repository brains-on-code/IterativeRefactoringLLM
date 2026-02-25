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
        int currentWindowHash = 0;
        int highestBasePower = (int) Math.pow(CHAR_SET_SIZE, patternLength - 1) % prime;

        for (int i = 0; i < patternLength; i++) {
            patternHash = (CHAR_SET_SIZE * patternHash + pattern.charAt(i)) % prime;
            currentWindowHash = (CHAR_SET_SIZE * currentWindowHash + text.charAt(i)) % prime;
        }

        for (int textStartIndex = 0; textStartIndex <= textLength - patternLength; textStartIndex++) {
            if (patternHash == currentWindowHash) {
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
                currentWindowHash =
                    (CHAR_SET_SIZE * (currentWindowHash - text.charAt(textStartIndex) * highestBasePower)
                            + text.charAt(textStartIndex + patternLength))
                        % prime;

                if (currentWindowHash < 0) {
                    currentWindowHash += prime;
                }
            }
        }
    }
}