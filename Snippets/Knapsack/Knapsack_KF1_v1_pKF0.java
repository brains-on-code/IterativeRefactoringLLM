package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * 0-1 Knapsack problem implementation.
 *
 * Computes the maximum total value that can be obtained with a given weight
 * capacity and arrays of item weights and values, where each item can be
 * taken at most once.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    private static void validateInputs(final int capacity, final int[] weights, final int[] values) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Weight capacity should not be negative.");
        }
        if (weights == null || values == null || weights.length != values.length) {
            throw new IllegalArgumentException("Input arrays must not be null and must have the same length.");
        }
        if (Arrays.stream(weights).anyMatch(weight -> weight <= 0)) {
            throw new IllegalArgumentException("Input array should not contain non-positive weight(s).");
        }
    }

    /**
     * Solves the 0-1 Knapsack problem using dynamic programming.
     *
     * @param capacity the maximum weight capacity of the knapsack
     * @param weights  the weights of the items
     * @param values   the values of the items
     * @return the maximum total value achievable within the given capacity
     * @throws IllegalArgumentException if inputs are invalid
     */
    public static int method2(final int capacity, final int[] weights, final int[] values)
        throws IllegalArgumentException {

        validateInputs(capacity, weights, values);

        int[] dp = new int[capacity + 1];

        for (int i = 0; i < values.length; i++) {
            for (int w = capacity; w >= weights[i]; w--) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }

        return dp[capacity];
    }
}