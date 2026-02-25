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

    private static void validateInput(final int[] numbers) {
        if (Arrays.stream(numbers).anyMatch(number -> number < 0)) {
            throw new IllegalArgumentException("Input array should not contain negative number(s).");
        }
    }

    public static int minimumSumPartition(final int[] numbers) {
        validateInput(numbers);

        int totalSum = Arrays.stream(numbers).sum();
        int targetSum = totalSum / 2;

        boolean[] isSumAchievable = new boolean[targetSum + 1];
        isSumAchievable[0] = true; // Base case: sum 0 is always achievable (empty subset)

        int bestSubsetSum = 0;

        for (int number : numbers) {
            for (int currentSum = targetSum; currentSum > 0; currentSum--) {
                if (number <= currentSum) {
                    isSumAchievable[currentSum] =
                        isSumAchievable[currentSum] || isSumAchievable[currentSum - number];
                }
                if (isSumAchievable[currentSum]) {
                    bestSubsetSum = Math.max(bestSubsetSum, currentSum);
                }
            }
        }

        // Difference in sum = (Total sum - bestSubsetSum) - bestSubsetSum
        return totalSum - (2 * bestSubsetSum);
    }
}