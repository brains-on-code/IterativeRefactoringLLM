package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

/**
 * Greedy algorithm to maximize value given a weight capacity, where items can be
 * taken fractionally (fractional knapsack).
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    private static class Item {
        final int index;
        final double valuePerWeight;

        Item(int index, int value, int weight) {
            this.index = index;
            this.valuePerWeight = (double) value / weight;
        }
    }

    /**
     * Computes the maximum total value that can be obtained with a given capacity,
     * where each item can be taken fully or fractionally.
     *
     * @param capacity the maximum total weight that can be taken
     * @param weights  the weights of the items
     * @param values   the values of the items
     * @return the maximum total value achievable
     */
    public static int method1(int capacity, int[] weights, int[] values) {
        int itemCount = weights.length;
        Item[] items = buildItems(weights, values);

        Arrays.sort(items, (first, second) -> Double.compare(second.valuePerWeight, first.valuePerWeight));

        int totalValue = 0;

        for (int i = 0; i < itemCount && capacity > 0; i++) {
            Item currentItem = items[i];
            int weight = weights[currentItem.index];
            int value = values[currentItem.index];

            if (capacity >= weight) {
                totalValue += value;
                capacity -= weight;
            } else {
                totalValue += (int) (currentItem.valuePerWeight * capacity);
                break;
            }
        }

        return totalValue;
    }

    private static Item[] buildItems(int[] weights, int[] values) {
        int itemCount = weights.length;
        Item[] items = new Item[itemCount];

        for (int i = 0; i < itemCount; i++) {
            items[i] = new Item(i, values[i], weights[i]);
        }

        return items;
    }
}