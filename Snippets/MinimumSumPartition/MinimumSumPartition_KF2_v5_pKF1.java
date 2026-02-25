package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

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

        boolean[] achievableSums = new boolean[targetSum + 1];
        achievableSums[0] = true;

        int closestAchievableSum = 0;

        for (int number : numbers) {
            for (int currentSum = targetSum; currentSum > 0; currentSum--) {
                if (number <= currentSum) {
                    achievableSums[currentSum] =
                        achievableSums[currentSum] || achievableSums[currentSum - number];
                }
                if (achievableSums[currentSum]) {
                    closestAchievableSum = Math.max(closestAchievableSum, currentSum);
                }
            }
        }

        return totalSum - (2 * closestAchievableSum);
    }
}