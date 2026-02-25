package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Computes the maximum value in each sliding window of a given size over an array.
 *
 * Time Complexity: O(n), where n is the number of elements in the input array.
 * Space Complexity: O(k), where k is the size of the sliding window.
 */
public class MaximumSlidingWindow {

    /**
     * Returns an array of maximum values for each sliding window of the given size.
     *
     * @param nums       the input array of integers
     * @param windowSize the size of the sliding window
     * @return an array containing the maximum for each window; empty if input is invalid
     */
    public int[] maxSlidingWindow(int[] nums, int windowSize) {
        if (nums == null || nums.length == 0 || windowSize <= 0 || windowSize > nums.length) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - windowSize + 1];

        // Deque stores indices of elements in decreasing order of their values.
        // The front of the deque always holds the index of the current window's maximum.
        Deque<Integer> deque = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < n; currentIndex++) {

            // Remove indices that are outside the current window.
            int windowStartIndex = currentIndex - windowSize + 1;
            if (!deque.isEmpty() && deque.peekFirst() < windowStartIndex) {
                deque.pollFirst();
            }

            // Maintain decreasing order in deque:
            // remove indices whose values are less than the current value.
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[currentIndex]) {
                deque.pollLast();
            }

            // Add current index as a candidate for maximum.
            deque.offerLast(currentIndex);

            // Once the first full window is formed, record its maximum.
            if (currentIndex >= windowSize - 1) {
                result[windowStartIndex] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}