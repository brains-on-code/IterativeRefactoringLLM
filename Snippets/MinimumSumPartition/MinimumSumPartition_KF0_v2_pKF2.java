package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Given an array of non-negative integers, partition it into two subsets such that
 * the absolute difference between the sums of the two subsets is minimized.
 *
 * <p>Returns the minimum possible difference between the sums of these two subsets.</p>
 *
 * <p>Example 1:
 * <pre>
 * Input:  array = {1, 6, 11, 4}
 * Output: 0
 * Explanation:
 *   Subset1 = {1, 4, 6}, sum = 11
 *   Subset2 = {11},      sum = 11
 *   Difference = |11 - 11| = 0
 * </pre>
 *
 * Example 2:
 * <pre>
 * Input:  array = {36, 7, 46, 40}
 * Output: 23
 * Explanation:
 *   Subset1 = {7, 46},   sum = 53
 *   Subset2 = {36, 40},  sum = 76
 *   Difference = |76 - 53| = 23
 * </pre>
 * </p>
 */
public final class MinimumSumPartition {

    private MinimumSumPartition() {
        // Prevent instantiation.
    }

    /**
     * Ensures that the input array contains only non-negative numbers.
     *
     * @param array the input array
     * @throws IllegalArgumentException if any element is negative
     */
    private static void validateInput(final int[] array) {
        if (Arrays.stream(array).anyMatch(a -> a < 0)) {
            throw new IllegalArgumentException("Input array should not contain negative number(s).");
        }
    }

    /**
     * Computes the minimum possible difference between the sums of two subsets
     * into which the given array can be partitioned.
     *
     * <p>Approach:
     * <ul>
     *   <li>Compute the total sum S of the array.</li>
     *   <li>Use dynamic programming to find all achievable subset sums up to S/2.</li>
     *   <li>Let s be the achievable sum closest to S/2.</li>
     *   <li>The minimum difference is S - 2s.</li>
     * </ul>
     * </p>
     *
     * @param array an array of non-negative integers
     * @return the minimum difference between the sums of two subsets
     * @throws IllegalArgumentException if the array contains negative numbers
     */
    public static int minimumSumPartition(final int[] array) {
        validateInput(array);

        int totalSum = Arrays.stream(array).sum();
        int target = totalSum / 2;

        // dp[s] is true if there exists a subset with sum exactly s.
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        int closestPartitionSum = 0;

        for (int value : array) {
            // Traverse backwards to avoid reusing the same element multiple times.
            for (int s = target; s >= value; s--) {
                if (dp[s - value]) {
                    dp[s] = true;
                    if (s > closestPartitionSum) {
                        closestPartitionSum = s;
                    }
                }
            }
        }

        // If one subset has sum s, the other has sum (totalSum - s).
        // The difference is |(totalSum - s) - s| = totalSum - 2 * s (since s <= totalSum / 2).
        return totalSum - 2 * closestPartitionSum;
    }
}