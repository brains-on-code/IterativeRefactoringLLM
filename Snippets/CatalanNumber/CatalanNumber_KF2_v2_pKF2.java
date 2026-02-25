package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

/**
 * Utility class for computing Catalan numbers using dynamic programming.
 *
 * <p>The nth Catalan number C(n) is defined as:
 * <pre>
 *   C(0) = 1
 *   C(n) = Σ (C(i) * C(n - i - 1)) for i = 0 to n - 1
 * </pre>
 */
public final class CatalanNumber {

    private CatalanNumber() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the nth Catalan number using a bottom-up dynamic programming approach.
     *
     * @param n index of the Catalan number to compute (0-based, typically n <= 50 to avoid overflow)
     * @return the nth Catalan number
     */
    static long findNthCatalan(int n) {
        long[] catalan = new long[n + 1];

        catalan[0] = 1;
        if (n >= 1) {
            catalan[1] = 1;
        }

        for (int i = 2; i <= n; i++) {
            long value = 0;
            for (int j = 0; j < i; j++) {
                value += catalan[j] * catalan[i - j - 1];
            }
            catalan[i] = value;
        }

        return catalan[n];
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter n to find the nth Catalan number (n <= 50): ");
            int n = scanner.nextInt();
            System.out.println(n + "th Catalan number is " + findNthCatalan(n));
        }
    }
}