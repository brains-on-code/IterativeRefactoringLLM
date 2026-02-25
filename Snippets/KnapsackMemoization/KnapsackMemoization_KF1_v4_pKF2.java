package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Solves the 0/1 Knapsack problem using top-down dynamic programming with memoization.
 *
 * Given:
 * - A set of items, each with a weight and a value.
 * - A maximum capacity of the knapsack.
 *
 * Goal:
 * - Maximize the total value of items placed in the knapsack without exceeding
 *   the capacity.
 */
public class Knapsack01TopDown {

    /**
     * Computes the maximum value that can be obtained with the given capacity.
     *
     * @param capacity the maximum weight capacity of the knapsack
     * @param weights  array of item weights
     * @param values   array of item values
     * @param n        number of items
     * @return maximum total value achievable
     */
    int getMaxValue(int capacity, int[] weights, int[] values, int n) {
        int[][] memo = new int[n + 1][capacity + 1];

        // Initialize memo table with -1 to indicate uncomputed states
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return getMaxValueRecursive(capacity, weights, values, n, memo);
    }

    /**
     * Recursive helper for 0/1 knapsack using memoization.
     *
     * @param capacity remaining capacity
     * @param weights  array of item weights
     * @param values   array of item values
     * @param n        number of items considered
     * @param memo     memoization table
     * @return maximum value for the given state
     */
    int getMaxValueRecursive(int capacity, int[] weights, int[] values, int n, int[][] memo) {
        // Base case: no items left or no remaining capacity
        if (n == 0 || capacity == 0) {
            return 0;
        }

        // Return memoized result if already computed
        if (memo[n][capacity] != -1) {
            return memo[n][capacity];
        }

        int currentItemIndex = n - 1;
        int currentItemWeight = weights[currentItemIndex];
        int currentItemValue = values[currentItemIndex];

        // If the current item's weight exceeds the remaining capacity, skip it
        if (currentItemWeight > capacity) {
            memo[n][capacity] = getMaxValueRecursive(capacity, weights, values, n - 1, memo);
            return memo[n][capacity];
        }

        // Option 1: include the current item
        int include =
            currentItemValue
                + getMaxValueRecursive(
                    capacity - currentItemWeight, weights, values, n - 1, memo);

        // Option 2: exclude the current item
        int exclude = getMaxValueRecursive(capacity, weights, values, n - 1, memo);

        // Store the better of the two options
        memo[n][capacity] = Math.max(include, exclude);

        return memo[n][capacity];
    }
}