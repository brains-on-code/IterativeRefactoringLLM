package com.thealgorithms.dynamicprogramming;

import java.util.Scanner;

/**
 * Computes the nth Catalan number using dynamic programming.
 *
 * <p>Reference: <a href="https://en.wikipedia.org/wiki/Catalan_number">Wikipedia</a>
 *
 * <p>Time Complexity: O(n^2) <br>
 * Space Complexity: O(n)
 *
 * <p>Note: For n &gt; 50, the result exceeds the range of {@code long};
 * use {@code BigInteger} instead.
 */
public final class CatalanNumber {

    private CatalanNumber() {
        // Prevent instantiation
    }

    /**
     * Returns the nth Catalan number.
     *
     * <p>Uses the recurrence:
     * <pre>
     * C₀ = 1
     * Cₙ = Σ (Cᵢ * Cₙ₋₁₋ᵢ), for i = 0 to n - 1, n &gt; 0
     * </pre>
     *
     * @param n index of the Catalan number (0 ≤ n ≤ 50)
     * @return the nth Catalan number
     */
    static long findNthCatalan(int n) {
        long[] catalan = new long[n + 1];

        catalan[0] = 1;
        if (n >= 1) {
            catalan[1] = 1;
        }

        for (int i = 2; i <= n; i++) {
            long currentValue = 0;
            for (int j = 0; j < i; j++) {
                currentValue += catalan[j] * catalan[i - j - 1];
            }
            catalan[i] = currentValue;
        }

        return catalan[n];
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter n (0 ≤ n ≤ 50) to find the nth Catalan number:");
            int n = scanner.nextInt();
            System.out.println(n + "th Catalan number is " + findNthCatalan(n));
        }
    }
}