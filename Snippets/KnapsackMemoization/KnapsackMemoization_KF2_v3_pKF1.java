package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public class KnapsackMemoization {

    int knapSack(int capacity, int[] weights, int[] values, int itemCount) {
        int[][] dp = new int[itemCount + 1][capacity + 1];

        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        return computeMaxValue(capacity, weights, values, itemCount, dp);
    }

    int computeMaxValue(
            int remainingCapacity,
            int[] weights,
            int[] values,
            int itemIndex,
            int[][] dp) {

        if (itemIndex == 0 || remainingCapacity == 0) {
            return 0;
        }

        if (dp[itemIndex][remainingCapacity] != -1) {
            return dp[itemIndex][remainingCapacity];
        }

        int weight = weights[itemIndex - 1];
        int value = values[itemIndex - 1];

        if (weight > remainingCapacity) {
            dp[itemIndex][remainingCapacity] =
                computeMaxValue(remainingCapacity, weights, values, itemIndex - 1, dp);
        } else {
            int includeValue =
                value
                    + computeMaxValue(
                        remainingCapacity - weight,
                        weights,
                        values,
                        itemIndex - 1,
                        dp);

            int excludeValue =
                computeMaxValue(
                    remainingCapacity,
                    weights,
                    values,
                    itemIndex - 1,
                    dp);

            dp[itemIndex][remainingCapacity] = Math.max(includeValue, excludeValue);
        }

        return dp[itemIndex][remainingCapacity];
    }
}