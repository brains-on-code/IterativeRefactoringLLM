package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Recursive solution for 0-1 knapsack with memoization.
 * Uses a 2-D memoization table to avoid recalculating subproblems.
 */
public class KnapsackMemoization {

    int knapSack(int capacity, int[] weights, int[] profits, int numOfItems) {
        int[][] memo = createMemoTable(numOfItems, capacity);
        return solveKnapsackRecursive(capacity, weights, profits, numOfItems, memo);
    }

    private int[][] createMemoTable(int numOfItems, int capacity) {
        int[][] memo = new int[numOfItems + 1][capacity + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return memo;
    }

    /**
     * Returns the maximum profit using a recursive approach with memoization.
     */
    int solveKnapsackRecursive(
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

        int currentItemWeight = weights[itemIndex - 1];
        int currentItemProfit = profits[itemIndex - 1];

        int result;
        if (currentItemWeight > capacity) {
            result = solveKnapsackRecursive(capacity, weights, profits, itemIndex - 1, memo);
        } else {
            int includeCurrent =
                currentItemProfit
                    + solveKnapsackRecursive(
                        capacity - currentItemWeight,
                        weights,
                        profits,
                        itemIndex - 1,
                        memo
                    );

            int excludeCurrent =
                solveKnapsackRecursive(capacity, weights, profits, itemIndex - 1, memo);

            result = Math.max(includeCurrent, excludeCurrent);
        }

        memo[itemIndex][capacity] = result;
        return result;
    }

    private boolean isBaseCase(int capacity, int itemIndex) {
        return itemIndex == 0 || capacity == 0;
    }

    private boolean isMemoized(int[][] memo, int itemIndex, int capacity) {
        return memo[itemIndex][capacity] != -1;
    }
}