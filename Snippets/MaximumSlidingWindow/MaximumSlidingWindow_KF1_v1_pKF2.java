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
     * <p>For an input array {@code nums} of length {@code n} and a window size {@code k},
     * this returns an array of length {@code n - k + 1} where each element is the maximum
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

        int[] result = new int[nums.length - windowSize + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // stores indices of elements in decreasing order

        for (int i = 0; i < nums.length; i++) {

            // Remove indices that are out of the current window
            if (!deque.isEmpty() && deque.peekFirst() == i - windowSize) {
                deque.pollFirst();
            }

            // Remove indices whose corresponding values are less than current element
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add current index
            deque.offerLast(i);

            // Once the first window is formed, record the maximum for each window
            if (i >= windowSize - 1) {
                result[i - windowSize + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
}