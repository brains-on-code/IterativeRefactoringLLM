package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Varun Upadhyay (https://github.com/varunu28)
 */
public final class Fibonacci {

    private Fibonacci() {
    }

    private static final Map<Integer, Integer> MEMOIZATION_CACHE = new HashMap<>();

    /**
     * This method finds the nth Fibonacci number using memoization technique.
     *
     * @param n the index for which we have to determine the Fibonacci number
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibonacciMemoized(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }

        if (MEMOIZATION_CACHE.containsKey(n)) {
            return MEMOIZATION_CACHE.get(n);
        }

        int fibonacciValue;
        if (n <= 1) {
            fibonacciValue = n;
        } else {
            fibonacciValue = fibonacciMemoized(n - 1) + fibonacciMemoized(n - 2);
            MEMOIZATION_CACHE.put(n, fibonacciValue);
        }

        return fibonacciValue;
    }

    /**
     * This method finds the nth Fibonacci number using a bottom-up dynamic programming approach.
     *
     * @param n the index for which we have to determine the Fibonacci number
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
     * This method finds the nth Fibonacci number using an optimized bottom-up approach.
     *
     * <p>This is an optimized version of the Fibonacci program without using a HashMap or recursion.
     * It saves both memory and time. Space complexity is O(1) and time complexity is O(n).
     *
     * @param n the index for which we have to determine the Fibonacci number
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative
     * @author Shoaib Rayeen (https://github.com/shoaibrayeen)
     */
    public static int fibonacciOptimized(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }

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
     * This method finds the nth Fibonacci number using Binet's formula in constant time.
     *
     * <p>The Fibonacci terms maintain a ratio called the golden ratio denoted by Φ (phi).
     * Φ = (1 + √5) / 2. Binet's formula: Fₙ = (Φⁿ – (–Φ)⁻ⁿ) / √5.
     *
     * @param n the index for which we have to determine the Fibonacci number
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibonacciBinet(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }

        double squareRootOfFive = Math.sqrt(5);
        double goldenRatio = (1 + squareRootOfFive) / 2;

        return (int) ((Math.pow(goldenRatio, n) - Math.pow(-goldenRatio, -n)) / squareRootOfFive);
    }
}