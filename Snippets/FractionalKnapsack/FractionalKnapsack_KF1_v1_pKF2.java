package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Utility class for greedy algorithms.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Solves the fractional knapsack problem.
     *
     * @param weights array of item weights
     * @param values  array of item values
     * @param capacity maximum capacity of the knapsack
     * @return maximum total value achievable with the given capacity
     */
    public static int method1(int[] weights, int[] values, int capacity) {
        double[][] valuePerWeight = new double[weights.length][2];

        // Store index and value-to-weight ratio for each item
        for (int i = 0; i < weights.length; i++) {
            valuePerWeight[i][0] = i;
            valuePerWeight[i][1] = values[i] / (double) weights[i];
        }

        // Sort items by value-to-weight ratio in ascending order
        Arrays.sort(valuePerWeight, Comparator.comparingDouble(o -> o[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        // Iterate from highest ratio to lowest
        for (int i = valuePerWeight.length - 1; i >= 0; i--) {
            int index = (int) valuePerWeight[i][0];
            if (remainingCapacity >= weights[index]) {
                // Take the whole item
                totalValue += values[index];
                remainingCapacity -= weights[index];
            } else {
                // Take the fractional part of the item
                totalValue += (int) (valuePerWeight[i][1] * remainingCapacity);
                break;
            }
        }
        return totalValue;
    }
}