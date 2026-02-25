package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Varun Upadhyay (https://github.com/varunu28)
 */
public final class Fibonacci {

    private Fibonacci() {
    }

    private static final Map<Integer, Integer> FIBONACCI_CACHE = new HashMap<>();

    /**
     * This method finds the nth Fibonacci number using memoization technique.
     *
     * @param index the index for which we have to determine the Fibonacci number
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if index is negative
     */
    public static int fibonacciMemoized(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        if (FIBONACCI_CACHE.containsKey(index)) {
            return FIBONACCI_CACHE.get(index);
        }

        int fibonacciValue;
        if (index <= 1) {
            fibonacciValue = index;
        } else {
            fibonacciValue = fibonacciMemoized(index - 1) + fibonacciMemoized(index - 2);
            FIBONACCI_CACHE.put(index, fibonacciValue);
        }

        return fibonacciValue;
    }

    /**
     * This method finds the nth Fibonacci number using a bottom-up dynamic programming approach.
     *
     * @param index the index for which we have to determine the Fibonacci number
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if index is negative
     */
    public static int fibonacciBottomUp(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        Map<Integer, Integer> fibonacciTable = new HashMap<>();

        for (int currentIndex = 0; currentIndex <= index; currentIndex++) {
            int fibonacciValue;
            if (currentIndex <= 1) {
                fibonacciValue = currentIndex;
            } else {
                fibonacciValue =
                    fibonacciTable.get(currentIndex - 1) + fibonacciTable.get(currentIndex - 2);
            }
            fibonacciTable.put(currentIndex, fibonacciValue);
        }

        return fibonacciTable.get(index);
    }

    /**
     * This method finds the nth Fibonacci number using an optimized bottom-up approach.
     *
     * <p>This is an optimized version of the Fibonacci program without using a HashMap or recursion.
     * It saves both memory and time. Space complexity is O(1) and time complexity is O(n).
     *
     * @param index the index for which we have to determine the Fibonacci number
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if index is negative
     * @author Shoaib Rayeen (https://github.com/shoaibrayeen)
     */
    public static int fibonacciOptimized(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        if (index == 0) {
            return 0;
        }

        int previousValue = 0;
        int currentValue = 1;

        for (int currentIndex = 2; currentIndex <= index; currentIndex++) {
            int nextValue = previousValue + currentValue;
            previousValue = currentValue;
            currentValue = nextValue;
        }

        return currentValue;
    }

    /**
     * This method finds the nth Fibonacci number using Binet's formula in constant time.
     *
     * <p>The Fibonacci terms maintain a ratio called the golden ratio denoted by Φ (phi).
     * Φ = (1 + √5) / 2. Binet's formula: Fₙ = (Φⁿ – (–Φ)⁻ⁿ) / √5.
     *
     * @param index the index for which we have to determine the Fibonacci number
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if index is negative
     */
    public static int fibonacciBinet(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        double squareRootOfFive = Math.sqrt(5);
        double goldenRatio = (1 + squareRootOfFive) / 2;

        return (int) ((Math.pow(goldenRatio, index) - Math.pow(-goldenRatio, -index)) / squareRootOfFive);
    }
}