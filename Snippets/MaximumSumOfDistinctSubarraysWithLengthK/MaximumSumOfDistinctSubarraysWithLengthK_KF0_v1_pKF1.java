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
    private MaximumSumOfDistinctSubarraysWithLengthK() {
    }

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

        long maxSum = 0; // Maximum sum of distinct subarrays
        long currentWindowSum = 0; // Sum of the current subarray
        Set<Integer> distinctElementsInWindow = new HashSet<>(); // Distinct elements in the current subarray

        // Initialize the first window
        for (int index = 0; index < windowSize; index++) {
            currentWindowSum += nums[index];
            distinctElementsInWindow.add(nums[index]);
        }

        // If the first window contains distinct elements, update maxSum
        if (distinctElementsInWindow.size() == windowSize) {
            maxSum = currentWindowSum;
        }

        // Slide the window across the array
        for (int windowStart = 1; windowStart <= nums.length - windowSize; windowStart++) {
            int outgoingElementIndex = windowStart - 1;
            int incomingElementIndex = windowStart + windowSize - 1;

            // Update the sum by removing the element that is sliding out and adding the new element
            currentWindowSum = currentWindowSum - nums[outgoingElementIndex] + nums[incomingElementIndex];

            int scanIndex = windowStart;
            boolean hasDuplicateOfOutgoingElement = false; // Indicates if the outgoing element still exists in the window

            while (scanIndex < windowStart + windowSize && distinctElementsInWindow.size() < windowSize) {
                if (nums[outgoingElementIndex] == nums[scanIndex]) {
                    hasDuplicateOfOutgoingElement = true;
                    break;
                } else {
                    scanIndex++;
                }
            }

            if (!hasDuplicateOfOutgoingElement) {
                distinctElementsInWindow.remove(nums[outgoingElementIndex]);
            }

            distinctElementsInWindow.add(nums[incomingElementIndex]);

            // If the current window has distinct elements, compare and possibly update maxSum
            if (distinctElementsInWindow.size() == windowSize && maxSum < currentWindowSum) {
                maxSum = currentWindowSum;
            }
        }

        return maxSum; // the final maximum sum
    }
}