package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * 0-1 Knapsack problem implementation.
 */
public final class ZeroOneKnapsack {

    private ZeroOneKnapsack() {
    }

    private static void validateInputs(final int capacity, final int[] itemWeights, final int[] itemValues) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Weight capacity should not be negative.");
        }
        if (itemWeights == null || itemValues == null || itemWeights.length != itemValues.length) {
            throw new IllegalArgumentException("Input arrays must not be null and must have the same length.");
        }
        if (Arrays.stream(itemWeights).anyMatch(weight -> weight <= 0)) {
            throw new IllegalArgumentException("Input array should not contain non-positive weight(s).");
        }
    }

    /**
     * Solves the 0-1 Knapsack problem using dynamic programming.
     *
     * @param capacity   maximum weight capacity of the knapsack
     * @param itemWeights array of item weights
     * @param itemValues  array of item values
     * @return maximum total value achievable within the given capacity
     * @throws IllegalArgumentException if inputs are invalid
     */
    public static int getMaxKnapsackValue(final int capacity, final int[] itemWeights, final int[] itemValues)
        throws IllegalArgumentException {

        validateInputs(capacity, itemWeights, itemValues);

        int[] maxValueAtCapacity = new int[capacity + 1];

        for (int index = 0; index < itemValues.length; index++) {
            int currentItemWeight = itemWeights[index];
            int currentItemValue = itemValues[index];

            for (int remainingCapacity = capacity; remainingCapacity > 0; remainingCapacity--) {
                if (currentItemWeight <= remainingCapacity) {
                    maxValueAtCapacity[remainingCapacity] =
                        Math.max(
                            maxValueAtCapacity[remainingCapacity],
                            maxValueAtCapacity[remainingCapacity - currentItemWeight] + currentItemValue
                        );
                }
            }
        }

        return maxValueAtCapacity[capacity];
    }
}