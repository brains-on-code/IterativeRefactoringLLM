package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * 0-1 Knapsack problem solved using top-down dynamic programming with memoization.
 */
public class KnapsackSolver {

    int getMaxValue(int capacity, int[] itemWeights, int[] itemValues, int itemCount) {
        int[][] memoTable = new int[itemCount + 1][capacity + 1];

        for (int[] row : memoTable) {
            Arrays.fill(row, -1);
        }

        return getMaxValueRecursive(capacity, itemWeights, itemValues, itemCount, memoTable);
    }

    int getMaxValueRecursive(
            int remainingCapacity,
            int[] itemWeights,
            int[] itemValues,
            int itemIndex,
            int[][] memoTable
    ) {
        if (itemIndex == 0 || remainingCapacity == 0) {
            return 0;
        }

        if (memoTable[itemIndex][remainingCapacity] != -1) {
            return memoTable[itemIndex][remainingCapacity];
        }

        int currentWeight = itemWeights[itemIndex - 1];
        int currentValue = itemValues[itemIndex - 1];

        if (currentWeight > remainingCapacity) {
            memoTable[itemIndex][remainingCapacity] =
                    getMaxValueRecursive(
                            remainingCapacity,
                            itemWeights,
                            itemValues,
                            itemIndex - 1,
                            memoTable
                    );
        } else {
            int valueIfIncluded =
                    currentValue
                            + getMaxValueRecursive(
                                    remainingCapacity - currentWeight,
                                    itemWeights,
                                    itemValues,
                                    itemIndex - 1,
                                    memoTable
                            );

            int valueIfExcluded =
                    getMaxValueRecursive(
                            remainingCapacity,
                            itemWeights,
                            itemValues,
                            itemIndex - 1,
                            memoTable
                    );

            memoTable[itemIndex][remainingCapacity] =
                    Math.max(valueIfIncluded, valueIfExcluded);
        }

        return memoTable[itemIndex][remainingCapacity];
    }
}