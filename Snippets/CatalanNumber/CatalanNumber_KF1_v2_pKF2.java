package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

/**
 * Utility class for computing Catalan numbers using dynamic programming.
 *
 * The nth Catalan number counts many combinatorial structures, such as:
 * - Number of valid sequences of n pairs of parentheses
 * - Number of full binary trees with n + 1 leaves
 * - Number of ways to triangulate a convex polygon with n + 2 sides
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Computes the nth Catalan number using a bottom-up dynamic programming approach.
     *
     * Recurrence:
     *   C(0) = 1
     *   C(1) = 1
     *   C(n) = Σ (C(i) * C(n - i - 1)) for i = 0..n-1
     *
     * @param n index of the Catalan number to compute (0-based, typically n <= 50)
     * @return the nth Catalan number
     */
    static long method1(int n) {
        long[] catalan = new long[n + 1];

        catalan[0] = 1;
        catalan[1] = 1;

        for (int i = 2; i <= n; i++) {
            catalan[i] = 0;
            for (int j = 0; j < i; j++) {
                catalan[i] += catalan[j] * catalan[i - j - 1];
            }
        }

        return catalan[n];
    }

    /**
     * Reads an integer n from standard input and prints the nth Catalan number.
     *
     * @param args command-line arguments (not used)
     */
    public static void method2(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the number n to find nth Catalan number (n <= 50)");
            int n = scanner.nextInt();
            System.out.println(n + "th Catalan number is " + method1(n));
        }
    }
}