package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * 0/1 Knapsack problem solved using top-down dynamic programming with memoization.
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

        // memo[i][w] will store the maximum value for the first i items and capacity w
        int[][] memo = new int[n + 1][capacity + 1];

        // initialize memo table with -1 to indicate uncomputed states
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
        // base case: no items left or no remaining capacity
        if (n == 0 || capacity == 0) {
            return 0;
        }

        // return already computed value
        if (memo[n][capacity] != -1) {
            return memo[n][capacity];
        }

        // if weight of the nth item is more than current capacity, skip it
        if (weights[n - 1] > capacity) {
            memo[n][capacity] = method2(capacity, weights, values, n - 1, memo);
        } else {
            // include the nth item
            final int include =
                values[n - 1] + method2(capacity - weights[n - 1], weights, values, n - 1, memo);

            // exclude the nth item
            final int exclude = method2(capacity, weights, values, n - 1, memo);

            // take the better of including or excluding the item
            memo[n][capacity] = Math.max(include, exclude);
        }
        return memo[n][capacity];
    }
}