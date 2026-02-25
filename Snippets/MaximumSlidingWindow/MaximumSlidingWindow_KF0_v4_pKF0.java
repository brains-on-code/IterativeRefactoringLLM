package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Maximum Sliding Window Algorithm
 *
 * Finds the maximum element in each sliding window of size k
 * in a given array of integers using a deque to track candidates.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(k)
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
        Deque<Integer> indexDeque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            removeIndicesOutOfWindow(indexDeque, i, windowSize);
            removeSmallerValuesFromBack(nums, indexDeque, i);

            indexDeque.offerLast(i);

            if (i >= windowSize - 1) {
                int windowStart = i - windowSize + 1;
                result[windowStart] = nums[indexDeque.peekFirst()];
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

    private void removeIndicesOutOfWindow(Deque<Integer> indexDeque, int currentIndex, int windowSize) {
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