package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Utility class for solving the 0/1 Knapsack problem using dynamic programming.
 *
 * <p>Given a set of items, each with a weight and a value, determine the maximum
 * value that can be put in a knapsack of a given capacity. Each item can be
 * included at most once (0/1 constraint).</p>
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
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
            throw new IllegalArgumentException("Weight capacity should not be negative.");
        }
        if (weights == null || values == null || weights.length != values.length) {
            throw new IllegalArgumentException("Input arrays must not be null and must have the same length.");
        }
        if (Arrays.stream(weights).anyMatch(w -> w <= 0)) {
            throw new IllegalArgumentException("Input array should not contain non-positive weight(s).");
        }
    }

    /**
     * Solves the 0/1 Knapsack problem using a 1D dynamic programming approach.
     *
     * @param capacity the maximum weight capacity of the knapsack
     * @param weights  array of item weights
     * @param values   array of item values
     * @return the maximum total value achievable within the given capacity
     * @throws IllegalArgumentException if inputs are invalid
     */
    public static int method2(final int capacity, final int[] weights, final int[] values)
        throws IllegalArgumentException {

        validateInputs(capacity, weights, values);

        // dp[w] will hold the maximum value achievable with capacity w
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < values.length; i++) {
            // Traverse capacities in reverse to enforce 0/1 constraint
            for (int w = capacity; w > 0; w--) {
                if (weights[i] <= w) {
                    dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
                }
            }
        }

        return dp[capacity];
    }
}