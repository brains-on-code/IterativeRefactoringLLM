package com.thealgorithms.dynamicprogramming;

final class MaximumSumOfNonAdjacentElements {

    private MaximumSumOfNonAdjacentElements() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the maximum sum of non-adjacent elements using a DP array.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public static int getMaxSumApproach1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        int[] dp = new int[n];

        // Base case: only one element
        dp[0] = arr[0];

        for (int i = 1; i < n; i++) {
            int notTake = dp[i - 1]; // Skip current element

            int take = arr[i];       // Take current element
            if (i > 1) {
                take += dp[i - 2];   // Add best up to i - 2 if it exists
            }

            dp[i] = Math.max(take, notTake);
        }

        return dp[n - 1];
    }

    /**
     * Returns the maximum sum of non-adjacent elements using
     * space-optimized dynamic programming.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public static int getMaxSumApproach2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;

        // prev1: best sum up to previous index
        // prev2: best sum up to index - 2
        int prev1 = arr[0];
        int prev2 = 0;

        for (int i = 1; i < n; i++) {
            int notTake = prev1; // Skip current element

            int take = arr[i];   // Take current element
            if (i > 1) {
                take += prev2;   // Add best up to i - 2 if it exists
            }

            int current = Math.max(take, notTake);

            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}