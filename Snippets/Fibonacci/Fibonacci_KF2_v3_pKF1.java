package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

public final class Fibonacci {

    private Fibonacci() {
    }

    private static final Map<Integer, Integer> MEMOIZATION_CACHE = new HashMap<>();

    public static int fibonacciMemoized(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        if (MEMOIZATION_CACHE.containsKey(n)) {
            return MEMOIZATION_CACHE.get(n);
        }

        int value;
        if (n <= 1) {
            value = n;
        } else {
            value = fibonacciMemoized(n - 1) + fibonacciMemoized(n - 2);
            MEMOIZATION_CACHE.put(n, value);
        }

        return value;
    }

    public static int fibonacciBottomUp(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        Map<Integer, Integer> sequence = new HashMap<>();

        for (int i = 0; i <= n; i++) {
            int value;
            if (i <= 1) {
                value = i;
            } else {
                value = sequence.get(i - 1) + sequence.get(i - 2);
            }
            sequence.put(i, value);
        }

        return sequence.get(n);
    }

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

    public static int fibonacciBinet(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        double sqrtFive = Math.sqrt(5);
        double goldenRatio = (1 + sqrtFive) / 2;

        return (int) ((Math.pow(goldenRatio, n) - Math.pow(-goldenRatio, -n)) / sqrtFive);
    }
}