package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for computing Fibonacci numbers using different approaches.
 */
public final class FibonacciUtils {

    private FibonacciUtils() {
        // Prevent instantiation
    }

    /** Memoization cache for the recursive Fibonacci implementation. */
    private static final Map<Integer, Integer> MEMOIZED_FIB = new HashMap<>();

    /**
     * Computes the n-th Fibonacci number using recursion with memoization.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibonacciRecursiveMemoized(int n) {
        validateNonNegative(n);

        if (MEMOIZED_FIB.containsKey(n)) {
            return MEMOIZED_FIB.get(n);
        }

        int result = (n <= 1)
            ? n
            : fibonacciRecursiveMemoized(n - 1) + fibonacciRecursiveMemoized(n - 2);

        MEMOIZED_FIB.put(n, result);
        return result;
    }

    /**
     * Computes the n-th Fibonacci number using bottom-up dynamic programming.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibonacciBottomUp(int n) {
        validateNonNegative(n);

        if (n <= 1) {
            return n;
        }

        Map<Integer, Integer> fibTable = new HashMap<>();
        fibTable.put(0, 0);
        fibTable.put(1, 1);

        for (int i = 2; i <= n; i++) {
            int value = fibTable.get(i - 1) + fibTable.get(i - 2);
            fibTable.put(i, value);
        }

        return fibTable.get(n);
    }

    /**
     * Computes the n-th Fibonacci number using an iterative approach
     * with constant space.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibonacciIterative(int n) {
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
     * Computes the n-th Fibonacci number using Binet's formula.
     * Note: This method uses floating-point arithmetic and may be
     * inaccurate for larger n due to rounding errors.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number (approximate for large n)
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibonacciBinet(int n) {
        validateNonNegative(n);

        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2;

        return (int) ((Math.pow(phi, n) - Math.pow(-phi, -n)) / sqrt5);
    }

    /**
     * Validates that the given number is non-negative.
     *
     * @param n the number to validate
     * @throws IllegalArgumentException if n is negative
     */
    private static void validateNonNegative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }
    }
}