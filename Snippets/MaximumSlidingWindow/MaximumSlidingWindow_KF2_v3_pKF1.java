package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumSlidingWindow {

    public int[] maxSlidingWindow(int[] nums, int windowSize) {
        if (nums == null || nums.length == 0 || windowSize <= 0 || windowSize > nums.length) {
            return new int[0];
        }

        int resultLength = nums.length - windowSize + 1;
        int[] maxValues = new int[resultLength];
        Deque<Integer> indexDeque = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {

            int windowStartIndex = currentIndex - windowSize + 1;

            if (!indexDeque.isEmpty() && indexDeque.peekFirst() < windowStartIndex) {
                indexDeque.pollFirst();
            }

            while (!indexDeque.isEmpty() && nums[indexDeque.peekLast()] < nums[currentIndex]) {
                indexDeque.pollLast();
            }

            indexDeque.offerLast(currentIndex);

            if (windowStartIndex >= 0) {
                maxValues[windowStartIndex] = nums[indexDeque.peekFirst()];
            }
        }

        return maxValues;
    }
}