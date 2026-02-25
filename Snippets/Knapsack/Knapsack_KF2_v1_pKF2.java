package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class Knapsack {

    private Knapsack() {
        // Utility class; prevent instantiation
    }

    /**
     * Validates the input parameters for the knapsack problem.
     *
     * @param weightCapacity maximum weight capacity of the knapsack
     * @param weights        array of item weights
     * @param values         array of item values
     * @throws IllegalArgumentException if any input is invalid
     */
    private static void validateInput(final int weightCapacity, final int[] weights, final int[] values) {
        if (weightCapacity < 0) {
            throw new IllegalArgumentException("Weight capacity should not be negative.");
        }
        if (weights == null || values == null) {
            throw new IllegalArgumentException("Input arrays must not be null.");
        }
        if (weights.length != values.length) {
            throw new IllegalArgumentException("Weights and values arrays must have the same length.");
        }
        if (Arrays.stream(weights).anyMatch(w -> w <= 0)) {
            throw new IllegalArgumentException("Weights must all be positive.");
        }
    }

    /**
     * Solves the 0/1 Knapsack problem using dynamic programming.
     *
     * @param weightCapacity maximum weight capacity of the knapsack
     * @param weights        array of item weights
     * @param values         array of item values
     * @return maximum total value achievable within the given capacity
     * @throws IllegalArgumentException if any input is invalid
     */
    public static int knapSack(final int weightCapacity, final int[] weights, final int[] values) {
        validateInput(weightCapacity, weights, values);

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