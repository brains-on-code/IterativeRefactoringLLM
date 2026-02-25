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
     * Solves the fractional knapsack problem using a greedy strategy.
     *
     * @param weights  array of item weights
     * @param values   array of item values
     * @param capacity maximum capacity of the knapsack
     * @return maximum total value achievable with the given capacity
     */
    public static int method1(int[] weights, int[] values, int capacity) {
        double[][] items = new double[weights.length][2];

        // items[i][0] = original index
        // items[i][1] = value-to-weight ratio
        for (int i = 0; i < weights.length; i++) {
            items[i][0] = i;
            items[i][1] = values[i] / (double) weights[i];
        }

        // Sort items by value-to-weight ratio in ascending order
        Arrays.sort(items, Comparator.comparingDouble(item -> item[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        // Traverse from highest ratio to lowest
        for (int i = items.length - 1; i >= 0; i--) {
            int index = (int) items[i][0];

            if (remainingCapacity >= weights[index]) {
                // Take the entire item
                totalValue += values[index];
                remainingCapacity -= weights[index];
            } else {
                // Take a fraction of the item proportional to remaining capacity
                totalValue += (int) (items[i][1] * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}