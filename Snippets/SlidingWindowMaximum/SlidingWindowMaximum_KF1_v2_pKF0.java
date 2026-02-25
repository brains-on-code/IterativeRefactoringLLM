package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Utility class for queue-related algorithms.
 */
public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
        // Prevent instantiation
    }

    /**
     * Returns an array of the maximum values in each sliding window of size {@code windowSize}
     * over the input array {@code nums}.
     *
     * Example:
     * nums = [1, 3, -1, -3, 5, 3, 6, 7], windowSize = 3
     * result = [3, 3, 5, 5, 6, 7]
     *
     * @param nums       the input array
     * @param windowSize the size of the sliding window
     * @return an array of maximums for each window; empty if windowSize is 0 or greater than nums.length
     */
    public static int[] maxSlidingWindow(int[] nums, int windowSize) {
        if (nums == null || windowSize <= 0 || nums.length < windowSize) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - windowSize + 1];
        Deque<Integer> indexDeque = new LinkedList<>(); // stores indices of elements in decreasing order of value

        for (int i = 0; i < n; i++) {
            int windowStart = i - windowSize + 1;

            // Remove indices that are out of the current window
            if (!indexDeque.isEmpty() && indexDeque.peekFirst() < windowStart) {
                indexDeque.pollFirst();
            }

            // Maintain elements in decreasing order in the deque
            while (!indexDeque.isEmpty() && nums[indexDeque.peekLast()] < nums[i]) {
                indexDeque.pollLast();
            }

            // Add current index
            indexDeque.offerLast(i);

            // Record the maximum for the current window
            if (windowStart >= 0) {
                result[windowStart] = nums[indexDeque.peekFirst()];
            }
        }

        return result;
    }
}