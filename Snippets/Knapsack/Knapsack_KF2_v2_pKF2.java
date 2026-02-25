package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Utility class providing a dynamic programming solution
 * to the 0/1 Knapsack problem.
 */
public final class Knapsack {

    private Knapsack() {
        // Prevent instantiation
    }

    /**
     * Ensures that the provided knapsack parameters are valid.
     *
     * @param weightCapacity maximum weight capacity of the knapsack (non-negative)
     * @param weights        array of item weights (non-null, positive values)
     * @param values         array of item values (non-null, same length as weights)
     * @throws IllegalArgumentException if any input is invalid
     */
    private static void validateInput(final int weightCapacity, final int[] weights, final int[] values) {
        if (weightCapacity < 0) {
            throw new IllegalArgumentException("Weight capacity must not be negative.");
        }
        if (weights == null || values == null) {
            throw new IllegalArgumentException("Weights and values arrays must not be null.");
        }
        if (weights.length != values.length) {
            throw new IllegalArgumentException("Weights and values arrays must have the same length.");
        }
        if (Arrays.stream(weights).anyMatch(w -> w <= 0)) {
            throw new IllegalArgumentException("All weights must be positive.");
        }
    }

    /**
     * Computes the maximum total value achievable with the given items and capacity
     * using a 0/1 Knapsack dynamic programming approach.
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
            int itemWeight = weights[i];
            int itemValue = values[i];

            for (int currentCapacity = weightCapacity; currentCapacity >= itemWeight; currentCapacity--) {
                dp[currentCapacity] =
                    Math.max(dp[currentCapacity], dp[currentCapacity - itemWeight] + itemValue);
            }
        }

        return dp[weightCapacity];
    }
}