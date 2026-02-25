package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
        // Utility class; prevent instantiation
    }

    public static int[] maxSlidingWindow(int[] nums, int windowSize) {
        if (nums == null || windowSize <= 0 || nums.length < windowSize) {
            return new int[0];
        }

        int n = nums.length;
        int resultLength = n - windowSize + 1;
        int[] result = new int[resultLength];
        Deque<Integer> indexDeque = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            int windowStartIndex = i - windowSize + 1;

            removeOutOfWindowIndices(indexDeque, windowStartIndex);
            removeSmallerValuesFromBack(nums, indexDeque, i);

            indexDeque.offerLast(i);

            if (windowStartIndex >= 0) {
                result[windowStartIndex] = nums[indexDeque.peekFirst()];
            }
        }

        return result;
    }

    private static void removeOutOfWindowIndices(Deque<Integer> indexDeque, int windowStartIndex) {
        while (!indexDeque.isEmpty() && indexDeque.peekFirst() < windowStartIndex) {
            indexDeque.pollFirst();
        }
    }

    private static void removeSmallerValuesFromBack(int[] nums, Deque<Integer> indexDeque, int currentIndex) {
        while (!indexDeque.isEmpty() && nums[indexDeque.peekLast()] < nums[currentIndex]) {
            indexDeque.pollLast();
        }
    }
}