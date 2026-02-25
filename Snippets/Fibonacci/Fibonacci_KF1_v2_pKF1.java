package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for computing Fibonacci numbers using various approaches.
 */
public final class Fibonacci {

    private Fibonacci() {
    }

    private static final Map<Integer, Integer> MEMOIZED_FIBONACCI = new HashMap<>();

    /**
     * Computes the nth Fibonacci number using a recursive memoized approach.
     *
     * @param n the index of the Fibonacci number to compute (must be non-negative)
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibonacciRecursiveMemoized(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }
        if (MEMOIZED_FIBONACCI.containsKey(n)) {
            return MEMOIZED_FIBONACCI.get(n);
        }

        int fibonacciValue;
        if (n <= 1) {
            fibonacciValue = n;
        } else {
            fibonacciValue = fibonacciRecursiveMemoized(n - 1) + fibonacciRecursiveMemoized(n - 2);
            MEMOIZED_FIBONACCI.put(n, fibonacciValue);
        }
        return fibonacciValue;
    }

    /**
     * Computes the nth Fibonacci number using a bottom-up dynamic programming approach.
     *
     * @param n the index of the Fibonacci number to compute (must be non-negative)
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibonacciBottomUp(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }

        Map<Integer, Integer> fibonacciValues = new HashMap<>();

        for (int i = 0; i <= n; i++) {
            int fibonacciValue;
            if (i <= 1) {
                fibonacciValue = i;
            } else {
                fibonacciValue = fibonacciValues.get(i - 1) + fibonacciValues.get(i - 2);
            }
            fibonacciValues.put(i, fibonacciValue);
        }

        return fibonacciValues.get(n);
    }

    /**
     * Computes the nth Fibonacci number using an iterative approach with constant space.
     *
     * @param n the index of the Fibonacci number to compute (must be non-negative)
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibonacciIterative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }
        if (n == 0) {
            return 0;
        }

        int previousValue = 0;
        int currentValue = 1;

        for (int i = 2; i <= n; i++) {
            int nextValue = previousValue + currentValue;
            previousValue = currentValue;
            currentValue = nextValue;
        }
        return currentValue;
    }

    /**
     * Computes the nth Fibonacci number using Binet's formula.
     *
     * @param n the index of the Fibonacci number to compute (must be non-negative)
     * @return the nth Fibonacci number (rounded down to the nearest integer)
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibonacciBinet(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }

        double sqrtFive = Math.sqrt(5);
        double goldenRatio = (1 + sqrtFive) / 2;
        return (int) ((Math.pow(goldenRatio, n) - Math.pow(-goldenRatio, -n)) / sqrtFive);
    }
}