package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Provides algorithms for processing integer arrays.
 */
public class Class1 {

    /**
     * Returns the maximum value in each sliding window of size {@code windowSize}.
     *
     * <p>For an input array {@code nums} of length {@code n}, this method returns an array of
     * length {@code n - windowSize + 1}, where each element is the maximum value in the
     * corresponding contiguous subarray (window) of size {@code windowSize}.</p>
     *
     * <p>Example:
     * <pre>
     * nums       = [1, 3, -1, -3, 5, 3, 6, 7]
     * windowSize = 3
     * result     = [3, 3, 5, 5, 6, 7]
     * </pre>
     * </p>
     *
     * @param nums       the input array
     * @param windowSize the size of the sliding window; must be between 1 and {@code nums.length}
     * @return an array of window maxima, or an empty array if the input is invalid
     */
    public int[] method1(int[] nums, int windowSize) {
        if (nums == null || nums.length == 0 || windowSize <= 0 || windowSize > nums.length) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - windowSize + 1];

        /*
         * Deque storing indices of elements in {@code nums}.
         *
         * Invariant:
         * - Indices in the deque are always within the current window.
         * - Values at those indices are in non-increasing order:
         *   nums[deque[0]] >= nums[deque[1]] >= ...
         *
         * Therefore, the index at the front of the deque always corresponds
         * to the maximum value in the current window.
         */
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            int windowStartIndex = i - windowSize + 1;

            // Remove indices that are outside the current window.
            if (!deque.isEmpty() && deque.peekFirst() < windowStartIndex) {
                deque.pollFirst();
            }

            // Maintain non-increasing order of values in the deque:
            // remove indices whose values are smaller than nums[i],
            // as they cannot be maxima for the current or any future window including i.
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add the current index; deque now represents candidates for the window maximum.
            deque.offerLast(i);

            // Once the first full window is formed, record its maximum.
            if (i >= windowSize - 1) {
                result[windowStartIndex] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}