package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

/**
 * References: https://en.wikipedia.org/wiki/Streaming_algorithm
 *
 * Computes the maximum sum of any subarray of fixed size {@code k} consisting only
 * of distinct elements. The subarray is considered over a stream (array) of integers.
 */
public final class MaximumSumOfDistinctSubarraysWithLengthK {

    private MaximumSumOfDistinctSubarraysWithLengthK() {
        // Utility class; prevent instantiation
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
        Set<Integer> windowElements = new HashSet<>();

        // Build the initial window [0, k)
        for (int i = 0; i < k; i++) {
            currentSum += nums[i];
            windowElements.add(nums[i]);
        }

        if (windowElements.size() == k) {
            maxSum = currentSum;
        }

        // Slide the window one position at a time: [start, start + k)
        for (int start = 1; start <= nums.length - k; start++) {
            int outgoing = nums[start - 1];
            int incoming = nums[start + k - 1];

            currentSum = currentSum - outgoing + incoming;

            boolean outgoingStillInWindow = false;
            int checkIndex = start;

            // Check if the outgoing element still appears in the new window
            while (checkIndex < start + k) {
                if (nums[checkIndex] == outgoing) {
                    outgoingStillInWindow = true;
                    break;
                }
                checkIndex++;
            }

            if (!outgoingStillInWindow) {
                windowElements.remove(outgoing);
            }

            windowElements.add(incoming);

            if (windowElements.size() == k && currentSum > maxSum) {
                maxSum = currentSum;
            }
        }

        return maxSum;
    }
}