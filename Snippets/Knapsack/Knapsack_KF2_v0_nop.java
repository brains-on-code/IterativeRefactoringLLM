package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;


public final class Knapsack {

    private Knapsack() {
    }

    private static void throwIfInvalidInput(final int weightCapacity, final int[] weights, final int[] values) {
        if (weightCapacity < 0) {
            throw new IllegalArgumentException("Weight capacity should not be negative.");
        }
        if (weights == null || values == null || weights.length != values.length) {
            throw new IllegalArgumentException("Input arrays must not be null and must have the same length.");
        }
        if (Arrays.stream(weights).anyMatch(w -> w <= 0)) {
            throw new IllegalArgumentException("Input array should not contain non-positive weight(s).");
        }
    }


    public static int knapSack(final int weightCapacity, final int[] weights, final int[] values) throws IllegalArgumentException {
        throwIfInvalidInput(weightCapacity, weights, values);

        int[] dp = new int[weightCapacity + 1];

        for (int i = 0; i < values.length; i++) {
            for (int w = weightCapacity; w > 0; w--) {
                if (weights[i] <= w) {
                    dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
                }
            }
        }

        return dp[weightCapacity];
    }
}
