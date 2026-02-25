package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

/**
 * References: https://en.wikipedia.org/wiki/Streaming_algorithm
 *
 * This model involves computing the maximum sum of subarrays of a fixed size \( K \) from a stream of integers.
 * As the stream progresses, elements from the end of the window are removed, and new elements from the stream are added.
 *
 * @author Swarga-codes (https://github.com/Swarga-codes)
 */
public final class MaximumSumOfDistinctSubarraysWithLengthK {

    private MaximumSumOfDistinctSubarraysWithLengthK() {}

    /**
     * Finds the maximum sum of a subarray of size K consisting of distinct elements.
     *
     * @param windowSize The size of the subarray.
     * @param nums The array from which subarrays will be considered.
     *
     * @return The maximum sum of any distinct-element subarray of size K. If no such subarray exists, returns 0.
     */
    public static long maximumSubarraySum(int windowSize, int... nums) {
        if (nums.length < windowSize) {
            return 0;
        }

        long maxDistinctWindowSum = 0;
        long currentWindowSum = 0;
        Set<Integer> distinctElementsInWindow = new HashSet<>();

        // Initialize the first window
        for (int i = 0; i < windowSize; i++) {
            currentWindowSum += nums[i];
            distinctElementsInWindow.add(nums[i]);
        }

        if (distinctElementsInWindow.size() == windowSize) {
            maxDistinctWindowSum = currentWindowSum;
        }

        // Slide the window across the array
        for (int windowStart = 1; windowStart <= nums.length - windowSize; windowStart++) {
            int outgoingIndex = windowStart - 1;
            int incomingIndex = windowStart + windowSize - 1;

            currentWindowSum = currentWindowSum - nums[outgoingIndex] + nums[incomingIndex];

            int scanIndex = windowStart;
            boolean isOutgoingElementStillPresent = false;

            while (scanIndex < windowStart + windowSize && distinctElementsInWindow.size() < windowSize) {
                if (nums[outgoingIndex] == nums[scanIndex]) {
                    isOutgoingElementStillPresent = true;
                    break;
                }
                scanIndex++;
            }

            if (!isOutgoingElementStillPresent) {
                distinctElementsInWindow.remove(nums[outgoingIndex]);
            }

            distinctElementsInWindow.add(nums[incomingIndex]);

            if (distinctElementsInWindow.size() == windowSize && maxDistinctWindowSum < currentWindowSum) {
                maxDistinctWindowSum = currentWindowSum;
            }
        }

        return maxDistinctWindowSum;
    }
}