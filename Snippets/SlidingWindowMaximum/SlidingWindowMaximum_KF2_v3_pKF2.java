package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the maximum value in every contiguous subarray (sliding window)
     * of size {@code k} over the input array {@code nums}.
     *
     * @param nums the input array
     * @param k    the window size
     * @return an array of length {@code nums.length - k + 1} where each element
     *         is the maximum of the corresponding window; or an empty array if
     *         {@code k == 0} or {@code k > nums.length}
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;

        if (k == 0 || n < k) {
            return new int[0];
        }

        int[] result = new int[n - k + 1];

        /*
         * Deque stores indices of elements in decreasing order of their values.
         * The front of the deque always holds the index of the current window's maximum.
         */
        Deque<Integer> deque = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            int windowStart = i - k + 1;

            // Remove indices that are no longer within the current window
            if (!deque.isEmpty() && deque.peekFirst() < windowStart) {
                deque.pollFirst();
            }

            // Maintain decreasing order in deque by value:
            // remove indices whose values are smaller than nums[i]
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add current index to the deque
            deque.offerLast(i);

            // Once the first full window is formed, record its maximum
            if (i >= k - 1) {
                result[windowStart] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}