package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * 0-1 Knapsack using top-down dynamic programming (recursion + memoization).
 *
 * <p>dpTable[i][w] stores the maximum profit achievable using the first {@code i}
 * items with a knapsack capacity of {@code w}.
 */
public class KnapsackMemoization {

    int knapSack(int capacity, int[] weights, int[] profits, int numOfItems) {
        int[][] dpTable = new int[numOfItems + 1][capacity + 1];
        for (int[] row : dpTable) {
            Arrays.fill(row, -1);
        }
        return solveKnapsackRecursive(capacity, weights, profits, numOfItems, dpTable);
    }

    /**
     * Returns the maximum profit for the given capacity and number of items.
     */
    int solveKnapsackRecursive(
            int capacity,
            int[] weights,
            int[] profits,
            int numOfItems,
            int[][] dpTable
    ) {
        if (numOfItems == 0 || capacity == 0) {
            return 0;
        }

        if (dpTable[numOfItems][capacity] != -1) {
            return dpTable[numOfItems][capacity];
        }

        int currentItemIndex = numOfItems - 1;
        int result;

        if (weights[currentItemIndex] > capacity) {
            result = solveKnapsackRecursive(
                    capacity,
                    weights,
                    profits,
                    numOfItems - 1,
                    dpTable
            );
        } else {
            int includeCurrentItem =
                    profits[currentItemIndex]
                            + solveKnapsackRecursive(
                                    capacity - weights[currentItemIndex],
                                    weights,
                                    profits,
                                    numOfItems - 1,
                                    dpTable
                            );

            int excludeCurrentItem =
                    solveKnapsackRecursive(
                            capacity,
                            weights,
                            profits,
                            numOfItems - 1,
                            dpTable
                    );

            result = Math.max(includeCurrentItem, excludeCurrentItem);
        }

        dpTable[numOfItems][capacity] = result;
        return result;
    }
}