package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class FractionalKnapsack {

    private FractionalKnapsack() {
        // Utility class; prevent instantiation
    }

    private static class Item {
        final int weight;
        final int value;
        final double ratio;

        Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
            this.ratio = value / (double) weight;
        }
    }

    public static int fractionalKnapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        Item[] items = new Item[n];

        for (int i = 0; i < n; i++) {
            items[i] = new Item(weights[i], values[i]);
        }

        Arrays.sort(items, Comparator.comparingDouble(item -> item.ratio));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int i = n - 1; i >= 0 && remainingCapacity > 0; i--) {
            Item item = items[i];

            if (remainingCapacity >= item.weight) {
                totalValue += item.value;
                remainingCapacity -= item.weight;
            } else {
                totalValue += (int) (item.ratio * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}