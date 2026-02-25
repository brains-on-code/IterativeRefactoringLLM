package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

public final class MaximumSumOfDistinctSubarraysWithLengthK {

    private MaximumSumOfDistinctSubarraysWithLengthK() {
        // Prevent instantiation
    }

    /**
     * Returns the maximum sum of any contiguous subarray of length {@code k}
     * whose elements are all distinct. Returns 0 if no such subarray exists
     * or if the input is invalid.
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

            // Remove elements from the left until the current element is unique in the window
            while (windowElements.contains(current)) {
                int leftValue = nums[left++];
                windowElements.remove(leftValue);
                currentSum -= leftValue;
            }

            windowElements.add(current);
            currentSum += current;

            // Maintain window size at most k
            while (right - left + 1 > k) {
                int leftValue = nums[left++];
                windowElements.remove(leftValue);
                currentSum -= leftValue;
            }

            // Update maximum sum when window size is exactly k
            if (right - left + 1 == k) {
                maxSum = Math.max(maxSum, currentSum);
            }
        }

        return maxSum;
    }
}