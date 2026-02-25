package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumSlidingWindow {

    public int[] maxSlidingWindow(int[] nums, int windowSize) {
        if (isInvalidInput(nums, windowSize)) {
            return new int[0];
        }

        int[] maxValues = new int[nums.length - windowSize + 1];
        Deque<Integer> indexDeque = new ArrayDeque<>();

        for (int i = 0; i < nums.length; i++) {
            removeOutOfWindowIndices(indexDeque, i, windowSize);
            removeSmallerElements(nums, indexDeque, i);

            indexDeque.offerLast(i);

            if (i >= windowSize - 1) {
                int resultIndex = i - windowSize + 1;
                maxValues[resultIndex] = nums[indexDeque.peekFirst()];
            }
        }

        return maxValues;
    }

    private boolean isInvalidInput(int[] nums, int windowSize) {
        return nums == null
            || nums.length == 0
            || windowSize <= 0
            || windowSize > nums.length;
    }

    private void removeOutOfWindowIndices(Deque<Integer> indexDeque, int currentIndex, int windowSize) {
        int windowStart = currentIndex - windowSize + 1;
        while (!indexDeque.isEmpty() && indexDeque.peekFirst() < windowStart) {
            indexDeque.pollFirst();
        }
    }

    private void removeSmallerElements(int[] nums, Deque<Integer> indexDeque, int currentIndex) {
        while (!indexDeque.isEmpty() && nums[indexDeque.peekLast()] < nums[currentIndex]) {
            indexDeque.pollLast();
        }
    }
}