package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Given an array of non-negative integers, partition the array into two subsets
 * such that the absolute difference between the sums of the subsets is minimized.
 *
 * <p>Returns the minimum possible difference between the sums of the two subsets.</p>
 *
 * <p>Example 1:
 * array = {1, 6, 11, 4}
 * Subset1 = {1, 4, 6}, sum = 11
 * Subset2 = {11}, sum = 11
 * Output: 0
 *
 * <p>Example 2:
 * array = {36, 7, 46, 40}
 * Subset1 = {7, 46}, sum = 53
 * Subset2 = {36, 40}, sum = 76
 * Output: 23
 */
public final class MinimumSumPartition {

    private MinimumSumPartition() {
        // Utility class; prevent instantiation
    }

    public static int minimumSumPartition(final int[] array) {
        validateInput(array);

        final int totalSum = Arrays.stream(array).sum();
        final int target = totalSum / 2;

        // dp[s] == true if a subset with sum 's' is achievable
        final boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        int bestAchievable = 0;

        for (int value : array) {
            for (int sum = target; sum >= value; sum--) {
                if (dp[sum - value]) {
                    dp[sum] = true;
                    bestAchievable = Math.max(bestAchievable, sum);
                }
            }
        }

        // Let S1 be bestAchievable, S2 = totalSum - S1
        // Difference = |S2 - S1| = totalSum - 2 * S1
        return totalSum - 2 * bestAchievable;
    }

    private static void validateInput(final int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Input array must not be null.");
        }
        if (Arrays.stream(array).anyMatch(a -> a < 0)) {
            throw new IllegalArgumentException("Input array must not contain negative numbers.");
        }
    }
}