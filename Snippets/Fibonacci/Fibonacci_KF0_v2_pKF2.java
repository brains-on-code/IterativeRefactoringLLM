package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for computing Fibonacci numbers using various approaches.
 */
public final class Fibonacci {

    private Fibonacci() {
        // Utility class; prevent instantiation
    }

    /** Cache used by {@link #fibMemo(int)} for memoized Fibonacci computation. */
    private static final Map<Integer, Integer> CACHE = new HashMap<>();

    /**
     * Computes the n-th Fibonacci number using memoization (top-down dynamic programming).
     *
     * <p>Time complexity: O(n) — each value from 0..n is computed once.<br>
     * Space complexity: O(n) — recursion stack plus cache.</p>
     *
     * @param n index of the Fibonacci number (0-based, must be non-negative)
     * @return the n-th Fibonacci number
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static int fibMemo(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }

        Integer cached = CACHE.get(n);
        if (cached != null) {
            return cached;
        }

        int value = (n <= 1) ? n : fibMemo(n - 1) + fibMemo(n - 2);
        CACHE.put(n, value);
        return value;
    }

    /**
     * Computes the n-th Fibonacci number using a bottom-up dynamic programming approach.
     *
     * <p>Time complexity: O(n).<br>
     * Space complexity: O(n) for the map.</p>
     *
     * @param n index of the Fibonacci number (0-based, must be non-negative)
     * @return the n-th Fibonacci number
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static int fibBotUp(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }

        Map<Integer, Integer> fib = new HashMap<>();

        for (int i = 0; i <= n; i++) {
            int value = (i <= 1) ? i : fib.get(i - 1) + fib.get(i - 2);
            fib.put(i, value);
        }

        return fib.get(n);
    }

    /**
     * Computes the n-th Fibonacci number using an optimized bottom-up approach
     * with constant extra space.
     *
     * <p>This implementation does not use recursion or additional data structures.</p>
     *
     * <p>Time complexity: O(n).<br>
     * Space complexity: O(1).</p>
     *
     * @param n index of the Fibonacci number (0-based, must be non-negative)
     * @return the n-th Fibonacci number
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static int fibOptimized(int n) {
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
     * Computes the n-th Fibonacci number using Binet's formula.
     *
     * <p>Binet's formula uses the golden ratio Φ to compute the n-th term in constant time:</p>
     *
     * <pre>
     * Φ = (1 + √5) / 2
     * F(n) = (Φⁿ − (−Φ)⁻ⁿ) / √5
     * </pre>
     *
     * <p>Note: Due to floating-point precision, this method may produce incorrect
     * results for larger values of {@code n}.</p>
     *
     * <p>Time complexity: O(1).<br>
     * Space complexity: O(1).</p>
     *
     * @param n index of the Fibonacci number (0-based, must be non-negative)
     * @return the n-th Fibonacci number
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static int fibBinet(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }

        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2;

        return (int) ((Math.pow(phi, n) - Math.pow(-phi, -n)) / sqrt5);
    }
}