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
        // Prevent instantiation of utility class.
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

        double[][] itemRatios = buildItemRatios(weights, values);

        // Sort items by value-to-weight ratio in ascending order.
        Arrays.sort(itemRatios, Comparator.comparingDouble(item -> item[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        // Traverse from highest ratio to lowest while there is remaining capacity.
        for (int i = itemRatios.length - 1; i >= 0 && remainingCapacity > 0; i--) {
            int itemIndex = (int) itemRatios[i][0];
            double valueToWeightRatio = itemRatios[i][1];

            int itemWeight = weights[itemIndex];
            int itemValue = values[itemIndex];

            if (remainingCapacity >= itemWeight) {
                // Take the whole item.
                totalValue += itemValue;
                remainingCapacity -= itemWeight;
            } else {
                // Take the fractional part that fits.
                totalValue += (int) (valueToWeightRatio * remainingCapacity);
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

    /**
     * Builds an array where each element contains:
     * index 0: original index of the item
     * index 1: value-to-weight ratio of the item
     */
    private static double[][] buildItemRatios(int[] weights, int[] values) {
        double[][] itemRatios = new double[weights.length][2];
        for (int i = 0; i < weights.length; i++) {
            itemRatios[i][0] = i;
            itemRatios[i][1] = values[i] / (double) weights[i];
        }
        return itemRatios;
    }
}