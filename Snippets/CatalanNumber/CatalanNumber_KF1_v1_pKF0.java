package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

/**
 * Utility class for computing Catalan numbers using dynamic programming.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Computes the nth Catalan number using dynamic programming.
     *
     * C(0) = 1, C(1) = 1
     * C(n) = Σ C(i) * C(n - i - 1) for i = 0..n-1
     *
     * @param n index of the Catalan number to compute (0-based)
     * @return the nth Catalan number
     */
    static long computeCatalan(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
        if (n == 0 || n == 1) {
            return 1L;
        }

        long[] catalan = new long[n + 1];
        catalan[0] = 1L;
        catalan[1] = 1L;

        for (int i = 2; i <= n; i++) {
            catalan[i] = 0L;
            for (int j = 0; j < i; j++) {
                catalan[i] += catalan[j] * catalan[i - j - 1];
            }
        }

        return catalan[n];
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the number n to find nth Catalan number (n <= 50):");
            int n = scanner.nextInt();
            System.out.println(n + "th Catalan number is " + computeCatalan(n));
        }
    }
}