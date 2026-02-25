package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * 0-1 Knapsack problem solved using top-down dynamic programming with memoization.
 */
public class KnapsackSolver {

    int getMaxValue(int capacity, int[] weights, int[] values, int numberOfItems) {
        int[][] memoizedValues = new int[numberOfItems + 1][capacity + 1];

        for (int[] row : memoizedValues) {
            Arrays.fill(row, -1);
        }

        return getMaxValueRecursive(capacity, weights, values, numberOfItems, memoizedValues);
    }

    int getMaxValueRecursive(
            int remainingCapacity,
            int[] weights,
            int[] values,
            int currentItemIndex,
            int[][] memoizedValues
    ) {
        if (currentItemIndex == 0 || remainingCapacity == 0) {
            return 0;
        }

        if (memoizedValues[currentItemIndex][remainingCapacity] != -1) {
            return memoizedValues[currentItemIndex][remainingCapacity];
        }

        int currentItemWeight = weights[currentItemIndex - 1];
        int currentItemValue = values[currentItemIndex - 1];

        if (currentItemWeight > remainingCapacity) {
            memoizedValues[currentItemIndex][remainingCapacity] =
                    getMaxValueRecursive(
                            remainingCapacity,
                            weights,
                            values,
                            currentItemIndex - 1,
                            memoizedValues
                    );
        } else {
            int valueIncludingCurrentItem =
                    currentItemValue
                            + getMaxValueRecursive(
                                    remainingCapacity - currentItemWeight,
                                    weights,
                                    values,
                                    currentItemIndex - 1,
                                    memoizedValues
                            );

            int valueExcludingCurrentItem =
                    getMaxValueRecursive(
                            remainingCapacity,
                            weights,
                            values,
                            currentItemIndex - 1,
                            memoizedValues
                    );

            memoizedValues[currentItemIndex][remainingCapacity] =
                    Math.max(valueIncludingCurrentItem, valueExcludingCurrentItem);
        }

        return memoizedValues[currentItemIndex][remainingCapacity];
    }
}