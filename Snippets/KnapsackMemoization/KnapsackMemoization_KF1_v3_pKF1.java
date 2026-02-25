package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * 0-1 Knapsack problem solved using top-down dynamic programming with memoization.
 */
public class KnapsackSolver {

    int getMaxValue(int capacity, int[] weights, int[] values, int itemCount) {
        int[][] dpCache = new int[itemCount + 1][capacity + 1];

        for (int[] row : dpCache) {
            Arrays.fill(row, -1);
        }

        return getMaxValueRecursive(capacity, weights, values, itemCount, dpCache);
    }

    int getMaxValueRecursive(
            int remainingCapacity,
            int[] weights,
            int[] values,
            int itemIndex,
            int[][] dpCache
    ) {
        if (itemIndex == 0 || remainingCapacity == 0) {
            return 0;
        }

        if (dpCache[itemIndex][remainingCapacity] != -1) {
            return dpCache[itemIndex][remainingCapacity];
        }

        int itemWeight = weights[itemIndex - 1];
        int itemValue = values[itemIndex - 1];

        if (itemWeight > remainingCapacity) {
            dpCache[itemIndex][remainingCapacity] =
                    getMaxValueRecursive(remainingCapacity, weights, values, itemIndex - 1, dpCache);
        } else {
            int valueWithItem =
                    itemValue
                            + getMaxValueRecursive(
                                    remainingCapacity - itemWeight,
                                    weights,
                                    values,
                                    itemIndex - 1,
                                    dpCache);

            int valueWithoutItem =
                    getMaxValueRecursive(
                            remainingCapacity,
                            weights,
                            values,
                            itemIndex - 1,
                            dpCache);

            dpCache[itemIndex][remainingCapacity] =
                    Math.max(valueWithItem, valueWithoutItem);
        }

        return dpCache[itemIndex][remainingCapacity];
    }
}