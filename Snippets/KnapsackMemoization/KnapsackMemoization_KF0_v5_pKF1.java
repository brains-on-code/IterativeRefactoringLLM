package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Recursive Solution for 0-1 knapsack with memoization.
 */
public class KnapsackMemoization {

    int knapSack(int capacity, int[] weights, int[] values, int itemCount) {
        int[][] memo = new int[itemCount + 1][capacity + 1];

        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return getMaxValue(capacity, weights, values, itemCount, memo);
    }

    int getMaxValue(
            int remainingCapacity,
            int[] weights,
            int[] values,
            int itemIndex,
            int[][] memo
    ) {
        if (itemIndex == 0 || remainingCapacity == 0) {
            return 0;
        }

        if (memo[itemIndex][remainingCapacity] != -1) {
            return memo[itemIndex][remainingCapacity];
        }

        int weight = weights[itemIndex - 1];
        int value = values[itemIndex - 1];

        if (weight > remainingCapacity) {
            memo[itemIndex][remainingCapacity] =
                getMaxValue(
                    remainingCapacity,
                    weights,
                    values,
                    itemIndex - 1,
                    memo
                );
        } else {
            int includeValue =
                value
                    + getMaxValue(
                        remainingCapacity - weight,
                        weights,
                        values,
                        itemIndex - 1,
                        memo
                    );

            int excludeValue =
                getMaxValue(
                    remainingCapacity,
                    weights,
                    values,
                    itemIndex - 1,
                    memo
                );

            memo[itemIndex][remainingCapacity] =
                Math.max(includeValue, excludeValue);
        }

        return memo[itemIndex][remainingCapacity];
    }
}