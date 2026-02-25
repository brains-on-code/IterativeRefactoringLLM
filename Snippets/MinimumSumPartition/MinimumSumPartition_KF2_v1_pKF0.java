package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class MinimumSumPartition {

    private MinimumSumPartition() {
        // Utility class; prevent instantiation
    }

    private static void validateInput(final int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Input array must not be null.");
        }
        if (Arrays.stream(array).anyMatch(a -> a < 0)) {
            throw new IllegalArgumentException("Input array should not contain negative numbers.");
        }
    }

    public static int minimumSumPartition(final int[] array) {
        validateInput(array);

        final int totalSum = Arrays.stream(array).sum();
        final int target = totalSum / 2;

        boolean[] canAchieve = new boolean[target + 1];
        canAchieve[0] = true;

        int bestAchievable = 0;

        for (int value : array) {
            for (int currentSum = target; currentSum >= value; currentSum--) {
                if (canAchieve[currentSum - value]) {
                    canAchieve[currentSum] = true;
                    bestAchievable = Math.max(bestAchievable, currentSum);
                }
            }
        }

        return totalSum - 2 * bestAchievable;
    }
}