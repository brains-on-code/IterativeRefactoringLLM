package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class Knapsack {

    private Knapsack() {
        // Utility class; prevent instantiation
    }

    private static void validateInput(final int capacity, final int[] weights, final int[] values) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Weight capacity should not be negative.");
        }
        if (weights == null || values == null) {
            throw new IllegalArgumentException("Input arrays must not be null.");
        }
        if (weights.length != values.length) {
            throw new IllegalArgumentException("Input arrays must have the same length.");
        }
        if (Arrays.stream(weights).anyMatch(weight -> weight <= 0)) {
            throw new IllegalArgumentException("Input array should not contain non-positive weight(s).");
        }
    }

    public static int knapSack(final int capacity, final int[] weights, final int[] values) {
        validateInput(capacity, weights, values);

        final int itemCount = values.length;
        final int[] maxValueForWeight = new int[capacity + 1];

        for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
            final int itemWeight = weights[itemIndex];
            final int itemValue = values[itemIndex];

            for (int currentCapacity = capacity; currentCapacity >= itemWeight; currentCapacity--) {
                final int remainingCapacity = currentCapacity - itemWeight;
                final int valueWithItem = maxValueForWeight[remainingCapacity] + itemValue;

                maxValueForWeight[currentCapacity] =
                        Math.max(maxValueForWeight[currentCapacity], valueWithItem);
            }
        }

        return maxValueForWeight[capacity];
    }
}