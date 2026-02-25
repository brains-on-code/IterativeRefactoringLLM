package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Solves the fractional knapsack problem using a greedy algorithm.
 *
 * <p>Given arrays of item weights and values, and a knapsack capacity, this method computes
 * the maximum total value that can be carried in the knapsack when fractions of items
 * are allowed.</p>
 *
 * <p>Problem reference: https://en.wikipedia.org/wiki/Continuous_knapsack_problem</p>
 */
public final class FractionalKnapsack {

    private FractionalKnapsack() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the maximum value that can be accommodated in a knapsack of a given capacity.
     *
     * @param weights  array of item weights
     * @param values   array of item values
     * @param capacity maximum weight capacity of the knapsack
     * @return maximum achievable value with fractional items allowed
     * @throws IllegalArgumentException if input arrays are null, of different lengths,
     *                                  or if capacity is negative
     */
    public static int fractionalKnapsack(int[] weights, int[] values, int capacity) {
        validateInput(weights, values, capacity);

        // Each entry: [0] = original index, [1] = value-to-weight ratio
        double[][] itemRatios = buildItemRatios(weights, values);

        // Sort items by ratio in ascending order; we'll iterate from the end (highest ratio first)
        Arrays.sort(itemRatios, Comparator.comparingDouble(o -> o[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        // Greedily pick items with the highest value-to-weight ratio first
        for (int i = itemRatios.length - 1; i >= 0 && remainingCapacity > 0; i--) {
            int index = (int) itemRatios[i][0];
            double ratio = itemRatios[i][1];

            if (remainingCapacity >= weights[index]) {
                // Take the whole item
                totalValue += values[index];
                remainingCapacity -= weights[index];
            } else {
                // Take only the fraction that fits
                totalValue += (int) (ratio * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }

    private static void validateInput(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null) {
            throw new IllegalArgumentException("Weights and values arrays must not be null.");
        }
        if (weights.length != values.length) {
            throw new IllegalArgumentException("Weights and values arrays must have the same length.");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must not be negative.");
        }
    }

    private static double[][] buildItemRatios(int[] weights, int[] values) {
        double[][] itemRatios = new double[weights.length][2];
        for (int i = 0; i < weights.length; i++) {
            itemRatios[i][0] = i;
            itemRatios[i][1] = values[i] / (double) weights[i];
        }
        return itemRatios;
    }
}