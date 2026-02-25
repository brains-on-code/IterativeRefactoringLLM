package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Recursive Solution for 0-1 knapsack with memoization.
 */
public class KnapsackMemoization {

    int knapSack(int capacity, int[] weights, int[] values, int itemCount) {
        int[][] memoTable = new int[itemCount + 1][capacity + 1];

        for (int[] row : memoTable) {
            Arrays.fill(row, -1);
        }

        return computeMaxValue(capacity, weights, values, itemCount, memoTable);
    }

    int computeMaxValue(
            int remainingCapacity,
            int[] weights,
            int[] values,
            int currentItemIndex,
            int[][] memoTable
    ) {
        if (currentItemIndex == 0 || remainingCapacity == 0) {
            return 0;
        }

        if (memoTable[currentItemIndex][remainingCapacity] != -1) {
            return memoTable[currentItemIndex][remainingCapacity];
        }

        int currentItemWeight = weights[currentItemIndex - 1];
        int currentItemValue = values[currentItemIndex - 1];

        if (currentItemWeight > remainingCapacity) {
            memoTable[currentItemIndex][remainingCapacity] =
                computeMaxValue(
                    remainingCapacity,
                    weights,
                    values,
                    currentItemIndex - 1,
                    memoTable
                );
        } else {
            int valueIfIncluded =
                currentItemValue
                    + computeMaxValue(
                        remainingCapacity - currentItemWeight,
                        weights,
                        values,
                        currentItemIndex - 1,
                        memoTable
                    );

            int valueIfExcluded =
                computeMaxValue(
                    remainingCapacity,
                    weights,
                    values,
                    currentItemIndex - 1,
                    memoTable
                );

            memoTable[currentItemIndex][remainingCapacity] =
                Math.max(valueIfIncluded, valueIfExcluded);
        }

        return memoTable[currentItemIndex][remainingCapacity];
    }
}