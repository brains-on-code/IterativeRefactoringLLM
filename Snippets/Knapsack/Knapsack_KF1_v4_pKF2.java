package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Solves the 0/1 Knapsack problem using dynamic programming.
 *
 * <p>Given a set of items, each with a weight and a value, determine the maximum
 * value that can be put in a knapsack of a given capacity. Each item can be
 * included at most once (0/1 constraint).</p>
 */
public final class Knapsack01 {

    private Knapsack01() {
        // Prevent instantiation
    }

    /**
     * Validates input for the 0/1 Knapsack problem.
     *
     * @param capacity the maximum weight capacity of the knapsack
     * @param weights  array of item weights
     * @param values   array of item values
     * @throws IllegalArgumentException if inputs are invalid
     */
    private static void validateInputs(final int capacity, final int[] weights, final int[] values) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must not be negative.");
        }
        if (weights == null || values == null) {
            throw new IllegalArgumentException("Weight and value arrays must not be null.");
        }
        if (weights.length != values.length) {
            throw new IllegalArgumentException("Weight and value arrays must have the same length.");
        }
        if (Arrays.stream(weights).anyMatch(w -> w <= 0)) {
            throw new IllegalArgumentException("All weights must be positive.");
        }
    }

    /**
     * Computes the maximum total value achievable within the given capacity
     * using a 1D dynamic programming approach.
     *
     * @param capacity the maximum weight capacity of the knapsack
     * @param weights  array of item weights
     * @param values   array of item values
     * @return the maximum total value achievable within the given capacity
     * @throws IllegalArgumentException if inputs are invalid
     */
    public static int solve(final int capacity, final int[] weights, final int[] values) {
        validateInputs(capacity, weights, values);

        int[] dp = new int[capacity + 1];

        for (int i = 0; i < values.length; i++) {
            int itemWeight = weights[i];
            int itemValue = values[i];

            for (int w = capacity; w >= itemWeight; w--) {
                dp[w] = Math.max(dp[w], dp[w - itemWeight] + itemValue);
            }
        }

        return dp[capacity];
    }
}