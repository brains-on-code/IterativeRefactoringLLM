package com.thealgorithms.strings;

import java.util.Scanner;

/**
 * Rabin-Karp string search implementation.
 */
public final class RabinKarpSearch {

    private RabinKarpSearch() {
    }

    private static Scanner scanner = null;
    private static final int CHAR_SET_SIZE = 256;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Enter String");
        String text = scanner.nextLine();
        System.out.println("Enter pattern");
        String pattern = scanner.nextLine();

        int prime = 101;
        searchPattern(text, pattern, prime);
    }

    private static void searchPattern(String text, String pattern, int prime) {
        int patternLength = pattern.length();
        int textLength = text.length();
        int textHash = 0;
        int patternHash = 0;
        int highestPower = 1;
        int i;
        int j;

        highestPower = (int) Math.pow(CHAR_SET_SIZE, patternLength - 1) % prime;

        for (i = 0; i < patternLength; i++) {
            patternHash = (CHAR_SET_SIZE * patternHash + pattern.charAt(i)) % prime;
            textHash = (CHAR_SET_SIZE * textHash + text.charAt(i)) % prime;
        }

        for (i = 0; i <= textLength - patternLength; i++) {
            if (patternHash == textHash) {
                for (j = 0; j < patternLength; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }

                if (j == patternLength) {
                    System.out.println("Pattern found at index " + i);
                }
            }

            if (i < textLength - patternLength) {
                textHash =
                    (CHAR_SET_SIZE * (textHash - text.charAt(i) * highestPower)
                            + text.charAt(i + patternLength))
                        % prime;

                if (textHash < 0) {
                    textHash = textHash + prime;
                }
            }
        }
    }
}