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

        int[] maxValuesPerWindow = new int[nums.length - windowSize + 1];
        Deque<Integer> candidateIndices = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {

            int windowStartIndex = currentIndex - windowSize + 1;

            // Remove indices that are outside the current window
            if (!candidateIndices.isEmpty() && candidateIndices.peekFirst() < windowStartIndex) {
                candidateIndices.pollFirst();
            }

            // Remove indices whose corresponding values are smaller than current value
            while (!candidateIndices.isEmpty() && nums[candidateIndices.peekLast()] < nums[currentIndex]) {
                candidateIndices.pollLast();
            }

            // Add the current index to the deque
            candidateIndices.offerLast(currentIndex);

            // Record the maximum for the current window
            if (windowStartIndex >= 0) {
                maxValuesPerWindow[windowStartIndex] = nums[candidateIndices.peekFirst()];
            }
        }

        return maxValuesPerWindow;
    }
}