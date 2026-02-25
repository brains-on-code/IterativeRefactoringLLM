package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public class KnapsackMemoization {

    int knapSack(int capacity, int[] weights, int[] values, int numberOfItems) {
        int[][] memoizedValues = new int[numberOfItems + 1][capacity + 1];

        for (int[] row : memoizedValues) {
            Arrays.fill(row, -1);
        }

        return computeMaxValue(capacity, weights, values, numberOfItems, memoizedValues);
    }

    int computeMaxValue(
            int remainingCapacity,
            int[] weights,
            int[] values,
            int currentItemIndex,
            int[][] memoizedValues) {

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
                computeMaxValue(remainingCapacity, weights, values, currentItemIndex - 1, memoizedValues);
        } else {
            int valueIncludingCurrentItem =
                currentItemValue
                    + computeMaxValue(
                        remainingCapacity - currentItemWeight,
                        weights,
                        values,
                        currentItemIndex - 1,
                        memoizedValues);

            int valueExcludingCurrentItem =
                computeMaxValue(
                    remainingCapacity,
                    weights,
                    values,
                    currentItemIndex - 1,
                    memoizedValues);

            memoizedValues[currentItemIndex][remainingCapacity] =
                Math.max(valueIncludingCurrentItem, valueExcludingCurrentItem);
        }

        return memoizedValues[currentItemIndex][remainingCapacity];
    }
}