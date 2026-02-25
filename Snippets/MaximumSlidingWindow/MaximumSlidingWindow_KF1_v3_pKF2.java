package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Utility class for array-related algorithms.
 */
public class Class1 {

    /**
     * Computes the maximum value in every contiguous subarray (sliding window) of size {@code windowSize}.
     *
     * <p>For an input array {@code nums} of length {@code n} and a window size {@code windowSize},
     * this returns an array of length {@code n - windowSize + 1} where each element is the maximum
     * of the corresponding window.</p>
     *
     * <p>Example:
     * nums = [1, 3, -1, -3, 5, 3, 6, 7], windowSize = 3
     * result = [3, 3, 5, 5, 6, 7]</p>
     *
     * @param nums       the input array
     * @param windowSize the size of the sliding window (must be between 1 and nums.length)
     * @return an array of window maxima; empty array if input is invalid
     */
    public int[] method1(int[] nums, int windowSize) {
        if (nums == null || nums.length == 0 || windowSize <= 0 || windowSize > nums.length) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - windowSize + 1];

        /*
         * Deque of indices into nums.
         * Invariant: nums[deque[0]] >= nums[deque[1]] >= ... (values are non-increasing).
         * The front of the deque always holds the index of the current window's maximum.
         */
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            int windowStartIndex = i - windowSize + 1;

            // Remove indices that are no longer within the current window.
            if (!deque.isEmpty() && deque.peekFirst() < windowStartIndex) {
                deque.pollFirst();
            }

            // Remove indices whose values are smaller than nums[i],
            // since they cannot be the maximum for this or any future window including i.
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add the current index; deque remains in non-increasing order of values.
            deque.offerLast(i);

            // Once the first full window is formed, record its maximum.
            if (i >= windowSize - 1) {
                result[windowStartIndex] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}