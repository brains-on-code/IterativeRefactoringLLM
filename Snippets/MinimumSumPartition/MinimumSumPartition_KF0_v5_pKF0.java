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

        final boolean[] achievableSums = computeAchievableSums(array, target);
        final int bestSubsetSum = findBestSubsetSum(achievableSums, target);

        return totalSum - 2 * bestSubsetSum;
    }

    private static boolean[] computeAchievableSums(final int[] array, final int target) {
        final boolean[] achievableSums = new boolean[target + 1];
        achievableSums[0] = true;

        for (int value : array) {
            updateAchievableSumsForValue(achievableSums, value, target);
        }

        return achievableSums;
    }

    private static void updateAchievableSumsForValue(
        final boolean[] achievableSums,
        final int value,
        final int target
    ) {
        for (int sum = target; sum >= value; sum--) {
            if (achievableSums[sum - value]) {
                achievableSums[sum] = true;
            }
        }
    }

    private static int findBestSubsetSum(final boolean[] achievableSums, final int target) {
        for (int sum = target; sum >= 0; sum--) {
            if (achievableSums[sum]) {
                return sum;
            }
        }
        return 0;
    }

    private static void validateInput(final int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Input array must not be null.");
        }

        final boolean hasNegative = Arrays.stream(array).anyMatch(value -> value < 0);
        if (hasNegative) {
            throw new IllegalArgumentException("Input array must not contain negative numbers.");
        }
    }
}