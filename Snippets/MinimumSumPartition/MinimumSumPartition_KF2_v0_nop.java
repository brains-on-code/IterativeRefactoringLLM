package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;


public final class MinimumSumPartition {
    private MinimumSumPartition() {
    }

    private static void throwIfInvalidInput(final int[] array) {
        if (Arrays.stream(array).anyMatch(a -> a < 0)) {
            throw new IllegalArgumentException("Input array should not contain negative number(s).");
        }
    }

    public static int minimumSumPartition(final int[] array) {
        throwIfInvalidInput(array);
        int sum = Arrays.stream(array).sum();
        boolean[] dp = new boolean[sum / 2 + 1];
        dp[0] = true;

        int closestPartitionSum = 0;

        for (int i = 0; i < array.length; i++) {
            for (int j = sum / 2; j > 0; j--) {
                if (array[i] <= j) {
                    dp[j] = dp[j] || dp[j - array[i]];
                }
                if (dp[j]) {
                    closestPartitionSum = Math.max(closestPartitionSum, j);
                }
            }
        }

        return sum - (2 * closestPartitionSum);
    }
}
