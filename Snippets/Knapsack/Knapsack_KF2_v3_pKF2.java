package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Provides a dynamic programming solution to the 0/1 Knapsack problem.
 */
public final class Knapsack {

    private Knapsack() {
        // Utility class; prevent instantiation
    }

    /**
     * Validates the input parameters for the knapsack problem.
     *
     * @param weightCapacity maximum weight capacity of the knapsack (must be >= 0)
     * @param weights        array of item weights (non-null, all > 0)
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

        int[] maxValueForCapacity = new int[weightCapacity + 1];

        for (int i = 0; i < values.length; i++) {
            int itemWeight = weights[i];
            int itemValue = values[i];

            // Traverse capacities in reverse to ensure each item is used at most once
            for (int currentCapacity = weightCapacity; currentCapacity >= itemWeight; currentCapacity--) {
                int withoutItem = maxValueForCapacity[currentCapacity];
                int withItem = maxValueForCapacity[currentCapacity - itemWeight] + itemValue;

                maxValueForCapacity[currentCapacity] = Math.max(withoutItem, withItem);
            }
        }

        return maxValueForCapacity[weightCapacity];
    }
}