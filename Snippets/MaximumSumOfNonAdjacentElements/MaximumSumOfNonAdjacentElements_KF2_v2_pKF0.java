package com.thealgorithms.dynamicprogramming;

final class MaximumSumOfNonAdjacentElements {

    private MaximumSumOfNonAdjacentElements() {
        // Utility class; prevent instantiation
    }

    public static int getMaxSumApproach1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        int[] maxSum = new int[n];

        maxSum[0] = arr[0];
        for (int i = 1; i < n; i++) {
            int includeCurrent = arr[i] + (i > 1 ? maxSum[i - 2] : 0);
            int excludeCurrent = maxSum[i - 1];
            maxSum[i] = Math.max(includeCurrent, excludeCurrent);
        }

        return maxSum[n - 1];
    }

    public static int getMaxSumApproach2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int includePrev = arr[0];
        int excludePrev = 0;

        for (int i = 1; i < arr.length; i++) {
            int includeCurrent = arr[i] + excludePrev;
            int excludeCurrent = includePrev;

            int currentMax = Math.max(includeCurrent, excludeCurrent);
            excludePrev = includePrev;
            includePrev = currentMax;
        }

        return includePrev;
    }
}