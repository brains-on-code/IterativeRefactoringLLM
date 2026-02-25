package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Dynamic Programming solution for the 0-1 Knapsack problem.
 *
 * <p>Given arrays of item weights and values, and a maximum weight capacity,
 * this class provides a method to compute the maximum total value achievable
 * without exceeding the capacity, where each item can be taken at most once.
 *
 * @see <a href="https://en.wikipedia.org/?title=0-1_Knapsack_problem">0-1 Knapsack Problem</a>
 */
public final class Knapsack {

    private Knapsack() {
        // Utility class; prevent instantiation.
    }

    private static void validateInput(final int weightCapacity, final int[] weights, final int[] values) {
        if (weightCapacity < 0) {
            throw new IllegalArgumentException("Weight capacity must not be negative.");
        }
        if (weights == null || values == null) {
            throw new IllegalArgumentException("Weight and value arrays must not be null.");
        }
        if (weights.length != values.length) {
            throw new IllegalArgumentException("Weight and value arrays must have the same length.");
        }
        if (Arrays.stream(weights).anyMatch(w -> w <= 0)) {
            throw new IllegalArgumentException("All item weights must be positive.");
        }
    }

    /**
     * Computes the maximum value achievable with the given capacity using 0-1 Knapsack DP.
     *
     * <p>Time complexity: O(n * W), where n is the number of items and W is the capacity.
     * Space complexity: O(W).
     *
     * @param weightCapacity the maximum weight capacity of the knapsack
     * @param weights        array of item weights
     * @param values         array of item values
     * @return the maximum total value achievable without exceeding the capacity
     * @throws IllegalArgumentException if inputs are invalid
     */
    public static int knapSack(final int weightCapacity, final int[] weights, final int[] values) {
        validateInput(weightCapacity, weights, values);

        int[] dp = new int[weightCapacity + 1];

        for (int i = 0; i < values.length; i++) {
            int itemWeight = weights[i];
            int itemValue = values[i];

            for (int w = weightCapacity; w >= itemWeight; w--) {
                dp[w] = Math.max(dp[w], dp[w - itemWeight] + itemValue);
            }
        }

        return dp[weightCapacity];
    }
}