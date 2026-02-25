package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

public final class CatalanNumber {

    private CatalanNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the nth Catalan number using dynamic programming.
     *
     * @param n index of the Catalan number to compute (0-based)
     * @return the nth Catalan number
     * @throws IllegalArgumentException if n is negative
     */
    static long findNthCatalan(int n) {
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
            catalan[i] = computeCatalanAtIndex(catalan, i);
        }

        return catalan[n];
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
            System.out.println("Enter n to find the nth Catalan number (n <= 50):");
            int n = scanner.nextInt();
            long result = findNthCatalan(n);
            System.out.println(n + "th Catalan number is " + result);
        }
    }
}