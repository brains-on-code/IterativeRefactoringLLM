package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The FractionalKnapsack class provides a method to solve the fractional knapsack problem
 * using a greedy algorithm approach. It allows for selecting fractions of items to maximize
 * the total value in a knapsack with a given weight capacity.
 *
 * Problem Link: https://en.wikipedia.org/wiki/Continuous_knapsack_problem
 */
public final class FractionalKnapsack {

    private FractionalKnapsack() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the maximum value that can be accommodated in a knapsack of a given capacity.
     *
     * @param weights  an array of integers representing the weights of the items
     * @param values   an array of integers representing the values of the items
     * @param capacity an integer representing the maximum weight capacity of the knapsack
     * @return the maximum value that can be obtained by including the items in the knapsack
     * @throws IllegalArgumentException if input arrays are null, of different lengths, or capacity is negative
     */
    public static int fractionalKnapsack(int[] weights, int[] values, int capacity) {
        validateInputs(weights, values, capacity);

        Item[] items = createItems(weights, values);

        Arrays.sort(items, Comparator.comparingDouble(Item::valuePerWeight).reversed());

        return computeMaxValue(items, capacity);
    }

    private static Item[] createItems(int[] weights, int[] values) {
        int itemCount = weights.length;
        Item[] items = new Item[itemCount];

        for (int i = 0; i < itemCount; i++) {
            items[i] = new Item(weights[i], values[i]);
        }

        return items;
    }

    private static int computeMaxValue(Item[] items, int capacity) {
        int totalValue = 0;
        int remainingCapacity = capacity;

        for (Item item : items) {
            if (remainingCapacity == 0) {
                break;
            }

            if (remainingCapacity >= item.weight) {
                totalValue += item.value;
                remainingCapacity -= item.weight;
            } else {
                totalValue += (int) (item.valuePerWeight() * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }

    private static void validateInputs(int[] weights, int[] values, int capacity) {
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

    private static final class Item {
        private final int weight;
        private final int value;

        private Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }

        private double valuePerWeight() {
            return (double) value / weight;
        }
    }
}