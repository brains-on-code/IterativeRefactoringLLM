package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
    }

    public static int[] findSlidingWindowMaximums(int[] nums, int windowSize) {
        int n = nums.length;
        if (n < windowSize || windowSize == 0) {
            return new int[0];
        }

        int[] maxValues = new int[n - windowSize + 1];
        Deque<Integer> indexDeque = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            int windowStart = i - windowSize + 1;

            if (!indexDeque.isEmpty() && indexDeque.peekFirst() < windowStart) {
                indexDeque.pollFirst();
            }

            while (!indexDeque.isEmpty() && nums[indexDeque.peekLast()] < nums[i]) {
                indexDeque.pollLast();
            }

            indexDeque.offerLast(i);

            if (i >= windowSize - 1) {
                maxValues[windowStart] = nums[indexDeque.peekFirst()];
            }
        }

        return maxValues;
    }
}