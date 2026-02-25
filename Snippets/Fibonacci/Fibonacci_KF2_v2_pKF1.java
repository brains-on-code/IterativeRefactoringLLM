package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

public final class Fibonacci {

    private Fibonacci() {
    }

    private static final Map<Integer, Integer> FIBONACCI_CACHE = new HashMap<>();

    public static int fibonacciMemoized(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        if (FIBONACCI_CACHE.containsKey(index)) {
            return FIBONACCI_CACHE.get(index);
        }

        int fibonacciNumber;
        if (index <= 1) {
            fibonacciNumber = index;
        } else {
            fibonacciNumber = fibonacciMemoized(index - 1) + fibonacciMemoized(index - 2);
            FIBONACCI_CACHE.put(index, fibonacciNumber);
        }

        return fibonacciNumber;
    }

    public static int fibonacciBottomUp(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        Map<Integer, Integer> fibonacciSequence = new HashMap<>();

        for (int currentIndex = 0; currentIndex <= index; currentIndex++) {
            int fibonacciNumber;
            if (currentIndex <= 1) {
                fibonacciNumber = currentIndex;
            } else {
                fibonacciNumber =
                    fibonacciSequence.get(currentIndex - 1) + fibonacciSequence.get(currentIndex - 2);
            }
            fibonacciSequence.put(currentIndex, fibonacciNumber);
        }

        return fibonacciSequence.get(index);
    }

    public static int fibonacciOptimized(int index) {
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

    public static int fibonacciBinet(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        double squareRootOfFive = Math.sqrt(5);
        double goldenRatio = (1 + squareRootOfFive) / 2;

        return (int) ((Math.pow(goldenRatio, index) - Math.pow(-goldenRatio, -index)) / squareRootOfFive);
    }
}