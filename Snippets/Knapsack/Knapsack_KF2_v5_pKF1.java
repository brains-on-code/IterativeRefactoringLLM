package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class Knapsack {

    private Knapsack() {
    }

    private static void validateInput(final int capacity, final int[] weights, final int[] values) {
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

    public static int knapSack(final int capacity, final int[] weights, final int[] values)
        throws IllegalArgumentException {

        validateInput(capacity, weights, values);

        int[] maxValueForCapacity = new int[capacity + 1];

        for (int itemIndex = 0; itemIndex < values.length; itemIndex++) {
            int itemWeight = weights[itemIndex];
            int itemValue = values[itemIndex];

            for (int currentCapacity = capacity; currentCapacity > 0; currentCapacity--) {
                if (itemWeight <= currentCapacity) {
                    maxValueForCapacity[currentCapacity] =
                        Math.max(
                            maxValueForCapacity[currentCapacity],
                            maxValueForCapacity[currentCapacity - itemWeight] + itemValue
                        );
                }
            }
        }

        return maxValueForCapacity[capacity];
    }
}