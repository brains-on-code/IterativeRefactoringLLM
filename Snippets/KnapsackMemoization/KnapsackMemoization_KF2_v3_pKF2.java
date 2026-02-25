package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public class KnapsackMemoization {

    /**
     * Solves the 0/1 Knapsack problem using top-down dynamic programming
     * with memoization.
     *
     * @param capacity   maximum capacity of the knapsack
     * @param weights    array of item weights
     * @param profits    array of item profits/values
     * @param numOfItems number of items available
     * @return maximum profit achievable with the given capacity
     */
    int knapSack(int capacity, int[] weights, int[] profits, int numOfItems) {
        int[][] memo = new int[numOfItems + 1][capacity + 1];

        // Initialize memo table with -1 to indicate uncomputed states
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return solveKnapsack(capacity, weights, profits, numOfItems, memo);
    }

    /**
     * Recursive helper for the 0/1 Knapsack problem with memoization.
     *
     * @param capacity   remaining capacity of the knapsack
     * @param weights    array of item weights
     * @param profits    array of item profits/values
     * @param numOfItems number of items still available to consider
     * @param memo       memoization table
     * @return maximum profit achievable with the given parameters
     */
    int solveKnapsack(
            int capacity,
            int[] weights,
            int[] profits,
            int numOfItems,
            int[][] memo
    ) {
        // Base case: no items left or no remaining capacity
        if (numOfItems == 0 || capacity == 0) {
            return 0;
        }

        // Return memoized result if already computed
        if (memo[numOfItems][capacity] != -1) {
            return memo[numOfItems][capacity];
        }

        int currentItemIndex = numOfItems - 1;
        int result;

        // If current item is too heavy, skip it
        if (weights[currentItemIndex] > capacity) {
            result = solveKnapsack(
                    capacity,
                    weights,
                    profits,
                    numOfItems - 1,
                    memo
            );
        } else {
            // Option 1: include current item
            int includeCurrent =
                    profits[currentItemIndex]
                            + solveKnapsack(
                                    capacity - weights[currentItemIndex],
                                    weights,
                                    profits,
                                    numOfItems - 1,
                                    memo
                            );

            // Option 2: exclude current item
            int excludeCurrent =
                    solveKnapsack(
                            capacity,
                            weights,
                            profits,
                            numOfItems - 1,
                            memo
                    );

            // Take the better of the two options
            result = Math.max(includeCurrent, excludeCurrent);
        }

        memo[numOfItems][capacity] = result;
        return result;
    }
}