package com.thealgorithms.others;

import java.util.HashMap;
import java.util.Map;

/**
 * Computes the maximum sum of any subarray of fixed size {@code k} consisting only
 * of distinct elements.
 *
 * <p>Reference: https://en.wikipedia.org/wiki/Streaming_algorithm
 */
public final class MaximumSumOfDistinctSubarraysWithLengthK {

    private MaximumSumOfDistinctSubarraysWithLengthK() {
        // Utility class; prevent instantiation.
    }

    /**
     * Returns the maximum sum of any subarray of length {@code k} whose elements are all distinct.
     *
     * @param k    the required subarray length
     * @param nums the input array
     * @return the maximum sum of a distinct-element subarray of length {@code k},
     *         or {@code 0} if no such subarray exists
     */
    public static long maximumSubarraySum(int k, int... nums) {
        if (k <= 0 || nums == null || nums.length < k) {
            return 0;
        }

        long maxSum = 0;
        long currentSum = 0;

        // Frequency of each element in the current window.
        Map<Integer, Integer> freq = new HashMap<>();

        // Initialize the first window [0, k).
        for (int i = 0; i < k; i++) {
            currentSum += nums[i];
            freq.merge(nums[i], 1, Integer::sum);
        }

        if (freq.size() == k) {
            maxSum = currentSum;
        }

        // Slide the window: [start, start + k).
        for (int start = 1; start <= nums.length - k; start++) {
            int outgoing = nums[start - 1];
            int incoming = nums[start + k - 1];

            currentSum = currentSum - outgoing + incoming;

            // Update frequency for outgoing element.
            freq.merge(outgoing, -1, Integer::sum);
            if (freq.get(outgoing) == 0) {
                freq.remove(outgoing);
            }

            // Update frequency for incoming element.
            freq.merge(incoming, 1, Integer::sum);

            if (freq.size() == k && currentSum > maxSum) {
                maxSum = currentSum;
            }
        }

        return maxSum;
    }
}