package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Computes the minimum difference between the sums of two subsets of a given array.
 *
 * Example:
 *   Input:  {1, 6, 11, 4}
 *   Output: 0
 *   Explanation: Subsets {1, 4, 6} and {11} both sum to 11.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    private static void validateNonNegative(final int[] numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException("Input array must not be null.");
        }

        boolean hasNegative = Arrays.stream(numbers).anyMatch(n -> n < 0);
        if (hasNegative) {
            throw new IllegalArgumentException("Input array should not contain negative number(s).");
        }
    }

    public static int method2(final int[] numbers) {
        validateNonNegative(numbers);

        int totalSum = Arrays.stream(numbers).sum();
        int targetSum = totalSum / 2;

        boolean[] canAchieveSum = new boolean[targetSum + 1];
        canAchieveSum[0] = true;

        for (int number : numbers) {
            for (int currentSum = targetSum; currentSum >= number; currentSum--) {
                if (canAchieveSum[currentSum - number]) {
                    canAchieveSum[currentSum] = true;
                }
            }
        }

        int bestAchievableSum = 0;
        for (int sum = targetSum; sum >= 0; sum--) {
            if (canAchieveSum[sum]) {
                bestAchievableSum = sum;
                break;
            }
        }

        return totalSum - 2 * bestAchievableSum;
    }
}