package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    private static final class Item {
        final int index;
        final double valuePerWeight;

        Item(int index, double valuePerWeight) {
            this.index = index;
            this.valuePerWeight = valuePerWeight;
        }
    }

    /**
     * Computes the maximum value achievable with a given capacity using a
     * fractional knapsack-like greedy approach.
     *
     * @param weights  array of item weights
     * @param values   array of item values
     * @param capacity maximum total weight allowed
     * @return maximum total value achievable (integer approximation)
     */
    public static int method1(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        Item[] items = new Item[n];

        for (int i = 0; i < n; i++) {
            double ratio = values[i] / (double) weights[i];
            items[i] = new Item(i, ratio);
        }

        Arrays.sort(items, Comparator.comparingDouble(item -> item.valuePerWeight));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int i = n - 1; i >= 0; i--) {
            Item item = items[i];
            int weight = weights[item.index];
            int value = values[item.index];

            if (remainingCapacity >= weight) {
                totalValue += value;
                remainingCapacity -= weight;
            } else {
                totalValue += (int) (item.valuePerWeight * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}