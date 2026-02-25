package com.thealgorithms.dynamicprogramming;

final class MaximumSumOfNonAdjacentElements {

    private MaximumSumOfNonAdjacentElements() {
        // Utility class; prevent instantiation
    }

    public static int getMaxSumApproach1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int length = arr.length;
        int[] maxSum = new int[length];

        maxSum[0] = arr[0];
        for (int i = 1; i < length; i++) {
            int includeCurrent = arr[i] + (i > 1 ? maxSum[i - 2] : 0);
            int excludeCurrent = maxSum[i - 1];
            maxSum[i] = Math.max(includeCurrent, excludeCurrent);
        }

        return maxSum[length - 1];
    }

    public static int getMaxSumApproach2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int includePrevious = arr[0];
        int excludePrevious = 0;

        for (int value : java.util.Arrays.copyOfRange(arr, 1, arr.length)) {
            int includeCurrent = value + excludePrevious;
            int excludeCurrent = includePrevious;

            int currentMax = Math.max(includeCurrent, excludeCurrent);
            excludePrevious = includePrevious;
            includePrevious = currentMax;
        }

        return includePrevious;
    }
}