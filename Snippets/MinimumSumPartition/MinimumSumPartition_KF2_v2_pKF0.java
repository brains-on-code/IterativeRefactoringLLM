package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public final class MinimumSumPartition {

    private MinimumSumPartition() {
        // Utility class; prevent instantiation
    }

    private static void validateInput(final int[] numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("Input array must not be null.");
        }
        if (Arrays.stream(numbers).anyMatch(n -> n < 0)) {
            throw new IllegalArgumentException("Input array should not contain negative numbers.");
        }
    }

    public static int minimumSumPartition(final int[] numbers) {
        validateInput(numbers);

        final int totalSum = Arrays.stream(numbers).sum();
        final int targetSum = totalSum / 2;

        final boolean[] canAchieveSum = new boolean[targetSum + 1];
        canAchieveSum[0] = true;

        int closestAchievableSum = 0;

        for (int number : numbers) {
            for (int currentSum = targetSum; currentSum >= number; currentSum--) {
                if (canAchieveSum[currentSum - number]) {
                    canAchieveSum[currentSum] = true;
                    closestAchievableSum = Math.max(closestAchievableSum, currentSum);
                }
            }
        }

        return totalSum - 2 * closestAchievableSum;
    }
}