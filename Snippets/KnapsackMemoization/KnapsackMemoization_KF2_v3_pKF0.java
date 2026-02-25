package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public class KnapsackMemoization {

    public int knapSack(int capacity, int[] weights, int[] profits, int numOfItems) {
        int[][] memo = initializeMemo(numOfItems, capacity);
        return getMaxProfit(capacity, weights, profits, numOfItems, memo);
    }

    private int[][] initializeMemo(int numOfItems, int capacity) {
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

        int weight = weights[itemIndex - 1];
        int profit = profits[itemIndex - 1];

        if (weight > remainingCapacity) {
            memo[itemIndex][remainingCapacity] =
                    getMaxProfit(remainingCapacity, weights, profits, itemIndex - 1, memo);
            return memo[itemIndex][remainingCapacity];
        }

        int profitWithItem =
                profit + getMaxProfit(
                        remainingCapacity - weight,
                        weights,
                        profits,
                        itemIndex - 1,
                        memo
                );

        int profitWithoutItem =
                getMaxProfit(remainingCapacity, weights, profits, itemIndex - 1, memo);

        memo[itemIndex][remainingCapacity] = Math.max(profitWithItem, profitWithoutItem);
        return memo[itemIndex][remainingCapacity];
    }
}