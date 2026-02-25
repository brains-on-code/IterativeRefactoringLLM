package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * A Dynamic Programming based solution for the 0-1 Knapsack problem.
 * This class provides a method, {@code knapSack}, that calculates the maximum value that can be
 * obtained from a given set of items with weights and values, while not exceeding a
 * given weight capacity.
 *
 * @see <a href="https://en.wikipedia.org/?title=0-1_Knapsack_problem">0-1 Knapsack Problem</a>
 */
public final class Knapsack {

    private Knapsack() {
        // Utility class; prevent instantiation
    }

    /**
     * Solves the 0-1 Knapsack problem using Dynamic Programming.
     *
     * @param weightCapacity the maximum weight capacity of the knapsack
     * @param weights        an array of item weights
     * @param values         an array of item values
     * @return the maximum value that can be obtained without exceeding the weight capacity
     * @throws IllegalArgumentException if the input arrays are null, have different lengths,
     *                                  or contain invalid values
     */
    public static int knapSack(final int weightCapacity, final int[] weights, final int[] values) {
        validateInput(weightCapacity, weights, values);

        final int itemCount = values.length;
        final int[] maxValueForWeight = new int[weightCapacity + 1];

        for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
            final int itemWeight = weights[itemIndex];
            final int itemValue = values[itemIndex];

            updateMaxValuesForItem(weightCapacity, maxValueForWeight, itemWeight, itemValue);
        }

        return maxValueForWeight[weightCapacity];
    }

    private static void validateInput(final int weightCapacity, final int[] weights, final int[] values) {
        if (weightCapacity < 0) {
            throw new IllegalArgumentException("Weight capacity should not be negative.");
        }
        if (weights == null || values == null) {
            throw new IllegalArgumentException("Input arrays must not be null.");
        }
        if (weights.length != values.length) {
            throw new IllegalArgumentException("Input arrays must have the same length.");
        }
        if (weights.length == 0) {
            return;
        }
        if (Arrays.stream(weights).anyMatch(weight -> weight <= 0)) {
            throw new IllegalArgumentException("Input array should not contain non-positive weight(s).");
        }
    }

    private static void updateMaxValuesForItem(
        final int weightCapacity,
        final int[] maxValueForWeight,
        final int itemWeight,
        final int itemValue
    ) {
        for (int currentCapacity = weightCapacity; currentCapacity >= itemWeight; currentCapacity--) {
            final int remainingCapacity = currentCapacity - itemWeight;
            final int valueWithItem = maxValueForWeight[remainingCapacity] + itemValue;

            maxValueForWeight[currentCapacity] =
                Math.max(maxValueForWeight[currentCapacity], valueWithItem);
        }
    }
}