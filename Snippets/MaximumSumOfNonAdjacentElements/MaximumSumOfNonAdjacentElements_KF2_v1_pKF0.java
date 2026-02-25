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
        int[] dp = new int[n];

        dp[0] = arr[0];
        for (int i = 1; i < n; i++) {
            int includeCurrent = arr[i];
            if (i > 1) {
                includeCurrent += dp[i - 2];
            }

            int excludeCurrent = dp[i - 1];
            dp[i] = Math.max(includeCurrent, excludeCurrent);
        }

        return dp[n - 1];
    }

    public static int getMaxSumApproach2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int prevInclude = arr[0];
        int prevExclude = 0;

        for (int i = 1; i < arr.length; i++) {
            int includeCurrent = arr[i] + prevExclude;
            int excludeCurrent = prevInclude;

            int current = Math.max(includeCurrent, excludeCurrent);
            prevExclude = prevInclude;
            prevInclude = current;
        }

        return prevInclude;
    }
}