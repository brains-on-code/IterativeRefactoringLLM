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
        if (Arrays.stream(numbers).anyMatch(n -> n < 0)) {
            throw new IllegalArgumentException("Input array should not contain negative number(s).");
        }
    }

    public static int method2(final int[] numbers) {
        validateNonNegative(numbers);

        int totalSum = Arrays.stream(numbers).sum();
        int target = totalSum / 2;

        boolean[] isSumPossible = new boolean[target + 1];
        isSumPossible[0] = true;

        int bestAchievableSum = 0;

        for (int number : numbers) {
            for (int currentSum = target; currentSum >= number; currentSum--) {
                if (isSumPossible[currentSum - number]) {
                    isSumPossible[currentSum] = true;
                    bestAchievableSum = Math.max(bestAchievableSum, currentSum);
                }
            }
        }

        return totalSum - 2 * bestAchievableSum;
    }
}