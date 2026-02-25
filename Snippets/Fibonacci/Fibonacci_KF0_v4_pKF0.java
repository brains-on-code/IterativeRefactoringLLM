package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for computing Fibonacci numbers using various approaches.
 *
 * <p>Provides memoized, bottom-up, space-optimized, and closed-form (Binet's
 * formula) implementations.</p>
 */
public final class Fibonacci {

    private static final Map<Integer, Integer> CACHE = new HashMap<>();

    private Fibonacci() {
        // Prevent instantiation
    }

    /**
     * Computes the nth Fibonacci number using memoization.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibMemo(int n) {
        validateNonNegative(n);

        if (n <= 1) {
            return n;
        }

        Integer cachedValue = CACHE.get(n);
        if (cachedValue != null) {
            return cachedValue;
        }

        int result = fibMemo(n - 1) + fibMemo(n - 2);
        CACHE.put(n, result);
        return result;
    }

    /**
     * Computes the nth Fibonacci number using a bottom-up dynamic programming approach.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibBotUp(int n) {
        validateNonNegative(n);

        if (n <= 1) {
            return n;
        }

        int[] fib = new int[n + 1];
        fib[0] = 0;
        fib[1] = 1;

        for (int i = 2; i <= n; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }

        return fib[n];
    }

    /**
     * Computes the nth Fibonacci number using an optimized bottom-up approach
     * with constant space complexity O(1).
     *
     * <p>This implementation avoids recursion and additional data structures,
     * resulting in O(n) time and O(1) space complexity.</p>
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibOptimized(int n) {
        validateNonNegative(n);

        if (n <= 1) {
            return n;
        }

        int previous = 0;
        int current = 1;

        for (int i = 2; i <= n; i++) {
            int next = previous + current;
            previous = current;
            current = next;
        }

        return current;
    }

    /**
     * Computes the nth Fibonacci number using Binet's formula in constant time O(1).
     *
     * <p>Binet's formula:
     * <pre>
     *   F(n) = (φ^n - (−φ)^(−n)) / √5
     *   where φ = (1 + √5) / 2
     * </pre>
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibBinet(int n) {
        validateNonNegative(n);

        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2;
        double numerator = Math.pow(phi, n) - Math.pow(-phi, -n);

        return (int) (numerator / sqrt5);
    }

    private static void validateNonNegative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }
    }
}