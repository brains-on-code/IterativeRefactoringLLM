package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for computing Fibonacci numbers using different approaches.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /** Memoization cache for the recursive Fibonacci implementation. */
    private static final Map<Integer, Integer> MEMOIZED_FIB = new HashMap<>();

    /**
     * Computes the n-th Fibonacci number using a top-down recursive
     * dynamic programming approach with memoization.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static int method1(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }
        if (MEMOIZED_FIB.containsKey(n)) {
            return MEMOIZED_FIB.get(n);
        }

        int result;
        if (n <= 1) {
            result = n;
        } else {
            result = method1(n - 1) + method1(n - 2);
            MEMOIZED_FIB.put(n, result);
        }
        return result;
    }

    /**
     * Computes the n-th Fibonacci number using a bottom-up
     * dynamic programming approach with a map as the table.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static int method2(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }

        Map<Integer, Integer> dp = new HashMap<>();

        for (int i = 0; i <= n; i++) {
            int value;
            if (i <= 1) {
                value = i;
            } else {
                value = dp.get(i - 1) + dp.get(i - 2);
            }
            dp.put(i, value);
        }

        return dp.get(n);
    }

    /**
     * Computes the n-th Fibonacci number using an iterative approach
     * with constant space.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static int method3(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }
        if (n == 0) {
            return 0;
        }

        int prev = 0;
        int curr = 1;

        for (int i = 2; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }

        return curr;
    }

    /**
     * Computes the n-th Fibonacci number using Binet's formula
     * (closed-form expression).
     *
     * Note: This method uses floating-point arithmetic and may be
     * inaccurate for large values of {@code n}.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number (rounded down to int)
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static int method4(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }

        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2;

        return (int) ((Math.pow(phi, n) - Math.pow(-phi, -n)) / sqrt5);
    }
}