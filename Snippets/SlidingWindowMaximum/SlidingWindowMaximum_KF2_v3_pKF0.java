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

        int n = nums.length;
        int[] result = new int[n - windowSize + 1];
        Deque<Integer> indexDeque = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            int windowStart = i - windowSize + 1;

            // Remove indices that are out of the current window
            if (!indexDeque.isEmpty() && indexDeque.peekFirst() < windowStart) {
                indexDeque.pollFirst();
            }

            // Maintain decreasing order in deque by value
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