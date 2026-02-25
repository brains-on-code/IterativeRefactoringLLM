package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/*
Example:
Input:  values = {1, 6, 11, 4}
Output: 0
Explanation:
Subset1 = {1, 4, 6}, subset sum = 11
Subset2 = {11}, subset sum = 11
Difference = |11 - 11| = 0

Input:  values = {36, 7, 46, 40}
Output: 23
Explanation:
Subset1 = {7, 46}, subset sum = 53
Subset2 = {36, 40}, subset sum = 76
Difference = |53 - 76| = 23
 */
public final class MinimumSubsetSumDifference {

    private MinimumSubsetSumDifference() {
    }

    private static void validateNonNegative(final int[] values) {
        if (Arrays.stream(values).anyMatch(value -> value < 0)) {
            throw new IllegalArgumentException("Input array should not contain negative number(s).");
        }
    }

    public static int findMinimumSubsetSumDifference(final int[] values) {
        validateNonNegative(values);

        int totalSum = Arrays.stream(values).sum();
        int targetSum = totalSum / 2;

        boolean[] achievableSums = new boolean[targetSum + 1];
        achievableSums[0] = true;

        int bestSubsetSum = 0;

        for (int value : values) {
            for (int currentSum = targetSum; currentSum > 0; currentSum--) {
                if (value <= currentSum) {
                    achievableSums[currentSum] =
                        achievableSums[currentSum] || achievableSums[currentSum - value];
                }
                if (achievableSums[currentSum]) {
                    bestSubsetSum = Math.max(bestSubsetSum, currentSum);
                }
            }
        }

        return totalSum - (2 * bestSubsetSum);
    }
}