package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for computing Fibonacci numbers using various approaches.
 *
 * @author Varun Upadhyay (https://github.com/varunu28)
 */
public final class Fibonacci {

    private Fibonacci() {
        // Prevent instantiation
    }

    private static final Map<Integer, Integer> CACHE = new HashMap<>();

    /**
     * Computes the nth Fibonacci number using memoization.
     *
     * @param n index of the Fibonacci number (must be non-negative)
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibMemo(int n) {
        validateNonNegative(n);

        Integer cachedValue = CACHE.get(n);
        if (cachedValue != null) {
            return cachedValue;
        }

        int result;
        if (n <= 1) {
            result = n;
        } else {
            result = fibMemo(n - 1) + fibMemo(n - 2);
        }

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

        Map<Integer, Integer> fib = new HashMap<>();
        for (int i = 0; i <= n; i++) {
            int value = (i <= 1) ? i : fib.get(i - 1) + fib.get(i - 2);
            fib.put(i, value);
        }

        return fib.get(n);
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
     * @author Shoaib Rayeen (https://github.com/shoaibrayeen)
     */
    public static int fibOptimized(int n) {
        validateNonNegative(n);

        if (n == 0) {
            return 0;
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

        double squareRootOf5 = Math.sqrt(5);
        double phi = (1 + squareRootOf5) / 2;
        double numerator = Math.pow(phi, n) - Math.pow(-phi, -n);

        return (int) (numerator / squareRootOf5);
    }

    private static void validateNonNegative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }
    }
}