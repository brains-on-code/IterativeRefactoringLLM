package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Utility class for computing the maximum value in each sliding window of size {@code k}
 * over an integer array.
 *
 * <p>Uses a deque to store indices of elements that are candidates for the maximum
 * in the current window. Each element is added and removed at most once, resulting
 * in an overall time complexity of O(n).
 */
public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
        // Prevent instantiation
    }

    /**
     * Computes the maximum value for every contiguous subarray (sliding window) of size {@code k}.
     *
     * <p>If {@code nums.length < k} or {@code k == 0}, an empty array is returned.
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
        Deque<Integer> deque = new LinkedList<>(); // stores indices of elements

        for (int i = 0; i < n; i++) {
            int windowStart = i - k + 1;

            // Remove indices that are outside the current window
            if (!deque.isEmpty() && deque.peekFirst() < windowStart) {
                deque.pollFirst();
            }

            // Maintain decreasing order in deque: remove smaller elements from the back
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add current index
            deque.offerLast(i);

            // Once the first window is formed, record the maximum for each window
            if (i >= k - 1) {
                result[windowStart] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}