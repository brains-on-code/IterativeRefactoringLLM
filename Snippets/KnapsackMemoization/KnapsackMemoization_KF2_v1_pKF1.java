package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public class KnapsackMemoization {

    int knapSack(int capacity, int[] weights, int[] values, int itemCount) {
        int[][] memo = new int[itemCount + 1][capacity + 1];

        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return solveKnapsack(capacity, weights, values, itemCount, memo);
    }

    int solveKnapsack(int remainingCapacity, int[] weights, int[] values, int itemIndex, int[][] memo) {
        if (itemIndex == 0 || remainingCapacity == 0) {
            return 0;
        }

        if (memo[itemIndex][remainingCapacity] != -1) {
            return memo[itemIndex][remainingCapacity];
        }

        int currentItemWeight = weights[itemIndex - 1];
        int currentItemValue = values[itemIndex - 1];

        if (currentItemWeight > remainingCapacity) {
            memo[itemIndex][remainingCapacity] =
                solveKnapsack(remainingCapacity, weights, values, itemIndex - 1, memo);
        } else {
            int valueWithCurrent =
                currentItemValue
                    + solveKnapsack(
                        remainingCapacity - currentItemWeight, weights, values, itemIndex - 1, memo);

            int valueWithoutCurrent =
                solveKnapsack(remainingCapacity, weights, values, itemIndex - 1, memo);

            memo[itemIndex][remainingCapacity] = Math.max(valueWithCurrent, valueWithoutCurrent);
        }

        return memo[itemIndex][remainingCapacity];
    }
}