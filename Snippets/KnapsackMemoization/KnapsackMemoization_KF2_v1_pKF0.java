package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public class KnapsackMemoization {

    public int knapSack(int capacity, int[] weights, int[] profits, int numOfItems) {
        int[][] dpTable = new int[numOfItems + 1][capacity + 1];
        for (int[] row : dpTable) {
            Arrays.fill(row, -1);
        }
        return solveKnapsackRecursive(capacity, weights, profits, numOfItems, dpTable);
    }

    private int solveKnapsackRecursive(
            int capacity,
            int[] weights,
            int[] profits,
            int itemIndex,
            int[][] dpTable
    ) {
        if (itemIndex == 0 || capacity == 0) {
            return 0;
        }

        if (dpTable[itemIndex][capacity] != -1) {
            return dpTable[itemIndex][capacity];
        }

        int currentItemWeight = weights[itemIndex - 1];
        int currentItemProfit = profits[itemIndex - 1];

        if (currentItemWeight > capacity) {
            dpTable[itemIndex][capacity] =
                    solveKnapsackRecursive(capacity, weights, profits, itemIndex - 1, dpTable);
        } else {
            int includeCurrentItem =
                    currentItemProfit
                            + solveKnapsackRecursive(
                                    capacity - currentItemWeight,
                                    weights,
                                    profits,
                                    itemIndex - 1,
                                    dpTable);

            int excludeCurrentItem =
                    solveKnapsackRecursive(capacity, weights, profits, itemIndex - 1, dpTable);

            dpTable[itemIndex][capacity] = Math.max(includeCurrentItem, excludeCurrentItem);
        }

        return dpTable[itemIndex][capacity];
    }
}