package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

/**
 * Computes Catalan numbers using dynamic programming.
 *
 * The nth Catalan number counts many combinatorial structures, such as:
 * <ul>
 *   <li>Number of valid sequences of n pairs of parentheses</li>
 *   <li>Number of full binary trees with n + 1 leaves</li>
 *   <li>Number of ways to triangulate a convex polygon with n + 2 sides</li>
 * </ul>
 */
public final class CatalanNumbers {

    private CatalanNumbers() {
        // Prevent instantiation
    }

    /**
     * Computes the nth Catalan number using a bottom-up dynamic programming approach.
     *
     * Recurrence:
     * <pre>
     *   C(0) = 1
     *   C(1) = 1
     *   C(n) = Σ (C(i) * C(n - i - 1)) for i = 0..n-1
     * </pre>
     *
     * @param n index of the Catalan number to compute (0-based, typically n <= 50)
     * @return the nth Catalan number
     * @throws IllegalArgumentException if {@code n} is negative
     */
    static long computeCatalan(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }

        if (n <= 1) {
            return 1L;
        }

        long[] catalan = new long[n + 1];
        catalan[0] = 1L;
        catalan[1] = 1L;

        for (int i = 2; i <= n; i++) {
            long value = 0L;
            for (int j = 0; j < i; j++) {
                value += catalan[j] * catalan[i - j - 1];
            }
            catalan[i] = value;
        }

        return catalan[n];
    }

    /**
     * Reads an integer n from standard input and prints the nth Catalan number.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter n (n <= 50) to compute the nth Catalan number: ");
            int n = scanner.nextInt();
            System.out.println(n + "th Catalan number is " + computeCatalan(n));
        }
    }
}