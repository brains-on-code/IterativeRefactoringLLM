package com.thealgorithms.dynamicprogramming;

final class MaximumSumOfNonAdjacentElements {

    private MaximumSumOfNonAdjacentElements() {
    }

    public static int getMaxSumApproach1(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int length = nums.length;
        int[] maxSumUpToIndex = new int[length];

        maxSumUpToIndex[0] = nums[0];

        for (int index = 1; index < length; index++) {
            int maxSumExcludingCurrent = maxSumUpToIndex[index - 1];

            int maxSumIncludingCurrent = nums[index];
            if (index > 1) {
                maxSumIncludingCurrent += maxSumUpToIndex[index - 2];
            }

            maxSumUpToIndex[index] = Math.max(maxSumIncludingCurrent, maxSumExcludingCurrent);
        }

        return maxSumUpToIndex[length - 1];
    }

    public static int getMaxSumApproach2(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int length = nums.length;

        int maxSumExcludingPrevious = 0;
        int maxSumIncludingPrevious = nums[0];

        for (int index = 1; index < length; index++) {
            int maxSumExcludingCurrent = maxSumIncludingPrevious;

            int maxSumIncludingCurrent = nums[index];
            if (index > 1) {
                maxSumIncludingCurrent += maxSumExcludingPrevious;
            }

            int currentMaxSum = Math.max(maxSumIncludingCurrent, maxSumExcludingCurrent);

            maxSumExcludingPrevious = maxSumIncludingPrevious;
            maxSumIncludingPrevious = currentMaxSum;
        }

        return maxSumIncludingPrevious;
    }
}