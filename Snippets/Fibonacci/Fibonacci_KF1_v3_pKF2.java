package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for computing Fibonacci numbers using multiple strategies.
 */
public final class FibonacciUtils {

    /** Cache for memoized recursive Fibonacci computation. */
    private static final Map<Integer, Integer> MEMO = new HashMap<>();

    private FibonacciUtils() {
        // Prevent instantiation.
    }

    /**
     * Computes the n-th Fibonacci number using top-down recursion with memoization.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static int fibRecursiveMemo(int n) {
        validateNonNegative(n);

        if (MEMO.containsKey(n)) {
            return MEMO.get(n);
        }

        int result = (n <= 1)
            ? n
            : fibRecursiveMemo(n - 1) + fibRecursiveMemo(n - 2);

        MEMO.put(n, result);
        return result;
    }

    /**
     * Computes the n-th Fibonacci number using bottom-up dynamic programming
     * with a map as the DP table.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number
     * @throws IllegalArgumentException if {@code n} is negative
     */
    public static int fibBottomUpMap(int n) {
        validateNonNegative(n);

        Map<Integer, Integer> dp = new HashMap<>();

        for (int i = 0; i <= n; i++) {
            int value = (i <= 1)
                ? i
                : dp.get(i - 1) + dp.get(i - 2);
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
    public static int fibIterative(int n) {
        validateNonNegative(n);

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
     * Computes the n-th Fibonacci number using Binet's formula (closed form).
     *
     * <p>Note: Uses floating-point arithmetic and may be inaccurate for large {@code n}.</p>
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return n-th Fibonacci number (rounded down to int)
     * @throws IllegalArgumentException if {@code n} is negative
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
     * @throws IllegalArgumentException if {@code n} is negative
     */
    private static void validateNonNegative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }
    }
}