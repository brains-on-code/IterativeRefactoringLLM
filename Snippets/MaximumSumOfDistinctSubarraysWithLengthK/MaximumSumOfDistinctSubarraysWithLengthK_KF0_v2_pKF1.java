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
        Set<Integer> windowDistinctElements = new HashSet<>();

        // Initialize the first window
        for (int index = 0; index < windowSize; index++) {
            currentWindowSum += nums[index];
            windowDistinctElements.add(nums[index]);
        }

        if (windowDistinctElements.size() == windowSize) {
            maxDistinctWindowSum = currentWindowSum;
        }

        // Slide the window across the array
        for (int windowStartIndex = 1; windowStartIndex <= nums.length - windowSize; windowStartIndex++) {
            int outgoingIndex = windowStartIndex - 1;
            int incomingIndex = windowStartIndex + windowSize - 1;

            currentWindowSum = currentWindowSum - nums[outgoingIndex] + nums[incomingIndex];

            int scanIndex = windowStartIndex;
            boolean outgoingElementStillPresent = false;

            while (scanIndex < windowStartIndex + windowSize && windowDistinctElements.size() < windowSize) {
                if (nums[outgoingIndex] == nums[scanIndex]) {
                    outgoingElementStillPresent = true;
                    break;
                }
                scanIndex++;
            }

            if (!outgoingElementStillPresent) {
                windowDistinctElements.remove(nums[outgoingIndex]);
            }

            windowDistinctElements.add(nums[incomingIndex]);

            if (windowDistinctElements.size() == windowSize && maxDistinctWindowSum < currentWindowSum) {
                maxDistinctWindowSum = currentWindowSum;
            }
        }

        return maxDistinctWindowSum;
    }
}