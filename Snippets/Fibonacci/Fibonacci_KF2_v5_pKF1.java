package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

public final class Fibonacci {

    private Fibonacci() {
    }

    private static final Map<Integer, Integer> memo = new HashMap<>();

    public static int fibonacciMemoized(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        int value;
        if (n <= 1) {
            value = n;
        } else {
            value = fibonacciMemoized(n - 1) + fibonacciMemoized(n - 2);
            memo.put(n, value);
        }

        return value;
    }

    public static int fibonacciBottomUp(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        Map<Integer, Integer> dp = new HashMap<>();

        for (int i = 0; i <= n; i++) {
            int value;
            if (i <= 1) {
                value = i;
            } else {
                value = dp.get(i - 1) + dp.get(i - 2);
            }
            dp.put(i, value);
        }

        return dp.get(n);
    }

    public static int fibonacciOptimized(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        if (n == 0) {
            return 0;
        }

        int prev = 0;
        int curr = 1;

        for (int i = 2; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }

        return curr;
    }

    public static int fibonacciBinet(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input index must be non-negative");
        }

        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2;

        return (int) ((Math.pow(phi, n) - Math.pow(-phi, -n)) / sqrt5);
    }
}