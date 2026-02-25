package com.thealgorithms.dynamicprogramming;

final class MaximumSumOfNonAdjacentElements {

    private MaximumSumOfNonAdjacentElements() {
    }

    public static int getMaxSumApproach1(int[] values) {
        if (values.length == 0) {
            return 0;
        }

        int length = values.length;
        int[] maxSumUpToIndex = new int[length];

        maxSumUpToIndex[0] = values[0];

        for (int index = 1; index < length; index++) {
            int maxSumExcludingCurrent = maxSumUpToIndex[index - 1];

            int maxSumIncludingCurrent = values[index];
            if (index > 1) {
                maxSumIncludingCurrent += maxSumUpToIndex[index - 2];
            }

            maxSumUpToIndex[index] = Math.max(maxSumIncludingCurrent, maxSumExcludingCurrent);
        }

        return maxSumUpToIndex[length - 1];
    }

    public static int getMaxSumApproach2(int[] values) {
        if (values.length == 0) {
            return 0;
        }

        int length = values.length;

        int maxSumExcludingPrevious = 0;
        int maxSumIncludingPrevious = values[0];

        for (int index = 1; index < length; index++) {
            int maxSumExcludingCurrent = maxSumIncludingPrevious;

            int maxSumIncludingCurrent = values[index];
            if (index > 1) {
                maxSumIncludingCurrent += maxSumExcludingPrevious;
            }

            int maxSumAtCurrent = Math.max(maxSumIncludingCurrent, maxSumExcludingCurrent);

            maxSumExcludingPrevious = maxSumIncludingPrevious;
            maxSumIncludingPrevious = maxSumAtCurrent;
        }

        return maxSumIncludingPrevious;
    }
}