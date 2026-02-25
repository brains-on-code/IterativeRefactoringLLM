package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public class KnapsackMemoization {

    /**
     * Solves the 0/1 Knapsack problem using memoization.
     *
     * @param capacity   maximum capacity of the knapsack
     * @param weights    array of item weights
     * @param profits    array of item profits/values
     * @param numOfItems number of items available
     * @return maximum profit achievable with the given capacity
     */
    int knapSack(int capacity, int[] weights, int[] profits, int numOfItems) {
        int[][] dpTable = new int[numOfItems + 1][capacity + 1];

        // Initialize memoization table with -1 to indicate uncomputed states
        for (int[] row : dpTable) {
            Arrays.fill(row, -1);
        }

        return solveKnapsackRecursive(capacity, weights, profits, numOfItems, dpTable);
    }

    /**
     * Recursive helper for the 0/1 Knapsack problem with memoization.
     *
     * @param capacity   remaining capacity of the knapsack
     * @param weights    array of item weights
     * @param profits    array of item profits/values
     * @param numOfItems number of items considered so far
     * @param dpTable    memoization table
     * @return maximum profit achievable with the given parameters
     */
    int solveKnapsackRecursive(
            int capacity,
            int[] weights,
            int[] profits,
            int numOfItems,
            int[][] dpTable
    ) {
        // Base case: no items left or no remaining capacity
        if (numOfItems == 0 || capacity == 0) {
            return 0;
        }

        // Return cached result if already computed
        if (dpTable[numOfItems][capacity] != -1) {
            return dpTable[numOfItems][capacity];
        }

        int result;
        int currentItemIndex = numOfItems - 1;

        // If current item's weight exceeds remaining capacity, skip it
        if (weights[currentItemIndex] > capacity) {
            result = solveKnapsackRecursive(
                    capacity,
                    weights,
                    profits,
                    numOfItems - 1,
                    dpTable
            );
        } else {
            // Option 1: include current item
            int includeCurrentItem = profits[currentItemIndex]
                    + solveKnapsackRecursive(
                            capacity - weights[currentItemIndex],
                            weights,
                            profits,
                            numOfItems - 1,
                            dpTable
                    );

            // Option 2: exclude current item
            int excludeCurrentItem = solveKnapsackRecursive(
                    capacity,
                    weights,
                    profits,
                    numOfItems - 1,
                    dpTable
            );

            // Take the better of the two options
            result = Math.max(includeCurrentItem, excludeCurrentItem);
        }

        dpTable[numOfItems][capacity] = result;
        return result;
    }
}