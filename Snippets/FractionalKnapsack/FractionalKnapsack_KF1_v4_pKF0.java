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
        int itemCount = weights.length;
        Item[] items = createItems(weights, values);

        Arrays.sort(items, Comparator.comparingDouble(item -> item.valuePerWeight));

        return calculateMaxValue(weights, values, capacity, items, itemCount);
    }

    private static Item[] createItems(int[] weights, int[] values) {
        int itemCount = weights.length;
        Item[] items = new Item[itemCount];

        for (int i = 0; i < itemCount; i++) {
            double valueToWeightRatio = values[i] / (double) weights[i];
            items[i] = new Item(i, valueToWeightRatio);
        }

        return items;
    }

    private static int calculateMaxValue(int[] weights, int[] values, int capacity, Item[] items, int itemCount) {
        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int i = itemCount - 1; i >= 0; i--) {
            Item currentItem = items[i];
            int currentWeight = weights[currentItem.index];
            int currentValue = values[currentItem.index];

            if (remainingCapacity >= currentWeight) {
                totalValue += currentValue;
                remainingCapacity -= currentWeight;
            } else {
                totalValue += (int) (currentItem.valuePerWeight * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}