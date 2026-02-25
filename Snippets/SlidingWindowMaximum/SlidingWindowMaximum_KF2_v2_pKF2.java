package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
        // Prevent instantiation
    }

    /**
     * Returns an array where each element is the maximum value
     * in a sliding window of size {@code k} over {@code nums}.
     *
     * @param nums the input array
     * @param k    the window size
     * @return array of window maxima
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;

        // Invalid or impossible window
        if (k == 0 || n < k) {
            return new int[0];
        }

        int[] result = new int[n - k + 1];
        // Deque holds indices of elements in decreasing order of their values
        Deque<Integer> deque = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            int windowStart = i - k + 1;

            // Remove indices that are left of the current window
            if (!deque.isEmpty() && deque.peekFirst() < windowStart) {
                deque.pollFirst();
            }

            // Remove indices whose values are smaller than nums[i]
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add current index
            deque.offerLast(i);

            // Record maximum once the first full window is formed
            if (i >= k - 1) {
                result[windowStart] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}