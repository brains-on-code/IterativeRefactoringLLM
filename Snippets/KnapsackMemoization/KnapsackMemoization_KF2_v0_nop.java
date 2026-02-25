package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;


public class KnapsackMemoization {

    int knapSack(int capacity, int[] weights, int[] profits, int numOfItems) {

        int[][] dpTable = new int[numOfItems + 1][capacity + 1];

        for (int[] table : dpTable) {
            Arrays.fill(table, -1);
        }

        return solveKnapsackRecursive(capacity, weights, profits, numOfItems, dpTable);
    }

    int solveKnapsackRecursive(int capacity, int[] weights, int[] profits, int numOfItems, int[][] dpTable) {
        if (numOfItems == 0 || capacity == 0) {
            return 0;
        }

        if (dpTable[numOfItems][capacity] != -1) {
            return dpTable[numOfItems][capacity];
        }

        if (weights[numOfItems - 1] > capacity) {
            dpTable[numOfItems][capacity] = solveKnapsackRecursive(capacity, weights, profits, numOfItems - 1, dpTable);
        } else {
            final int includeCurrentItem = profits[numOfItems - 1] + solveKnapsackRecursive(capacity - weights[numOfItems - 1], weights, profits, numOfItems - 1, dpTable);

            final int excludeCurrentItem = solveKnapsackRecursive(capacity, weights, profits, numOfItems - 1, dpTable);

            dpTable[numOfItems][capacity] = Math.max(includeCurrentItem, excludeCurrentItem);
        }
        return dpTable[numOfItems][capacity];
    }
}
