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
     * Finds the maximum values in each sliding window of size windowSize.
     *
     * @param nums       the input array of integers
     * @param windowSize the size of the sliding window
     * @return an array of integers representing the maximums in each window
     */
    public int[] maxSlidingWindow(int[] nums, int windowSize) {
        if (isInvalidInput(nums, windowSize)) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - windowSize + 1];
        Deque<Integer> indices = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            removeOutOfWindowIndices(indices, i, windowSize);
            removeSmallerValuesFromBack(nums, indices, i);

            indices.offerLast(i);

            if (i >= windowSize - 1) {
                int windowStart = i - windowSize + 1;
                result[windowStart] = nums[indices.peekFirst()];
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

    private void removeOutOfWindowIndices(Deque<Integer> indices, int currentIndex, int windowSize) {
        int windowStartIndex = currentIndex - windowSize + 1;
        while (!indices.isEmpty() && indices.peekFirst() < windowStartIndex) {
            indices.pollFirst();
        }
    }

    private void removeSmallerValuesFromBack(int[] nums, Deque<Integer> indices, int currentIndex) {
        while (!indices.isEmpty() && nums[indices.peekLast()] < nums[currentIndex]) {
            indices.pollLast();
        }
    }
}