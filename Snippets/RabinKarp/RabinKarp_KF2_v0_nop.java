package com.thealgorithms.strings;

import java.util.Scanner;


public final class RabinKarp {
    private RabinKarp() {
    }

    public static Scanner scanner = null;
    public static final int ALPHABET_SIZE = 256;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Enter String");
        String text = scanner.nextLine();
        System.out.println("Enter pattern");
        String pattern = scanner.nextLine();

        int q = 101;
        searchPat(text, pattern, q);
    }

    private static void searchPat(String text, String pattern, int q) {
        int m = pattern.length();
        int n = text.length();
        int t = 0;
        int p = 0;
        int h = 1;
        int j = 0;
        int i = 0;

        h = (int) Math.pow(ALPHABET_SIZE, m - 1) % q;

        for (i = 0; i < m; i++) {
            p = (ALPHABET_SIZE * p + pattern.charAt(i)) % q;
            t = (ALPHABET_SIZE * t + text.charAt(i)) % q;
        }

        for (i = 0; i <= n - m; i++) {
            if (p == t) {
                for (j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }

                if (j == m) {
                    System.out.println("Pattern found at index " + i);
                }
            }

            if (i < n - m) {
                t = (ALPHABET_SIZE * (t - text.charAt(i) * h) + text.charAt(i + m)) % q;

                if (t < 0) {
                    t = (t + q);
                }
            }
        }
    }
}
