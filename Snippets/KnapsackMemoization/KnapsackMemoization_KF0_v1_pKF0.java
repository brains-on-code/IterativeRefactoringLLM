package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Recursive solution for 0-1 knapsack with memoization.
 * Uses a 2-D memoization table to avoid recalculating subproblems.
 */
public class KnapsackMemoization {

    int knapSack(int capacity, int[] weights, int[] profits, int numOfItems) {
        int[][] memo = new int[numOfItems + 1][capacity + 1];

        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return solveKnapsackRecursive(capacity, weights, profits, numOfItems, memo);
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
        if (itemIndex == 0 || capacity == 0) {
            return 0;
        }

        if (memo[itemIndex][capacity] != -1) {
            return memo[itemIndex][capacity];
        }

        int currentItemWeight = weights[itemIndex - 1];
        int currentItemProfit = profits[itemIndex - 1];

        if (currentItemWeight > capacity) {
            memo[itemIndex][capacity] =
                solveKnapsackRecursive(capacity, weights, profits, itemIndex - 1, memo);
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

            memo[itemIndex][capacity] = Math.max(includeCurrent, excludeCurrent);
        }

        return memo[itemIndex][capacity];
    }
}