package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class Class1 {

    /**
     * Returns an array of the maximum values in each sliding window of size windowSize
     * over the input array nums.
     *
     * @param nums       the input array
     * @param windowSize the size of the sliding window
     * @return an array of maximums for each window; empty array if input is invalid
     */
    public int[] slidingWindowMaximum(int[] nums, int windowSize) {
        if (isInvalidInput(nums, windowSize)) {
            return new int[0];
        }

        int resultLength = nums.length - windowSize + 1;
        int[] result = new int[resultLength];
        Deque<Integer> indexDeque = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {
            removeOutOfWindowIndices(indexDeque, currentIndex, windowSize);
            removeSmallerValues(nums, indexDeque, currentIndex);

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
        int windowStartIndex = currentIndex - windowSize;
        if (!indexDeque.isEmpty() && indexDeque.peekFirst() == windowStartIndex) {
            indexDeque.pollFirst();
        }
    }

    private void removeSmallerValues(int[] nums, Deque<Integer> indexDeque, int currentIndex) {
        while (!indexDeque.isEmpty() && nums[indexDeque.peekLast()] < nums[currentIndex]) {
            indexDeque.pollLast();
        }
    }
}