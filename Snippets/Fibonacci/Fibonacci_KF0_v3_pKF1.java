package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Varun Upadhyay (https://github.com/varunu28)
 */
public final class Fibonacci {

    private Fibonacci() {
    }

    private static final Map<Integer, Integer> MEMOIZED_FIBONACCI = new HashMap<>();

    /**
     * This method finds the nth Fibonacci number using memoization technique.
     *
     * @param n the index for which we have to determine the Fibonacci number
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if n is negative
     */
    public static int fibonacciMemoized(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        if (MEMOIZED_FIBONACCI.containsKey(n)) {
            return MEMOIZED_FIBONACCI.get(n);
        }

        int fibonacciValue;
        if (n <= 1) {
            fibonacciValue = n;
        } else {
            fibonacciValue = fibonacciMemoized(n - 1) + fibonacciMemoized(n - 2);
            MEMOIZED_FIBONACCI.put(n, fibonacciValue);
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
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        Map<Integer, Integer> fibonacciTable = new HashMap<>();

        for (int i = 0; i <= n; i++) {
            int fibonacciValue;
            if (i <= 1) {
                fibonacciValue = i;
            } else {
                fibonacciValue = fibonacciTable.get(i - 1) + fibonacciTable.get(i - 2);
            }
            fibonacciTable.put(i, fibonacciValue);
        }

        return fibonacciTable.get(n);
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
            throw new IllegalArgumentException("Input index must be non-negative");
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
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        double sqrtFive = Math.sqrt(5);
        double goldenRatio = (1 + sqrtFive) / 2;

        return (int) ((Math.pow(goldenRatio, n) - Math.pow(-goldenRatio, -n)) / sqrtFive);
    }
}