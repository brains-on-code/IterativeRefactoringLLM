package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class Knapsack {

    private Knapsack() {
    }

    private static void validateInput(final int capacity, final int[] itemWeights, final int[] itemValues) {
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

    public static int knapSack(final int capacity, final int[] itemWeights, final int[] itemValues) throws IllegalArgumentException {
        validateInput(capacity, itemWeights, itemValues);

        int[] maxValueForWeight = new int[capacity + 1];

        for (int itemIndex = 0; itemIndex < itemValues.length; itemIndex++) {
            for (int currentCapacity = capacity; currentCapacity > 0; currentCapacity--) {
                if (itemWeights[itemIndex] <= currentCapacity) {
                    maxValueForWeight[currentCapacity] =
                        Math.max(
                            maxValueForWeight[currentCapacity],
                            maxValueForWeight[currentCapacity - itemWeights[itemIndex]] + itemValues[itemIndex]
                        );
                }
            }
        }

        return maxValueForWeight[capacity];
    }
}