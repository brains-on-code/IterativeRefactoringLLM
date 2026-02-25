package com.thealgorithms.dynamicprogramming;

final class MaximumSumOfNonAdjacentElements {

    private MaximumSumOfNonAdjacentElements() {
    }

    public static int getMaxSumApproach1(int[] values) {
        if (values.length == 0) {
            return 0;
        }

        int n = values.length;
        int[] maxSumAtIndex = new int[n];

        maxSumAtIndex[0] = values[0];

        for (int i = 1; i < n; i++) {
            int maxWithoutCurrent = maxSumAtIndex[i - 1];

            int maxWithCurrent = values[i];
            if (i > 1) {
                maxWithCurrent += maxSumAtIndex[i - 2];
            }

            maxSumAtIndex[i] = Math.max(maxWithCurrent, maxWithoutCurrent);
        }

        return maxSumAtIndex[n - 1];
    }

    public static int getMaxSumApproach2(int[] values) {
        if (values.length == 0) {
            return 0;
        }

        int n = values.length;

        int maxSumExcludingPrevious = 0;
        int maxSumIncludingPrevious = values[0];

        for (int i = 1; i < n; i++) {
            int maxWithoutCurrent = maxSumIncludingPrevious;

            int maxWithCurrent = values[i];
            if (i > 1) {
                maxWithCurrent += maxSumExcludingPrevious;
            }

            int currentMax = Math.max(maxWithCurrent, maxWithoutCurrent);

            maxSumExcludingPrevious = maxSumIncludingPrevious;
            maxSumIncludingPrevious = currentMax;
        }

        return maxSumIncludingPrevious;
    }
}