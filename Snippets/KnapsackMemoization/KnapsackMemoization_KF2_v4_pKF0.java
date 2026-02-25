package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public class KnapsackMemoization {

    public int knapSack(int capacity, int[] weights, int[] profits, int numOfItems) {
        int[][] memo = createMemoTable(numOfItems, capacity);
        return getMaxProfit(capacity, weights, profits, numOfItems, memo);
    }

    private int[][] createMemoTable(int numOfItems, int capacity) {
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

        int currentItemIndex = itemIndex - 1;
        int currentWeight = weights[currentItemIndex];
        int currentProfit = profits[currentItemIndex];

        if (currentWeight > remainingCapacity) {
            memo[itemIndex][remainingCapacity] =
                    getMaxProfit(remainingCapacity, weights, profits, itemIndex - 1, memo);
            return memo[itemIndex][remainingCapacity];
        }

        int profitWithItem =
                currentProfit
                        + getMaxProfit(
                                remainingCapacity - currentWeight,
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