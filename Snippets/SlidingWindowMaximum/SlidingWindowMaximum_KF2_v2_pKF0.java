package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
        // Utility class; prevent instantiation
    }

    public static int[] maxSlidingWindow(int[] nums, int windowSize) {
        if (nums == null || windowSize <= 0 || nums.length < windowSize) {
            return new int[0];
        }

        int length = nums.length;
        int resultLength = length - windowSize + 1;
        int[] result = new int[resultLength];
        Deque<Integer> indices = new LinkedList<>();

        for (int i = 0; i < length; i++) {
            int windowStart = i - windowSize + 1;

            // Remove indices that are out of the current window
            if (!indices.isEmpty() && indices.peekFirst() < windowStart) {
                indices.pollFirst();
            }

            // Remove indices whose corresponding values are less than current value
            while (!indices.isEmpty() && nums[indices.peekLast()] < nums[i]) {
                indices.pollLast();
            }

            // Add current index
            indices.offerLast(i);

            // Store the maximum for the current window
            if (windowStart >= 0) {
                result[windowStart] = nums[indices.peekFirst()];
            }
        }

        return result;
    }
}