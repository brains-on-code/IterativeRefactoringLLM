package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the maximum value in each sliding window of size {@code k}
     * over the input array {@code nums}.
     *
     * @param nums the input array
     * @param k    the size of the sliding window
     * @return an array where each element is the maximum of a window
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;

        // Edge cases: window larger than array or zero-sized window
        if (k == 0 || n < k) {
            return new int[0];
        }

        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new LinkedList<>(); // stores indices of elements

        for (int i = 0; i < n; i++) {
            // Remove indices that are out of the current window
            int windowStartIndex = i - k + 1;
            if (!deque.isEmpty() && deque.peekFirst() < windowStartIndex) {
                deque.pollFirst();
            }

            // Maintain elements in decreasing order in the deque
            // Remove indices whose corresponding values are less than nums[i]
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add current index
            deque.offerLast(i);

            // Once we've processed at least k elements, record the maximum
            if (i >= k - 1) {
                result[windowStartIndex] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}