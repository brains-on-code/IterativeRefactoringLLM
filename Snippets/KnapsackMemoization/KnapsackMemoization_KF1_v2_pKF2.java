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
public class Class1 {

    /**
     * Computes the maximum value that can be obtained with the given capacity.
     *
     * @param capacity the maximum weight capacity of the knapsack
     * @param weights  array of item weights
     * @param values   array of item values
     * @param n        number of items
     * @return maximum total value achievable
     */
    int method1(int capacity, int[] weights, int[] values, int n) {
        int[][] memo = new int[n + 1][capacity + 1];

        // Initialize memo table with -1 to indicate uncomputed states
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return method2(capacity, weights, values, n, memo);
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
    int method2(int capacity, int[] weights, int[] values, int n, int[][] memo) {
        // Base case: no items left or no remaining capacity
        if (n == 0 || capacity == 0) {
            return 0;
        }

        // Return already computed value
        if (memo[n][capacity] != -1) {
            return memo[n][capacity];
        }

        // If weight of the nth item is more than current capacity, skip it
        if (weights[n - 1] > capacity) {
            memo[n][capacity] = method2(capacity, weights, values, n - 1, memo);
        } else {
            int include =
                values[n - 1] + method2(capacity - weights[n - 1], weights, values, n - 1, memo);
            int exclude = method2(capacity, weights, values, n - 1, memo);

            // Take the better of including or excluding the item
            memo[n][capacity] = Math.max(include, exclude);
        }

        return memo[n][capacity];
    }
}