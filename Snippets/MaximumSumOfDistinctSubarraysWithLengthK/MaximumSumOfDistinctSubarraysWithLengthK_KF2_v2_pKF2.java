package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

public final class MaximumSumOfDistinctSubarraysWithLengthK {

    private MaximumSumOfDistinctSubarraysWithLengthK() {
        // Prevent instantiation
    }

    /**
     * Computes the maximum sum of any contiguous subarray of length {@code k}
     * whose elements are all distinct. Returns 0 if no such subarray exists.
     *
     * @param k    required subarray length
     * @param nums input array
     * @return maximum sum of a distinct-element subarray of length {@code k}
     */
    public static long maximumSubarraySum(int k, int... nums) {
        if (k <= 0 || nums == null || nums.length < k) {
            return 0;
        }

        long maxSum = 0;
        long currentSum = 0;
        Set<Integer> window = new HashSet<>();

        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            int current = nums[right];

            // Ensure all elements in the window remain distinct
            while (window.contains(current)) {
                int leftValue = nums[left++];
                window.remove(leftValue);
                currentSum -= leftValue;
            }

            window.add(current);
            currentSum += current;

            // Maintain window size at most k
            while (right - left + 1 > k) {
                int leftValue = nums[left++];
                window.remove(leftValue);
                currentSum -= leftValue;
            }

            // Update result when window size is exactly k
            if (right - left + 1 == k) {
                maxSum = Math.max(maxSum, currentSum);
            }
        }

        return maxSum;
    }
}