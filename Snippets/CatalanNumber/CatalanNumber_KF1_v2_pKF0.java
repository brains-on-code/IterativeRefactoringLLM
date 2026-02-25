package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

/**
 * Utility class for computing Catalan numbers using dynamic programming.
 */
public final class CatalanNumbers {

    private CatalanNumbers() {
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
     * @throws IllegalArgumentException if n is negative
     */
    static long computeCatalan(int n) {
        validateInput(n);

        if (n <= 1) {
            return 1L;
        }

        long[] catalan = new long[n + 1];
        initializeBaseCases(catalan);

        for (int i = 2; i <= n; i++) {
            catalan[i] = computeCatalanAtIndex(catalan, i);
        }

        return catalan[n];
    }

    private static void validateInput(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
    }

    private static void initializeBaseCases(long[] catalan) {
        catalan[0] = 1L;
        catalan[1] = 1L;
    }

    private static long computeCatalanAtIndex(long[] catalan, int index) {
        long value = 0L;
        for (int j = 0; j < index; j++) {
            value += catalan[j] * catalan[index - j - 1];
        }
        return value;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the number n to find nth Catalan number (n <= 50):");
            int n = scanner.nextInt();
            System.out.println(n + "th Catalan number is " + computeCatalan(n));
        }
    }
}