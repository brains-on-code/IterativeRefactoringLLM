package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for array-related algorithms.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Returns the maximum sum of any contiguous subarray of length {@code windowSize}
     * that contains only distinct elements. If the array length is smaller than
     * {@code windowSize}, returns 0.
     *
     * @param windowSize the size of the sliding window
     * @param nums       the input array
     * @return the maximum sum of a window of size {@code windowSize} with all
     *         distinct elements, or 0 if no such window exists
     */
    public static long method1(int windowSize, int... nums) {
        if (nums.length < windowSize || windowSize <= 0) {
            return 0;
        }

        long maxSum = 0;
        long currentSum = 0;
        Set<Integer> windowElements = new HashSet<>();

        // Build the first window
        for (int i = 0; i < windowSize; i++) {
            currentSum += nums[i];
            windowElements.add(nums[i]);
        }

        if (windowElements.size() == windowSize) {
            maxSum = currentSum;
        }

        // Slide the window across the array
        for (int start = 1; start <= nums.length - windowSize; start++) {
            int outgoing = nums[start - 1];
            int incoming = nums[start + windowSize - 1];

            currentSum = currentSum - outgoing + incoming;

            boolean outgoingStillInWindow = false;

            // Check if the outgoing element still appears in the new window
            for (int i = start; i < start + windowSize; i++) {
                if (nums[i] == outgoing) {
                    outgoingStillInWindow = true;
                    break;
                }
            }

            if (!outgoingStillInWindow) {
                windowElements.remove(outgoing);
            }

            windowElements.add(incoming);

            if (windowElements.size() == windowSize && currentSum > maxSum) {
                maxSum = currentSum;
            }
        }

        return maxSum;
    }
}