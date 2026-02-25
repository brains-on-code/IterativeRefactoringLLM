package com.thealgorithms.dynamicprogramming;

/**
 * Utility class to find the maximum sum of non-adjacent elements in an array.
 * Contains two approaches:
 * 1) O(n) time, O(n) space using a DP array.
 * 2) O(n) time, O(1) space using two variables.
 */
final class MaximumSumOfNonAdjacentElements {

    private MaximumSumOfNonAdjacentElements() {
        // Prevent instantiation
    }

    private static boolean isNullOrEmpty(int[] arr) {
        return arr == null || arr.length == 0;
    }

    private static boolean hasSingleElement(int[] arr) {
        return arr.length == 1;
    }

    /**
     * Approach 1: Uses a dynamic programming array to store the maximum sum at
     * each index.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     *
     * @param arr the input array of integers
     * @return the maximum sum of non-adjacent elements
     */
    public static int getMaxSumApproach1(int[] arr) {
        if (isNullOrEmpty(arr)) {
            return 0;
        }

        if (hasSingleElement(arr)) {
            return arr[0];
        }

        int n = arr.length;
        int[] dp = new int[n];

        dp[0] = arr[0];
        dp[1] = Math.max(arr[0], arr[1]);

        for (int i = 2; i < n; i++) {
            int includeCurrent = arr[i] + dp[i - 2];
            int excludeCurrent = dp[i - 1];
            dp[i] = Math.max(includeCurrent, excludeCurrent);
        }

        return dp[n - 1];
    }

    /**
     * Approach 2: Optimized space complexity approach using two variables instead
     * of an array.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     *
     * @param arr the input array of integers
     * @return the maximum sum of non-adjacent elements
     */
    public static int getMaxSumApproach2(int[] arr) {
        if (isNullOrEmpty(arr)) {
            return 0;
        }

        if (hasSingleElement(arr)) {
            return arr[0];
        }

        int prevTwo = 0;
        int prevOne = arr[0];

        for (int i = 1; i < arr.length; i++) {
            int includeCurrent = arr[i] + (i > 1 ? prevTwo : 0);
            int excludeCurrent = prevOne;
            int current = Math.max(includeCurrent, excludeCurrent);

            prevTwo = prevOne;
            prevOne = current;
        }

        return prevOne;
    }
}