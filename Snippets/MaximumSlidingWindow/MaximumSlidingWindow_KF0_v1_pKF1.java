package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Maximum Sliding Window Algorithm
 *
 * This algorithm finds the maximum element in each sliding window of size k
 * in a given array of integers. It uses a deque (double-ended queue) to
 * efficiently keep track of potential maximum values in the current window.
 *
 * Time Complexity: O(n), where n is the number of elements in the input array
 * Space Complexity: O(k), where k is the size of the sliding window
 */
public class MaximumSlidingWindow {

    /**
     * Finds the maximum values in each sliding window of size k.
     *
     * @param nums       The input array of integers
     * @param windowSize The size of the sliding window
     * @return An array of integers representing the maximums in each window
     */
    public int[] maxSlidingWindow(int[] nums, int windowSize) {
        if (nums == null || nums.length == 0 || windowSize <= 0 || windowSize > nums.length) {
            return new int[0];
        }

        int[] windowMaximums = new int[nums.length - windowSize + 1];
        Deque<Integer> indexDeque = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {

            int windowStartIndex = currentIndex - windowSize + 1;

            // Remove indices that are outside the current window
            if (!indexDeque.isEmpty() && indexDeque.peekFirst() < windowStartIndex) {
                indexDeque.pollFirst();
            }

            // Remove indices whose corresponding values are smaller than current value
            while (!indexDeque.isEmpty() && nums[indexDeque.peekLast()] < nums[currentIndex]) {
                indexDeque.pollLast();
            }

            // Add the current index to the deque
            indexDeque.offerLast(currentIndex);

            // Record the maximum for the current window
            if (windowStartIndex >= 0) {
                windowMaximums[windowStartIndex] = nums[indexDeque.peekFirst()];
            }
        }

        return windowMaximums;
    }
}