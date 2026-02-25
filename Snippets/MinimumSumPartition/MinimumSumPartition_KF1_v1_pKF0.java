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
        if (Arrays.stream(numbers).anyMatch(a -> a < 0)) {
            throw new IllegalArgumentException("Input array should not contain negative number(s).");
        }
    }

    public static int method2(final int[] numbers) {
        validateNonNegative(numbers);

        int totalSum = Arrays.stream(numbers).sum();
        int target = totalSum / 2;

        boolean[] possible = new boolean[target + 1];
        possible[0] = true;

        int bestAchievable = 0;

        for (int num : numbers) {
            for (int currentSum = target; currentSum > 0; currentSum--) {
                if (num <= currentSum) {
                    possible[currentSum] = possible[currentSum] || possible[currentSum - num];
                }
                if (possible[currentSum]) {
                    bestAchievable = Math.max(bestAchievable, currentSum);
                }
            }
        }

        return totalSum - (2 * bestAchievable);
    }
}