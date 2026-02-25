package com.thealgorithms.dynamicprogramming;

/**
 * Computes the maximum sum of non-adjacent elements in an array.
 *
 * <p>Provides two approaches:
 * <ul>
 *     <li>Approach 1: O(n) time, O(n) space (DP array)</li>
 *     <li>Approach 2: O(n) time, O(1) space (space-optimized)</li>
 * </ul>
 *
 * <p>Reference:
 * https://takeuforward.org/data-structure/maximum-sum-of-non-adjacent-elements-dp-5/
 */
final class MaximumSumOfNonAdjacentElements {

    private MaximumSumOfNonAdjacentElements() {
        // Utility class; prevent instantiation
    }

    /**
     * Approach 1: Dynamic programming with an auxiliary array.
     *
     * <p>Definition:
     * <pre>
     * dp[i] = maximum sum of non-adjacent elements in subarray arr[0..i]
     * </pre>
     *
     * <p>Time Complexity: O(n)  
     * Space Complexity: O(n)
     *
     * @param arr input array of integers
     * @return maximum sum of non-adjacent elements
     */
    public static int getMaxSumApproach1(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        int[] dp = new int[n];

        dp[0] = arr[0];

        for (int i = 1; i < n; i++) {
            int notTake = dp[i - 1]; // skip current element
            int take = arr[i];       // take current element
            if (i > 1) {
                take += dp[i - 2];   // add best sum excluding adjacent element
            }
            dp[i] = Math.max(take, notTake);
        }

        return dp[n - 1];
    }

    /**
     * Approach 2: Space-optimized dynamic programming using two variables.
     *
     * <p>Definitions:
     * <pre>
     * prev1 = maximum sum up to previous index (i - 1)
     * prev2 = maximum sum up to index before previous (i - 2)
     * </pre>
     *
     * <p>Time Complexity: O(n)  
     * Space Complexity: O(1)
     *
     * @param arr input array of integers
     * @return maximum sum of non-adjacent elements
     */
    public static int getMaxSumApproach2(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }

        int prev1 = arr[0]; // dp[i - 1]
        int prev2 = 0;      // dp[i - 2]

        for (int i = 1; i < arr.length; i++) {
            int notTake = prev1; // skip current element
            int take = arr[i];   // take current element
            if (i > 1) {
                take += prev2;   // add best sum excluding adjacent element
            }

            int current = Math.max(take, notTake);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}