package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumSlidingWindow {

    public int[] maxSlidingWindow(int[] nums, int windowSize) {
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
        int windowStartIndex = currentIndex - windowSize + 1;
        while (!indexDeque.isEmpty() && indexDeque.peekFirst() < windowStartIndex) {
            indexDeque.pollFirst();
        }
    }

    private void removeSmallerValues(int[] nums, Deque<Integer> indexDeque, int currentIndex) {
        while (!indexDeque.isEmpty() && nums[indexDeque.peekLast()] < nums[currentIndex]) {
            indexDeque.pollLast();
        }
    }
}