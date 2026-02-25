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
        final double valueToWeightRatio;

        Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
            this.valueToWeightRatio = value / (double) weight;
        }
    }

    public static int fractionalKnapsack(int[] weights, int[] values, int capacity) {
        int itemCount = weights.length;
        Item[] items = new Item[itemCount];

        for (int i = 0; i < itemCount; i++) {
            items[i] = new Item(weights[i], values[i]);
        }

        Arrays.sort(items, Comparator.comparingDouble(item -> item.valueToWeightRatio));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int i = itemCount - 1; i >= 0 && remainingCapacity > 0; i--) {
            Item currentItem = items[i];

            if (remainingCapacity >= currentItem.weight) {
                totalValue += currentItem.value;
                remainingCapacity -= currentItem.weight;
            } else {
                totalValue += (int) (currentItem.valueToWeightRatio * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}