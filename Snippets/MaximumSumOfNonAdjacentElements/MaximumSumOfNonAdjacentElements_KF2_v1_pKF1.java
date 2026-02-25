package com.thealgorithms.dynamicprogramming;

final class MaximumSumOfNonAdjacentElements {

    private MaximumSumOfNonAdjacentElements() {
    }

    public static int getMaxSumApproach1(int[] numbers) {
        if (numbers.length == 0) {
            return 0;
        }

        int length = numbers.length;
        int[] maxSumUpToIndex = new int[length];

        maxSumUpToIndex[0] = numbers[0];

        for (int index = 1; index < length; index++) {
            int excludeCurrent = maxSumUpToIndex[index - 1];

            int includeCurrent = numbers[index];
            if (index > 1) {
                includeCurrent += maxSumUpToIndex[index - 2];
            }

            maxSumUpToIndex[index] = Math.max(includeCurrent, excludeCurrent);
        }

        return maxSumUpToIndex[length - 1];
    }

    public static int getMaxSumApproach2(int[] numbers) {
        if (numbers.length == 0) {
            return 0;
        }

        int length = numbers.length;

        int maxSumExcludingPrevious = 0;
        int maxSumIncludingPrevious = numbers[0];

        for (int index = 1; index < length; index++) {
            int excludeCurrent = maxSumIncludingPrevious;

            int includeCurrent = numbers[index];
            if (index > 1) {
                includeCurrent += maxSumExcludingPrevious;
            }

            int currentMax = Math.max(includeCurrent, excludeCurrent);

            maxSumExcludingPrevious = maxSumIncludingPrevious;
            maxSumIncludingPrevious = currentMax;
        }

        return maxSumIncludingPrevious;
    }
}