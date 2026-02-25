package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/*
Given an array of non-negative integers, partition the array into two subsets such that
the difference in the sum of elements of both subsets is minimized.
Return the minimum difference in sum of these subsets you can achieve.

Input:  array[] = {1, 6, 11, 4}
Output: 0
Explanation:
Subset1 = {1, 4, 6}, sum of Subset1 = 11
Subset2 = {11}, sum of Subset2 = 11

Input:  array[] = {36, 7, 46, 40}
Output: 23
Explanation:
Subset1 = {7, 46} ;  sum of Subset1 = 53
Subset2 = {36, 40} ; sum of Subset2  = 76
 */
public final class MinimumSumPartition {

    private MinimumSumPartition() {
    }

    private static void validateInput(final int[] values) {
        if (Arrays.stream(values).anyMatch(value -> value < 0)) {
            throw new IllegalArgumentException("Input array should not contain negative number(s).");
        }
    }

    public static int minimumSumPartition(final int[] values) {
        validateInput(values);

        int totalSum = Arrays.stream(values).sum();
        int halfSum = totalSum / 2;

        boolean[] achievableSums = new boolean[halfSum + 1];
        achievableSums[0] = true; // Base case: sum 0 is always achievable (empty subset)

        int closestAchievableSum = 0;

        for (int value : values) {
            for (int currentSum = halfSum; currentSum > 0; currentSum--) {
                if (value <= currentSum) {
                    achievableSums[currentSum] =
                        achievableSums[currentSum] || achievableSums[currentSum - value];
                }
                if (achievableSums[currentSum]) {
                    closestAchievableSum = Math.max(closestAchievableSum, currentSum);
                }
            }
        }

        // Difference in sum = (Total sum - closestAchievableSum) - closestAchievableSum
        return totalSum - (2 * closestAchievableSum);
    }
}