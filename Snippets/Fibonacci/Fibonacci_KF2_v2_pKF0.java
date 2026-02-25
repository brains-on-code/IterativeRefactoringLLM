package com.thealgorithms.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

public final class Fibonacci {

    private static final Map<Integer, Integer> CACHE = new HashMap<>();

    private Fibonacci() {
        // Utility class; prevent instantiation
    }

    public static int fibMemo(int n) {
        validateNonNegative(n);

        Integer cachedValue = CACHE.get(n);
        if (cachedValue != null) {
            return cachedValue;
        }

        int result = (n <= 1) ? n : fibMemo(n - 1) + fibMemo(n - 2);
        CACHE.put(n, result);
        return result;
    }

    public static int fibBotUp(int n) {
        validateNonNegative(n);

        Map<Integer, Integer> fibValues = new HashMap<>();
        for (int i = 0; i <= n; i++) {
            int value = (i <= 1) ? i : fibValues.get(i - 1) + fibValues.get(i - 2);
            fibValues.put(i, value);
        }

        return fibValues.get(n);
    }

    public static int fibOptimized(int n) {
        validateNonNegative(n);

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

    public static int fibBinet(int n) {
        validateNonNegative(n);

        double sqrt5 = Math.sqrt(5);
        double phi = (1 + sqrt5) / 2;

        return (int) ((Math.pow(phi, n) - Math.pow(-phi, -n)) / sqrt5);
    }

    private static void validateNonNegative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input n must be non-negative");
        }
    }
}