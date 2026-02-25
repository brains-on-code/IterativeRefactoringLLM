package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Computes the maximum value in each sliding window of size {@code k}
 * over an integer array using a deque-based O(n) algorithm.
 */
public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
        // Prevent instantiation
    }

    /**
     * Returns the maximum value for every contiguous subarray (sliding window)
     * of size {@code k}.
     *
     * <p>If {@code nums.length < k} or {@code k == 0}, an empty array is returned.</p>
     *
     * <p>Example:
     * <pre>
     * nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
     * result = [3, 3, 5, 5, 6, 7]
     * </pre>
     *
     * @param nums the input array
     * @param k    the window size
     * @return an array where each element is the maximum of a sliding window
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;

        if (k == 0 || n < k) {
            return new int[0];
        }

        int[] result = new int[n - k + 1];

        /*
         * Deque stores indices of elements in decreasing order of their values.
         * Front: index of the maximum element for the current window.
         * Back: indices of elements that may become maximums in future windows.
         */
        Deque<Integer> deque = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            int windowStart = i - k + 1;

            // Remove indices that are outside the current window
            if (!deque.isEmpty() && deque.peekFirst() < windowStart) {
                deque.pollFirst();
            }

            // Maintain decreasing order in deque by value
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add current index as a candidate
            deque.offerLast(i);

            // Record maximum once the first full window is formed
            if (i >= k - 1) {
                result[windowStart] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}