package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumSlidingWindow {

    /**
     * Returns an array of the maximum values in each sliding window of the given size.
     *
     * @param nums       the input array
     * @param windowSize the size of the sliding window
     * @return an array containing the maximum of each sliding window
     */
    public int[] maxSlidingWindow(int[] nums, int windowSize) {
        if (nums == null || nums.length == 0 || windowSize <= 0 || windowSize > nums.length) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - windowSize + 1];

        // Deque stores indices of elements in nums.
        // It is maintained so that:
        // 1) Indices are within the current window.
        // 2) Values at stored indices are in decreasing order.
        Deque<Integer> deque = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < n; currentIndex++) {

            // Remove indices that are out of the current window
            int windowStartIndex = currentIndex - windowSize + 1;
            if (!deque.isEmpty() && deque.peekFirst() < windowStartIndex) {
                deque.pollFirst();
            }

            // Remove indices whose corresponding values are less than nums[currentIndex]
            // to maintain decreasing order in the deque
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[currentIndex]) {
                deque.pollLast();
            }

            // Add current index to the deque
            deque.offerLast(currentIndex);

            // Once the first window is formed, record the maximum for each window
            if (currentIndex >= windowSize - 1) {
                result[windowStartIndex] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}