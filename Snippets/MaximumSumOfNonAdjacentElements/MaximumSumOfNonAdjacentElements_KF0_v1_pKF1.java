package com.thealgorithms.dynamicprogramming;

/**
 * Class to find the maximum sum of non-adjacent elements in an array. This
 * class contains two approaches: one with O(n) space complexity and another
 * with O(1) space optimization. For more information, refer to
 * https://takeuforward.org/data-structure/maximum-sum-of-non-adjacent-elements-dp-5/
 */
final class MaximumSumOfNonAdjacentElements {

    private MaximumSumOfNonAdjacentElements() {
    }

    /**
     * Approach 1: Uses a dynamic programming array to store the maximum sum at
     * each index. Time Complexity: O(n) - where n is the length of the input
     * array. Space Complexity: O(n) - due to the additional dp array.
     *
     * @param numbers The input array of integers.
     * @return The maximum sum of non-adjacent elements.
     */
    public static int getMaxSumApproach1(int[] numbers) {
        if (numbers.length == 0) {
            return 0;
        }

        int length = numbers.length;
        int[] maxSumAtIndex = new int[length];

        maxSumAtIndex[0] = numbers[0];

        for (int index = 1; index < length; index++) {
            int excludeCurrent = maxSumAtIndex[index - 1];

            int includeCurrent = numbers[index];
            if (index > 1) {
                includeCurrent += maxSumAtIndex[index - 2];
            }

            maxSumAtIndex[index] = Math.max(includeCurrent, excludeCurrent);
        }

        return maxSumAtIndex[length - 1];
    }

    /**
     * Approach 2: Optimized space complexity approach using two variables instead
     * of an array. Time Complexity: O(n) - where n is the length of the input
     * array. Space Complexity: O(1) - as it only uses constant space for two
     * variables.
     *
     * @param numbers The input array of integers.
     * @return The maximum sum of non-adjacent elements.
     */
    public static int getMaxSumApproach2(int[] numbers) {
        if (numbers.length == 0) {
            return 0;
        }

        int length = numbers.length;

        int maxSumIncludingPrevious = numbers[0];
        int maxSumExcludingPrevious = 0;

        for (int index = 1; index < length; index++) {
            int excludeCurrent = maxSumIncludingPrevious;

            int includeCurrent = numbers[index];
            if (index > 1) {
                includeCurrent += maxSumExcludingPrevious;
            }

            int currentMaxSum = Math.max(includeCurrent, excludeCurrent);

            maxSumExcludingPrevious = maxSumIncludingPrevious;
            maxSumIncludingPrevious = currentMaxSum;
        }

        return maxSumIncludingPrevious;
    }
}