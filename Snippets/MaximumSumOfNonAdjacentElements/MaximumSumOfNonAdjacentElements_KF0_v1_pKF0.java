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
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        if (n == 1) {
            return arr[0];
        }

        int[] dp = new int[n];
        dp[0] = arr[0];
        dp[1] = Math.max(arr[0], arr[1]);

        for (int i = 2; i < n; i++) {
            int take = arr[i] + dp[i - 2];
            int notTake = dp[i - 1];
            dp[i] = Math.max(take, notTake);
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
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        if (n == 1) {
            return arr[0];
        }

        int prev2 = 0;
        int prev1 = arr[0];

        for (int i = 1; i < n; i++) {
            int take = arr[i] + (i > 1 ? prev2 : 0);
            int notTake = prev1;
            int current = Math.max(take, notTake);

            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}