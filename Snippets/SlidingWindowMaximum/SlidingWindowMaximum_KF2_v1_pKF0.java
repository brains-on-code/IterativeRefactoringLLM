package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
        // Utility class; prevent instantiation
    }

    public static int[] maxSlidingWindow(int[] nums, int windowSize) {
        int length = nums.length;

        if (windowSize <= 0 || length < windowSize) {
            return new int[0];
        }

        int[] result = new int[length - windowSize + 1];
        Deque<Integer> indexDeque = new LinkedList<>();

        for (int currentIndex = 0; currentIndex < length; currentIndex++) {
            int windowStart = currentIndex - windowSize + 1;

            // Remove indices that are out of the current window
            if (!indexDeque.isEmpty() && indexDeque.peekFirst() < windowStart) {
                indexDeque.pollFirst();
            }

            // Maintain decreasing order in deque (by value)
            while (!indexDeque.isEmpty()
                    && nums[indexDeque.peekLast()] < nums[currentIndex]) {
                indexDeque.pollLast();
            }

            // Add current index
            indexDeque.offerLast(currentIndex);

            // Record the maximum for the current window
            if (currentIndex >= windowSize - 1) {
                result[windowStart] = nums[indexDeque.peekFirst()];
            }
        }

        return result;
    }
}