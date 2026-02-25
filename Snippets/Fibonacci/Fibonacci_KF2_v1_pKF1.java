package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

public final class Fibonacci {

    private Fibonacci() {
    }

    private static final Map<Integer, Integer> MEMOIZATION_CACHE = new HashMap<>();

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

    public static int fibonacciBinet(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }

        double squareRootOfFive = Math.sqrt(5);
        double goldenRatio = (1 + squareRootOfFive) / 2;

        return (int) ((Math.pow(goldenRatio, n) - Math.pow(-goldenRatio, -n)) / squareRootOfFive);
    }
}