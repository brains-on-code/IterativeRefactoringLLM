package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Utility class for array-related algorithms.
 */
public class Class1 {

    /**
     * Returns the maximum value in every contiguous subarray (sliding window) of size {@code windowSize}.
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

        // Deque stores indices of elements; values are in non-increasing order
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {

            // Remove indices that are outside the current window
            int windowStartIndex = i - windowSize + 1;
            if (!deque.isEmpty() && deque.peekFirst() < windowStartIndex) {
                deque.pollFirst();
            }

            // Maintain decreasing order in deque:
            // remove indices whose values are smaller than the current element
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add current index to the deque
            deque.offerLast(i);

            // Once the first full window is formed, record its maximum
            if (i >= windowSize - 1) {
                result[windowStartIndex] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}