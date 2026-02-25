package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

public final class MaximumSumOfDistinctSubarraysWithLengthK {

    private MaximumSumOfDistinctSubarraysWithLengthK() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the maximum sum of any contiguous subarray of length {@code k}
     * whose elements are all distinct.
     * <p>
     * Returns 0 if:
     * <ul>
     *   <li>{@code k <= 0}</li>
     *   <li>{@code nums} is {@code null}</li>
     *   <li>{@code nums.length < k}</li>
     *   <li>no subarray of length {@code k} with all distinct elements exists</li>
     * </ul>
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
        Set<Integer> windowElements = new HashSet<>();

        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            int current = nums[right];

            // Ensure all elements in the window are distinct by shrinking from the left
            while (windowElements.contains(current)) {
                int leftValue = nums[left++];
                windowElements.remove(leftValue);
                currentSum -= leftValue;
            }

            windowElements.add(current);
            currentSum += current;

            // Ensure the window size does not exceed k
            while (right - left + 1 > k) {
                int leftValue = nums[left++];
                windowElements.remove(leftValue);
                currentSum -= leftValue;
            }

            // When the window size is exactly k, update the maximum sum
            if (right - left + 1 == k) {
                maxSum = Math.max(maxSum, currentSum);
            }
        }

        return maxSum;
    }
}