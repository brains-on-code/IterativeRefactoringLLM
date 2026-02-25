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
        int resultLength = n - windowSize + 1;
        int[] result = new int[resultLength];
        Deque<Integer> indexDeque = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < n; currentIndex++) {
            removeOutOfWindowIndices(indexDeque, currentIndex, windowSize);
            removeSmallerValuesFromBack(nums, indexDeque, currentIndex);

            indexDeque.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                int resultIndex = currentIndex - windowSize + 1;
                result[resultIndex] = nums[indexDeque.peekFirst()];
            }
        }

        return result;
    }

    private boolean isInvalidInput(int[] nums, int windowSize) {
        return nums == null || nums.length == 0 || windowSize <= 0 || windowSize > nums.length;
    }

    private void removeOutOfWindowIndices(Deque<Integer> indexDeque, int currentIndex, int windowSize) {
        int windowStartIndex = currentIndex - windowSize + 1;
        while (!indexDeque.isEmpty() && indexDeque.peekFirst() < windowStartIndex) {
            indexDeque.pollFirst();
        }
    }

    private void removeSmallerValuesFromBack(int[] nums, Deque<Integer> indexDeque, int currentIndex) {
        while (!indexDeque.isEmpty() && nums[indexDeque.peekLast()] < nums[currentIndex]) {
            indexDeque.pollLast();
        }
    }
}