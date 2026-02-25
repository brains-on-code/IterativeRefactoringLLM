package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * 0-1 Knapsack problem implementation.
 *
 * Computes the maximum total value that can be obtained with a given weight
 * capacity and arrays of item weights and values, where each item can be
 * taken at most once.
 */
public final class Knapsack01 {

    private Knapsack01() {
        // Utility class; prevent instantiation
    }

    private static void validateInputs(final int capacity, final int[] weights, final int[] values) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Weight capacity should not be negative.");
        }
        if (weights == null || values == null) {
            throw new IllegalArgumentException("Input arrays must not be null.");
        }
        if (weights.length != values.length) {
            throw new IllegalArgumentException("Weights and values arrays must have the same length.");
        }
        if (Arrays.stream(weights).anyMatch(weight -> weight <= 0)) {
            throw new IllegalArgumentException("Weights must all be positive.");
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
    public static int solve(final int capacity, final int[] weights, final int[] values) {
        validateInputs(capacity, weights, values);

        final int itemCount = values.length;
        final int[] maxValueForWeight = new int[capacity + 1];

        for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
            final int itemWeight = weights[itemIndex];
            final int itemValue = values[itemIndex];

            for (int currentCapacity = capacity; currentCapacity >= itemWeight; currentCapacity--) {
                int withoutItem = maxValueForWeight[currentCapacity];
                int withItem = maxValueForWeight[currentCapacity - itemWeight] + itemValue;
                maxValueForWeight[currentCapacity] = Math.max(withoutItem, withItem);
            }
        }

        return maxValueForWeight[capacity];
    }
}