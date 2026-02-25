package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class MinimumSumPartition {

    private MinimumSumPartition() {
        // Utility class; prevent instantiation
    }

    private static void validateInput(final int[] array) {
        boolean hasNegative = Arrays.stream(array).anyMatch(value -> value < 0);
        if (hasNegative) {
            throw new IllegalArgumentException("Input array should not contain negative numbers.");
        }
    }

    /**
     * Computes the minimum possible difference between the sums of two subsets
     * formed from the given array.
     *
     * @param array the input array of non-negative integers
     * @return the minimum difference between the sums of two subsets
     */
    public static int minimumSumPartition(final int[] array) {
        validateInput(array);

        int totalSum = Arrays.stream(array).sum();
        int target = totalSum / 2;

        /*
         * dp[sum] is true if there exists a subset of the array whose elements
         * add up exactly to 'sum'.
         */
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        int bestAchievableSum = 0;

        for (int value : array) {
            for (int sum = target; sum >= value; sum--) {
                if (dp[sum - value]) {
                    dp[sum] = true;
                    bestAchievableSum = Math.max(bestAchievableSum, sum);
                }
            }
        }

        return totalSum - 2 * bestAchievableSum;
    }
}