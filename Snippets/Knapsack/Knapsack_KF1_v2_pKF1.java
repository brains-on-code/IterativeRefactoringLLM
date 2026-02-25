package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * 0-1 Knapsack problem implementation.
 */
public final class ZeroOneKnapsack {

    private ZeroOneKnapsack() {
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
     * @param capacity maximum weight capacity of the knapsack
     * @param weights  array of item weights
     * @param values   array of item values
     * @return maximum total value achievable within the given capacity
     * @throws IllegalArgumentException if inputs are invalid
     */
    public static int getMaxKnapsackValue(final int capacity, final int[] weights, final int[] values)
        throws IllegalArgumentException {

        validateInputs(capacity, weights, values);

        int[] maxValuesForCapacity = new int[capacity + 1];

        for (int itemIndex = 0; itemIndex < values.length; itemIndex++) {
            for (int currentCapacity = capacity; currentCapacity > 0; currentCapacity--) {
                if (weights[itemIndex] <= currentCapacity) {
                    maxValuesForCapacity[currentCapacity] =
                        Math.max(
                            maxValuesForCapacity[currentCapacity],
                            maxValuesForCapacity[currentCapacity - weights[itemIndex]] + values[itemIndex]
                        );
                }
            }
        }

        return maxValuesForCapacity[capacity];
    }
}