package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for computing Fibonacci numbers using various approaches.
 */
public final class Fibonacci {

    private Fibonacci() {
    }

    private static final Map<Integer, Integer> memoizedFibonacciValues = new HashMap<>();

    /**
     * Computes the nth Fibonacci number using a recursive memoized approach.
     *
     * @param index the index of the Fibonacci number to compute (must be non-negative)
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if index is negative
     */
    public static int fibonacciRecursiveMemoized(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }
        if (memoizedFibonacciValues.containsKey(index)) {
            return memoizedFibonacciValues.get(index);
        }

        int fibonacciValue;
        if (index <= 1) {
            fibonacciValue = index;
        } else {
            fibonacciValue =
                fibonacciRecursiveMemoized(index - 1) + fibonacciRecursiveMemoized(index - 2);
            memoizedFibonacciValues.put(index, fibonacciValue);
        }
        return fibonacciValue;
    }

    /**
     * Computes the nth Fibonacci number using a bottom-up dynamic programming approach.
     *
     * @param index the index of the Fibonacci number to compute (must be non-negative)
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if index is negative
     */
    public static int fibonacciBottomUp(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        Map<Integer, Integer> fibonacciValuesByIndex = new HashMap<>();

        for (int currentIndex = 0; currentIndex <= index; currentIndex++) {
            int fibonacciValue;
            if (currentIndex <= 1) {
                fibonacciValue = currentIndex;
            } else {
                fibonacciValue =
                    fibonacciValuesByIndex.get(currentIndex - 1)
                        + fibonacciValuesByIndex.get(currentIndex - 2);
            }
            fibonacciValuesByIndex.put(currentIndex, fibonacciValue);
        }

        return fibonacciValuesByIndex.get(index);
    }

    /**
     * Computes the nth Fibonacci number using an iterative approach with constant space.
     *
     * @param index the index of the Fibonacci number to compute (must be non-negative)
     * @return the nth Fibonacci number
     * @throws IllegalArgumentException if index is negative
     */
    public static int fibonacciIterative(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }
        if (index == 0) {
            return 0;
        }

        int previousFibonacci = 0;
        int currentFibonacci = 1;

        for (int currentIndex = 2; currentIndex <= index; currentIndex++) {
            int nextFibonacci = previousFibonacci + currentFibonacci;
            previousFibonacci = currentFibonacci;
            currentFibonacci = nextFibonacci;
        }
        return currentFibonacci;
    }

    /**
     * Computes the nth Fibonacci number using Binet's formula.
     *
     * @param index the index of the Fibonacci number to compute (must be non-negative)
     * @return the nth Fibonacci number (rounded down to the nearest integer)
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