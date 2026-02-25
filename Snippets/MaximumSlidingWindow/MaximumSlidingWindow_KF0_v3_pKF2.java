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
        if (isInvalidInput(nums, windowSize)) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - windowSize + 1];

        /*
         * Deque of indices, maintained so that:
         * - All indices are within the current window.
         * - Values at stored indices are in non-increasing order.
         *   → The front of the deque always holds the index of the
         *     maximum element for the current window.
         */
        Deque<Integer> deque = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < n; currentIndex++) {
            int windowStartIndex = currentIndex - windowSize + 1;

            removeOutOfWindowIndices(deque, windowStartIndex);
            removeSmallerValuesFromBack(deque, nums, currentIndex);

            // Add current index as a candidate for the maximum.
            deque.offerLast(currentIndex);

            // Record the maximum once the first full window is formed.
            if (currentIndex >= windowSize - 1) {
                result[windowStartIndex] = nums[deque.peekFirst()];
            }
        }

        return result;
    }

    private boolean isInvalidInput(int[] nums, int windowSize) {
        return nums == null
            || nums.length == 0
            || windowSize <= 0
            || windowSize > nums.length;
    }

    /**
     * Removes indices from the front of the deque that are outside the current window.
     *
     * @param deque            deque of candidate indices for the current window
     * @param windowStartIndex starting index of the current window
     */
    private void removeOutOfWindowIndices(Deque<Integer> deque, int windowStartIndex) {
        while (!deque.isEmpty() && deque.peekFirst() < windowStartIndex) {
            deque.pollFirst();
        }
    }

    /**
     * Maintains the deque in non-increasing order of values by removing indices
     * from the back whose corresponding values are less than the current value.
     *
     * @param deque        deque of candidate indices for the current window
     * @param nums         input array
     * @param currentIndex index of the current element being processed
     */
    private void removeSmallerValuesFromBack(Deque<Integer> deque, int[] nums, int currentIndex) {
        while (!deque.isEmpty() && nums[deque.peekLast()] < nums[currentIndex]) {
            deque.pollLast();
        }
    }
}