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
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
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
    public static int method1(int capacity, int[] weights, int[] values) {
        int n = weights.length;
        // Each entry: [index, valuePerWeight]
        double[][] items = new double[n][2];

        for (int i = 0; i < n; i++) {
            items[i][0] = i;
            items[i][1] = (double) values[i] / weights[i];
        }

        // Sort items by value per weight in descending order
        Arrays.sort(items, (a, b) -> Double.compare(b[1], a[1]));

        int maxValue = 0;

        for (int i = 0; i < n; i++) {
            int index = (int) items[i][0];
            if (capacity >= weights[index]) {
                maxValue += values[index];
                capacity -= weights[index];
            } else {
                maxValue += (int) (items[i][1] * capacity);
                break;
            }
        }

        return maxValue;
    }
}