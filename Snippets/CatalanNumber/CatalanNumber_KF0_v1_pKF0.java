package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

/**
 * Implementation of finding the nth Catalan number using dynamic programming.
 * <p>
 * Reference: <a href="https://en.wikipedia.org/wiki/Catalan_number">Wikipedia</a>
 * </p>
 *
 * <p>
 * Time Complexity: O(n^2) <br>
 * Space Complexity: O(n)
 * </p>
 *
 * <p>
 * Note: n should be less than or equal to 50, since the 50th Catalan number is
 * 6,533,841,209,031,609,592. For n &gt; 50, use {@link java.math.BigInteger}
 * instead of {@code long}.
 * </p>
 */
public final class CatalanNumber {

    private CatalanNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the nth Catalan number using dynamic programming.
     *
     * @param n index of the Catalan number to compute (0 ≤ n ≤ 50)
     * @return the nth Catalan number
     * @throws IllegalArgumentException if n is negative or greater than 50
     */
    static long findNthCatalan(int n) {
        if (n < 0 || n > 50) {
            throw new IllegalArgumentException("n must be between 0 and 50 (inclusive)");
        }

        if (n == 0 || n == 1) {
            return 1L;
        }

        long[] catalan = new long[n + 1];
        catalan[0] = 1L;
        catalan[1] = 1L;

        // Catalan recurrence:
        // C0 = 1
        // Cn = sum_{i=0 to n-1} (Ci * Cn-1-i), for n > 0
        for (int i = 2; i <= n; i++) {
            long value = 0L;
            for (int j = 0; j < i; j++) {
                value += catalan[j] * catalan[i - 1 - j];
            }
            catalan[i] = value;
        }

        return catalan[n];
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter n to find the nth Catalan number (0 ≤ n ≤ 50):");
            int n = scanner.nextInt();
            long result = findNthCatalan(n);
            System.out.println(n + "th Catalan number is " + result);
        }
    }
}