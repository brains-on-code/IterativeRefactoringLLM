package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public class KnapsackMemoization {

    public int knapSack(int capacity, int[] weights, int[] profits, int numOfItems) {
        int[][] memo = initializeMemoTable(numOfItems, capacity);
        return getMaxProfit(capacity, weights, profits, numOfItems, memo);
    }

    private int[][] initializeMemoTable(int numOfItems, int capacity) {
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
            int itemCount,
            int[][] memo
    ) {
        if (itemCount == 0 || remainingCapacity == 0) {
            return 0;
        }

        if (memo[itemCount][remainingCapacity] != -1) {
            return memo[itemCount][remainingCapacity];
        }

        int currentIndex = itemCount - 1;
        int currentWeight = weights[currentIndex];
        int currentProfit = profits[currentIndex];

        if (currentWeight > remainingCapacity) {
            memo[itemCount][remainingCapacity] =
                    getMaxProfit(remainingCapacity, weights, profits, itemCount - 1, memo);
            return memo[itemCount][remainingCapacity];
        }

        int profitIncludingCurrent =
                currentProfit
                        + getMaxProfit(
                                remainingCapacity - currentWeight,
                                weights,
                                profits,
                                itemCount - 1,
                                memo
                        );

        int profitExcludingCurrent =
                getMaxProfit(remainingCapacity, weights, profits, itemCount - 1, memo);

        memo[itemCount][remainingCapacity] =
                Math.max(profitIncludingCurrent, profitExcludingCurrent);

        return memo[itemCount][remainingCapacity];
    }
}