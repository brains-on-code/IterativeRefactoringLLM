package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

/**
 * Implementation of finding the nth Catalan number using dynamic programming.
 *
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

    private static final int MAX_N = 50;

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
        validateInput(n);

        if (n <= 1) {
            return 1L;
        }

        long[] catalan = new long[n + 1];
        catalan[0] = 1L;
        catalan[1] = 1L;

        for (int i = 2; i <= n; i++) {
            catalan[i] = computeCatalanAtIndex(i, catalan);
        }

        return catalan[n];
    }

    private static void validateInput(int n) {
        if (n < 0 || n > MAX_N) {
            throw new IllegalArgumentException(
                String.format("n must be between 0 and %d (inclusive)", MAX_N)
            );
        }
    }

    private static long computeCatalanAtIndex(int index, long[] catalan) {
        long result = 0L;
        for (int leftIndex = 0; leftIndex < index; leftIndex++) {
            int rightIndex = index - 1 - leftIndex;
            result += catalan[leftIndex] * catalan[rightIndex];
        }
        return result;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter n to find the nth Catalan number (0 ≤ n ≤ 50): ");
            int n = scanner.nextInt();
            long result = findNthCatalan(n);
            System.out.println(n + "th Catalan number is " + result);
        }
    }
}