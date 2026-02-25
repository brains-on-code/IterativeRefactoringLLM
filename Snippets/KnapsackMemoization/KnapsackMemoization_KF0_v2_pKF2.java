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
     * Computes the maximum profit for the given capacity and number of items.
     */
    int solveKnapsackRecursive(
            int capacity,
            int[] weights,
            int[] profits,
            int numOfItems,
            int[][] dpTable
    ) {
        // Base case: no items left or no remaining capacity.
        if (numOfItems == 0 || capacity == 0) {
            return 0;
        }

        // Return cached result if already computed.
        if (dpTable[numOfItems][capacity] != -1) {
            return dpTable[numOfItems][capacity];
        }

        int currentItemIndex = numOfItems - 1;
        int result;

        // If the current item is too heavy, skip it.
        if (weights[currentItemIndex] > capacity) {
            result = solveKnapsackRecursive(
                    capacity,
                    weights,
                    profits,
                    numOfItems - 1,
                    dpTable
            );
        } else {
            // Option 1: include the current item.
            int includeCurrentItem =
                    profits[currentItemIndex]
                            + solveKnapsackRecursive(
                                    capacity - weights[currentItemIndex],
                                    weights,
                                    profits,
                                    numOfItems - 1,
                                    dpTable
                            );

            // Option 2: exclude the current item.
            int excludeCurrentItem =
                    solveKnapsackRecursive(
                            capacity,
                            weights,
                            profits,
                            numOfItems - 1,
                            dpTable
                    );

            // Take the better of the two options.
            result = Math.max(includeCurrentItem, excludeCurrentItem);
        }

        dpTable[numOfItems][capacity] = result;
        return result;
    }
}