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
        ensureNonNegative(n);

        if (n <= 1) {
            return 1L;
        }

        long[] catalan = createCatalanArrayWithBaseCases(n);

        for (int i = 2; i <= n; i++) {
            catalan[i] = computeCatalanValue(catalan, i);
        }

        return catalan[n];
    }

    private static long[] createCatalanArrayWithBaseCases(int n) {
        long[] catalan = new long[n + 1];
        catalan[0] = 1L;
        catalan[1] = 1L;
        return catalan;
    }

    private static void ensureNonNegative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
    }

    private static long computeCatalanValue(long[] catalan, int n) {
        long result = 0L;
        for (int leftTreeSize = 0; leftTreeSize < n; leftTreeSize++) {
            int rightTreeSize = n - leftTreeSize - 1;
            result += catalan[leftTreeSize] * catalan[rightTreeSize];
        }
        return result;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter n to find the nth Catalan number (n <= 50): ");
            int n = scanner.nextInt();
            System.out.println(n + "th Catalan number is " + computeCatalan(n));
        }
    }
}