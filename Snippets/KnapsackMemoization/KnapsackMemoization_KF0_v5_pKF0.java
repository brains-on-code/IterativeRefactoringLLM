package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Recursive solution for 0-1 knapsack with memoization.
 * Uses a 2-D memoization table to avoid recalculating subproblems.
 */
public class KnapsackMemoization {

    int knapSack(int capacity, int[] weights, int[] profits, int numOfItems) {
        int[][] memo = initializeMemoTable(numOfItems, capacity);
        return solveKnapsack(capacity, weights, profits, numOfItems, memo);
    }

    private int[][] initializeMemoTable(int numOfItems, int capacity) {
        int[][] memo = new int[numOfItems + 1][capacity + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return memo;
    }

    /**
     * Returns the maximum profit using a recursive approach with memoization.
     */
    int solveKnapsack(
        int capacity,
        int[] weights,
        int[] profits,
        int itemIndex,
        int[][] memo
    ) {
        if (isBaseCase(capacity, itemIndex)) {
            return 0;
        }

        if (isMemoized(memo, itemIndex, capacity)) {
            return memo[itemIndex][capacity];
        }

        int currentWeight = weights[itemIndex - 1];
        int currentProfit = profits[itemIndex - 1];

        int bestProfit;
        if (currentWeight > capacity) {
            bestProfit = solveKnapsack(capacity, weights, profits, itemIndex - 1, memo);
        } else {
            int profitWithCurrent =
                currentProfit
                    + solveKnapsack(
                        capacity - currentWeight,
                        weights,
                        profits,
                        itemIndex - 1,
                        memo
                    );

            int profitWithoutCurrent =
                solveKnapsack(capacity, weights, profits, itemIndex - 1, memo);

            bestProfit = Math.max(profitWithCurrent, profitWithoutCurrent);
        }

        memo[itemIndex][capacity] = bestProfit;
        return bestProfit;
    }

    private boolean isBaseCase(int capacity, int itemIndex) {
        return itemIndex == 0 || capacity == 0;
    }

    private boolean isMemoized(int[][] memo, int itemIndex, int capacity) {
        return memo[itemIndex][capacity] != -1;
    }
}