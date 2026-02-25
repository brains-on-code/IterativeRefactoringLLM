package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * 0/1 Knapsack problem solved using top-down dynamic programming with memoization.
 */
public class Class1 {

    /**
     * Computes the maximum value that can be put in a knapsack of given capacity.
     *
     * @param capacity  the maximum weight capacity of the knapsack
     * @param weights   array of item weights
     * @param values    array of item values
     * @param itemCount number of items
     * @return maximum total value achievable within the given capacity
     */
    int solveKnapsack(int capacity, int[] weights, int[] values, int itemCount) {
        int[][] memo = new int[itemCount + 1][capacity + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return knapsackRecursive(capacity, weights, values, itemCount, memo);
    }

    /**
     * Helper method implementing the recursive knapsack with memoization.
     */
    int knapsackRecursive(int capacity, int[] weights, int[] values, int itemIndex, int[][] memo) {
        if (itemIndex == 0 || capacity == 0) {
            return 0;
        }

        if (memo[itemIndex][capacity] != -1) {
            return memo[itemIndex][capacity];
        }

        int currentItemIndex = itemIndex - 1;
        int currentItemWeight = weights[currentItemIndex];
        int currentItemValue = values[currentItemIndex];

        if (currentItemWeight > capacity) {
            memo[itemIndex][capacity] =
                knapsackRecursive(capacity, weights, values, itemIndex - 1, memo);
        } else {
            int includeItem =
                currentItemValue
                    + knapsackRecursive(
                        capacity - currentItemWeight, weights, values, itemIndex - 1, memo);

            int excludeItem =
                knapsackRecursive(capacity, weights, values, itemIndex - 1, memo);

            memo[itemIndex][capacity] = Math.max(includeItem, excludeItem);
        }

        return memo[itemIndex][capacity];
    }
}