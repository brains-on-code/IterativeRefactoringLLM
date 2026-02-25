package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * A Dynamic Programming based solution for the 0-1 Knapsack problem.
 * This class provides a method, `knapSack`, that calculates the maximum value that can be
 * obtained from a given set of items with weights and values, while not exceeding a
 * given weight capacity.
 *
 * @see <a href="https://en.wikipedia.org/?title=0-1_Knapsack_problem">0-1 Knapsack Problem </a>
 */
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

    /**
     * Solves the 0-1 Knapsack problem using Dynamic Programming.
     *
     * @param capacity   The maximum weight capacity of the knapsack.
     * @param itemWeights An array of item weights.
     * @param itemValues  An array of item values.
     * @return The maximum value that can be obtained without exceeding the weight capacity.
     * @throws IllegalArgumentException If the input arrays are null or have different lengths.
     */
    public static int knapSack(final int capacity, final int[] itemWeights, final int[] itemValues)
        throws IllegalArgumentException {

        validateInput(capacity, itemWeights, itemValues);

        int[] maxValueForCapacity = new int[capacity + 1];

        for (int itemIndex = 0; itemIndex < itemValues.length; itemIndex++) {
            int currentItemWeight = itemWeights[itemIndex];
            int currentItemValue = itemValues[itemIndex];

            for (int currentCapacity = capacity; currentCapacity > 0; currentCapacity--) {
                if (currentItemWeight <= currentCapacity) {
                    maxValueForCapacity[currentCapacity] =
                        Math.max(
                            maxValueForCapacity[currentCapacity],
                            maxValueForCapacity[currentCapacity - currentItemWeight] + currentItemValue
                        );
                }
            }
        }

        return maxValueForCapacity[capacity];
    }
}