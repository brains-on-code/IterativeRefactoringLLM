package com.thealgorithms.dynamicprogramming;

final class MaximumSumOfNonAdjacentElements {

    private MaximumSumOfNonAdjacentElements() {
    }

    public static int getMaxSumApproach1(int[] numbers) {
        if (numbers.length == 0) {
            return 0;
        }

        int n = numbers.length;
        int[] maxSumAtIndex = new int[n];

        maxSumAtIndex[0] = numbers[0];

        for (int i = 1; i < n; i++) {
            int maxSumWithoutCurrent = maxSumAtIndex[i - 1];

            int maxSumWithCurrent = numbers[i];
            if (i > 1) {
                maxSumWithCurrent += maxSumAtIndex[i - 2];
            }

            maxSumAtIndex[i] = Math.max(maxSumWithCurrent, maxSumWithoutCurrent);
        }

        return maxSumAtIndex[n - 1];
    }

    public static int getMaxSumApproach2(int[] numbers) {
        if (numbers.length == 0) {
            return 0;
        }

        int n = numbers.length;

        int maxSumExcludingPrevious = 0;
        int maxSumIncludingPrevious = numbers[0];

        for (int i = 1; i < n; i++) {
            int maxSumExcludingCurrent = maxSumIncludingPrevious;

            int maxSumWithCurrent = numbers[i];
            if (i > 1) {
                maxSumWithCurrent += maxSumExcludingPrevious;
            }

            int currentMaxSum = Math.max(maxSumWithCurrent, maxSumExcludingCurrent);

            maxSumExcludingPrevious = maxSumIncludingPrevious;
            maxSumIncludingPrevious = currentMaxSum;
        }

        return maxSumIncludingPrevious;
    }
}