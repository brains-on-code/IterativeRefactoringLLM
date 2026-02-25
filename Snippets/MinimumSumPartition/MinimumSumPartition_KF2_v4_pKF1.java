package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

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

        boolean[] isSumAchievable = new boolean[halfSum + 1];
        isSumAchievable[0] = true;

        int bestAchievableSum = 0;

        for (int value : values) {
            for (int currentSum = halfSum; currentSum > 0; currentSum--) {
                if (value <= currentSum) {
                    isSumAchievable[currentSum] =
                        isSumAchievable[currentSum] || isSumAchievable[currentSum - value];
                }
                if (isSumAchievable[currentSum]) {
                    bestAchievableSum = Math.max(bestAchievableSum, currentSum);
                }
            }
        }

        return totalSum - (2 * bestAchievableSum);
    }
}