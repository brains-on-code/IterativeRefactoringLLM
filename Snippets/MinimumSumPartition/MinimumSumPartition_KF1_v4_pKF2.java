package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * Utility class for partitioning an array into two subsets such that the
 * absolute difference between the sums of the subsets is minimized.
 *
 * <p>Example:
 * <pre>
 * Input:  {1, 6, 11, 4}
 * Output: 0
 * Explanation:
 *   Subset1 = {1, 4, 6}, sum = 11
 *   Subset2 = {11},      sum = 11
 *   Difference = |11 - 11| = 0
 *
 * Input:  {36, 7, 46, 40}
 * Output: 23
 * Explanation:
 *   Subset1 = {7, 46},   sum = 53
 *   Subset2 = {36, 40},  sum = 76
 *   Difference = |53 - 76| = 23
 * </pre>
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation of utility class.
    }

    /**
     * Ensures that the input array contains no negative numbers.
     *
     * @param numbers the input array
     * @throws IllegalArgumentException if any element is negative
     */
    private static void validateInput(final int[] numbers) {
        if (Arrays.stream(numbers).anyMatch(a -> a < 0)) {
            throw new IllegalArgumentException("Input array should not contain negative number(s).");
        }
    }

    /**
     * Computes the minimum possible difference between the sums of two subsets
     * formed from the given array.
     *
     * <p>Algorithm overview:
     * <ol>
     *   <li>Compute the total sum of all elements.</li>
     *   <li>Use 1D dynamic programming to determine which subset sums up to
     *       {@code totalSum / 2} are achievable.</li>
     *   <li>Let {@code bestAchievable} be the largest achievable sum not
     *       exceeding {@code totalSum / 2}.</li>
     *   <li>The minimal difference is {@code totalSum - 2 * bestAchievable}.</li>
     * </ol>
     *
     * @param numbers the input array of non-negative integers
     * @return the minimum difference between the sums of two subsets
     */
    public static int method2(final int[] numbers) {
        validateInput(numbers);

        int totalSum = Arrays.stream(numbers).sum();
        int halfSum = totalSum / 2;

        // dp[s] is true if there exists a subset with sum exactly s.
        boolean[] dp = new boolean[halfSum + 1];
        dp[0] = true;

        int bestAchievable = 0;

        for (int num : numbers) {
            for (int s = halfSum; s >= num; s--) {
                if (dp[s - num]) {
                    dp[s] = true;
                    bestAchievable = Math.max(bestAchievable, s);
                }
            }
        }

        return totalSum - 2 * bestAchievable;
    }
}