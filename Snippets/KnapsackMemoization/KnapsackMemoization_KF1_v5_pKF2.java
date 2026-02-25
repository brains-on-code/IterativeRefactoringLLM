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

        // Fill memo table with -1 to mark states as uncomputed
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
        if (n == 0 || capacity == 0) {
            return 0;
        }

        if (memo[n][capacity] != -1) {
            return memo[n][capacity];
        }

        int currentItemIndex = n - 1;
        int currentItemWeight = weights[currentItemIndex];
        int currentItemValue = values[currentItemIndex];

        if (currentItemWeight > capacity) {
            memo[n][capacity] = getMaxValueRecursive(capacity, weights, values, n - 1, memo);
            return memo[n][capacity];
        }

        int includeCurrent =
            currentItemValue
                + getMaxValueRecursive(
                    capacity - currentItemWeight, weights, values, n - 1, memo);

        int excludeCurrent = getMaxValueRecursive(capacity, weights, values, n - 1, memo);

        memo[n][capacity] = Math.max(includeCurrent, excludeCurrent);
        return memo[n][capacity];
    }
}