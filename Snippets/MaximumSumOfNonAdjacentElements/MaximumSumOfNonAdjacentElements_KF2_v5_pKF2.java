package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the maximum sum of non-adjacent elements
 * in an integer array using dynamic programming.
 */
final class MaximumSumOfNonAdjacentElements {

    private MaximumSumOfNonAdjacentElements() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the maximum sum of non-adjacent elements using a DP array.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param arr the input array
     * @return the maximum sum of non-adjacent elements
     */
    public static int getMaxSumApproach1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        int[] dp = new int[n];

        // Base case: only the first element is available.
        dp[0] = arr[0];

        for (int i = 1; i < n; i++) {
            int notTake = dp[i - 1]; // Exclude current element.
            int take = arr[i];       // Include current element.

            if (i > 1) {
                take += dp[i - 2];   // Add best sum up to i - 2.
            }

            dp[i] = Math.max(take, notTake);
        }

        return dp[n - 1];
    }

    /**
     * Computes the maximum sum of non-adjacent elements using
     * space-optimized dynamic programming.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param arr the input array
     * @return the maximum sum of non-adjacent elements
     */
    public static int getMaxSumApproach2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;

        // prev1: best sum up to index i - 1
        // prev2: best sum up to index i - 2
        int prev1 = arr[0];
        int prev2 = 0;

        for (int i = 1; i < n; i++) {
            int notTake = prev1; // Exclude current element.
            int take = arr[i];   // Include current element.

            if (i > 1) {
                take += prev2;   // Add best sum up to i - 2.
            }

            int current = Math.max(take, notTake);

            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}