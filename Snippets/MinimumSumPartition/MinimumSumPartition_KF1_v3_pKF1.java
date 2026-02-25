package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/*
Example:
Input:  values = {1, 6, 11, 4}
Output: 0
Explanation:
Subset1 = {1, 4, 6}, subset sum = 11
Subset2 = {11}, subset sum = 11
Difference = |11 - 11| = 0

Input:  values = {36, 7, 46, 40}
Output: 23
Explanation:
Subset1 = {7, 46}, subset sum = 53
Subset2 = {36, 40}, subset sum = 76
Difference = |53 - 76| = 23
 */
public final class MinimumSubsetSumDifference {

    private MinimumSubsetSumDifference() {
    }

    private static void validateNonNegative(final int[] numbers) {
        if (Arrays.stream(numbers).anyMatch(number -> number < 0)) {
            throw new IllegalArgumentException("Input array should not contain negative number(s).");
        }
    }

    public static int findMinimumSubsetSumDifference(final int[] numbers) {
        validateNonNegative(numbers);

        int totalSum = Arrays.stream(numbers).sum();
        int halfTotalSum = totalSum / 2;

        boolean[] isSubsetSumAchievable = new boolean[halfTotalSum + 1];
        isSubsetSumAchievable[0] = true;

        int closestAchievableSum = 0;

        for (int number : numbers) {
            for (int currentSum = halfTotalSum; currentSum > 0; currentSum--) {
                if (number <= currentSum) {
                    isSubsetSumAchievable[currentSum] =
                        isSubsetSumAchievable[currentSum] || isSubsetSumAchievable[currentSum - number];
                }
                if (isSubsetSumAchievable[currentSum]) {
                    closestAchievableSum = Math.max(closestAchievableSum, currentSum);
                }
            }
        }

        return totalSum - (2 * closestAchievableSum);
    }
}