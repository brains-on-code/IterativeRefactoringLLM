package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

public final class Fibonacci {

    private Fibonacci() {
    }

    private static final Map<Integer, Integer> memoizedValues = new HashMap<>();

    public static int fibonacciMemoized(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        if (memoizedValues.containsKey(index)) {
            return memoizedValues.get(index);
        }

        int fibonacciValue;
        if (index <= 1) {
            fibonacciValue = index;
        } else {
            fibonacciValue = fibonacciMemoized(index - 1) + fibonacciMemoized(index - 2);
            memoizedValues.put(index, fibonacciValue);
        }

        return fibonacciValue;
    }

    public static int fibonacciBottomUp(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        Map<Integer, Integer> fibonacciSequence = new HashMap<>();

        for (int currentIndex = 0; currentIndex <= index; currentIndex++) {
            int fibonacciValue;
            if (currentIndex <= 1) {
                fibonacciValue = currentIndex;
            } else {
                fibonacciValue =
                    fibonacciSequence.get(currentIndex - 1) + fibonacciSequence.get(currentIndex - 2);
            }
            fibonacciSequence.put(currentIndex, fibonacciValue);
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

        int previousValue = 0;
        int currentValue = 1;

        for (int currentIndex = 2; currentIndex <= index; currentIndex++) {
            int nextValue = previousValue + currentValue;
            previousValue = currentValue;
            currentValue = nextValue;
        }

        return currentValue;
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