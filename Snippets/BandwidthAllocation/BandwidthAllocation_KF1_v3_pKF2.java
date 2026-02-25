package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

/**
 * Greedy algorithm for the fractional knapsack problem.
 *
 * Given:
 * - A knapsack with a maximum capacity.
 * - Arrays of weights and corresponding values.
 *
 * The goal is to maximize the total value in the knapsack, where items can be
 * taken fractionally.
 */
public final class FractionalKnapsack {

    private FractionalKnapsack() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the maximum value that can be obtained in a knapsack of given
     * capacity using the fractional knapsack strategy.
     *
     * @param capacity the maximum weight capacity of the knapsack
     * @param weights  array of item weights
     * @param values   array of item values
     * @return the maximum total value achievable
     */
    public static int getMaxValue(int capacity, int[] weights, int[] values) {
        int n = weights.length;

        // items[i][0] = original index of the item
        // items[i][1] = value-to-weight ratio of the item
        double[][] items = new double[n][2];

        for (int i = 0; i < n; i++) {
            items[i][0] = i;
            items[i][1] = (double) values[i] / weights[i];
        }

        // Sort items by value-to-weight ratio in descending order
        Arrays.sort(items, (a, b) -> Double.compare(b[1], a[1]));

        int maxValue = 0;

        for (int i = 0; i < n && capacity > 0; i++) {
            int index = (int) items[i][0];
            int weight = weights[index];
            int value = values[index];
            double ratio = items[i][1];

            if (capacity >= weight) {
                // Take the whole item
                maxValue += value;
                capacity -= weight;
            } else {
                // Take the fractional part of the item that fits
                maxValue += (int) (ratio * capacity);
                break;
            }
        }

        return maxValue;
    }
}