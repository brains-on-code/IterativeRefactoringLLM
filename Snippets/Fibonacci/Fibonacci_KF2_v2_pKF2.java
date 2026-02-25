package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class providing multiple implementations for computing Fibonacci numbers.
 */
public final class Fibonacci {

    private Fibonacci() {
        // Utility class; prevent instantiation.
    }

    /** Cache for memoized Fibonacci computation. */
    private static final Map<Integer, Integer> CACHE = new HashMap<>();

    /**
     * Computes the n-th Fibonacci number using top-down memoization.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibMemo(int n) {
        validateNonNegative(n);

        Integer cached = CACHE.get(n);
        if (cached != null) {
            return cached;
        }

        int result = (n <= 1) ? n : fibMemo(n - 1) + fibMemo(n - 2);
        CACHE.put(n, result);
        return result;
    }

    /**
     * Computes the n-th Fibonacci number using bottom-up dynamic programming.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibBotUp(int n) {
        validateNonNegative(n);

        Map<Integer, Integer> fib = new HashMap<>();

        for (int i = 0; i <= n; i++) {
            int value = (i <= 1) ? i : fib.get(i - 1) + fib.get(i - 2);
            fib.put(i, value);
        }

        return fib.get(n);
    }

    /**
     * Computes the n-th Fibonacci number using an iterative approach
     * with constant space.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibOptimized(int n) {
        validateNonNegative(n);

        if (n == 0) {
            return 0;
        }

        int prev = 0;
        int current = 1;

        for (int i = 2; i <= n; i++) {
            int next = prev + current;
            prev = current;
            current = next;
        }

        return current;
    }

    /**
     * Computes the n-th Fibonacci number using Binet's formula.
     * Uses floating-point arithmetic and may be inaccurate for large n.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number (approximate for large n)
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibBinet(int n) {
        validateNonNegative(n);

        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2;

        return (int) ((Math.pow(phi, n) - Math.pow(-phi, -n)) / sqrt5);
    }

    /**
     * Ensures that the given integer is non-negative.
     *
     * @param n value to validate
     * @throws IllegalArgumentException if n is negative
     */
    private static void validateNonNegative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }
    }
}