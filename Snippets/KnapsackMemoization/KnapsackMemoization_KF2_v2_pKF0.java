package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public class KnapsackMemoization {

    public int knapSack(int capacity, int[] weights, int[] profits, int numOfItems) {
        int[][] memo = createAndInitializeMemo(numOfItems, capacity);
        return getMaxProfit(capacity, weights, profits, numOfItems, memo);
    }

    private int[][] createAndInitializeMemo(int numOfItems, int capacity) {
        int[][] memo = new int[numOfItems + 1][capacity + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return memo;
    }

    private int getMaxProfit(
            int remainingCapacity,
            int[] weights,
            int[] profits,
            int itemIndex,
            int[][] memo
    ) {
        if (itemIndex == 0 || remainingCapacity == 0) {
            return 0;
        }

        if (memo[itemIndex][remainingCapacity] != -1) {
            return memo[itemIndex][remainingCapacity];
        }

        int currentItemWeight = weights[itemIndex - 1];
        int currentItemProfit = profits[itemIndex - 1];

        if (currentItemWeight > remainingCapacity) {
            memo[itemIndex][remainingCapacity] =
                    getMaxProfit(remainingCapacity, weights, profits, itemIndex - 1, memo);
        } else {
            int profitIncludingCurrent =
                    currentItemProfit
                            + getMaxProfit(
                                    remainingCapacity - currentItemWeight,
                                    weights,
                                    profits,
                                    itemIndex - 1,
                                    memo);

            int profitExcludingCurrent =
                    getMaxProfit(remainingCapacity, weights, profits, itemIndex - 1, memo);

            memo[itemIndex][remainingCapacity] =
                    Math.max(profitIncludingCurrent, profitExcludingCurrent);
        }

        return memo[itemIndex][remainingCapacity];
    }
}