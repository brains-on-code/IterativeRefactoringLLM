package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class FractionalKnapsack {

    private FractionalKnapsack() {
        // Prevent instantiation
    }

    /**
     * Solves the fractional knapsack problem using a greedy strategy.
     *
     * @param weights  array of item weights
     * @param values   array of item values
     * @param capacity maximum weight capacity of the knapsack
     * @return maximum total value achievable (as an integer)
     */
    public static int fractionalKnapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;

        // itemsByRatio[i][0] = original index, itemsByRatio[i][1] = value-to-weight ratio
        double[][] itemsByRatio = new double[n][2];

        for (int i = 0; i < n; i++) {
            itemsByRatio[i][0] = i;
            itemsByRatio[i][1] = values[i] / (double) weights[i];
        }

        // Sort items by value-to-weight ratio (ascending)
        Arrays.sort(itemsByRatio, Comparator.comparingDouble(item -> item[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        // Process items from highest ratio to lowest
        for (int i = n - 1; i >= 0 && remainingCapacity > 0; i--) {
            int index = (int) itemsByRatio[i][0];
            int weight = weights[index];
            int value = values[index];
            double ratio = itemsByRatio[i][1];

            if (remainingCapacity >= weight) {
                // Take the entire item
                totalValue += value;
                remainingCapacity -= weight;
            } else {
                // Take the fraction of the item that fits
                totalValue += (int) (ratio * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}