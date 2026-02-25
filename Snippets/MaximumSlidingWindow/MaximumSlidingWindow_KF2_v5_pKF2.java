package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumSlidingWindow {

    /**
     * Returns the maximum value in each sliding window of size {@code windowSize}
     * over the input array {@code nums}.
     *
     * @param nums       the input array
     * @param windowSize the size of the sliding window
     * @return an array of window maxima, or an empty array if the input is invalid
     */
    public int[] maxSlidingWindow(int[] nums, int windowSize) {
        if (nums == null || nums.length == 0 || windowSize <= 0 || windowSize > nums.length) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - windowSize + 1];

        /*
         * Deque storing indices of elements in nums.
         *
         * Invariants:
         *  - All indices are within the current window.
         *  - Values at those indices are in non-increasing order:
         *      nums[deque[0]] >= nums[deque[1]] >= ...
         *    Therefore, nums[deque[0]] is always the maximum in the current window.
         */
        Deque<Integer> deque = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < n; currentIndex++) {
            int windowStartIndex = currentIndex - windowSize + 1;

            // Remove indices that are no longer in the current window
            if (!deque.isEmpty() && deque.peekFirst() < windowStartIndex) {
                deque.pollFirst();
            }

            // Remove indices whose values are smaller than the current value
            // to maintain non-increasing order in the deque
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[currentIndex]) {
                deque.pollLast();
            }

            // Add the current index as a candidate for the maximum
            deque.offerLast(currentIndex);

            // Once the first full window is formed, record its maximum
            if (currentIndex >= windowSize - 1) {
                result[windowStartIndex] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}