package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

/**
 * References: https://en.wikipedia.org/wiki/Streaming_algorithm
 *
 * This model involves computing the maximum sum of subarrays of a fixed size K from a stream of integers.
 * As the stream progresses, elements from the end of the window are removed, and new elements from the stream are added.
 *
 * @author Swarga-codes (https://github.com/Swarga-codes)
 */
public final class MaximumSumOfDistinctSubarraysWithLengthK {

    private MaximumSumOfDistinctSubarraysWithLengthK() {
        // Utility class
    }

    /**
     * Finds the maximum sum of a subarray of size K consisting of distinct elements.
     *
     * @param k    The size of the subarray.
     * @param nums The array from which subarrays will be considered.
     * @return The maximum sum of any distinct-element subarray of size K. If no such subarray exists, returns 0.
     */
    public static long maximumSubarraySum(int k, int... nums) {
        if (k <= 0 || nums == null || nums.length < k) {
            return 0;
        }

        long maxSum = 0;
        long currentSum = 0;
        Set<Integer> windowElements = new HashSet<>();

        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            int currentValue = nums[right];

            left = shrinkWindowToRemoveDuplicate(nums, windowElements, left, currentValue, currentSum);

            windowElements.add(currentValue);
            currentSum += currentValue;

            while (windowSize(left, right) > k) {
                int leftValue = nums[left];
                currentSum -= leftValue;
                windowElements.remove(leftValue);
                left++;
            }

            if (windowSize(left, right) == k) {
                maxSum = Math.max(maxSum, currentSum);
            }
        }

        return maxSum;
    }

    private static int shrinkWindowToRemoveDuplicate(
        int[] nums,
        Set<Integer> windowElements,
        int left,
        int currentValue,
        long currentSum
    ) {
        while (windowElements.contains(currentValue)) {
            int leftValue = nums[left];
            windowElements.remove(leftValue);
            currentSum -= leftValue;
            left++;
        }
        return left;
    }

    private static int windowSize(int left, int right) {
        return right - left + 1;
    }
}