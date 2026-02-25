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
        int[] maxValues = new int[n - windowSize + 1];
        Deque<Integer> indices = new LinkedList<>();

        for (int right = 0; right < n; right++) {
            int left = right - windowSize + 1;

            removeIndicesOutOfWindow(indices, left);
            removeSmallerValuesFromBack(nums, indices, right);

            indices.offerLast(right);

            if (left >= 0) {
                maxValues[left] = nums[indices.peekFirst()];
            }
        }

        return maxValues;
    }

    private static void removeIndicesOutOfWindow(Deque<Integer> indices, int windowStart) {
        while (!indices.isEmpty() && indices.peekFirst() < windowStart) {
            indices.pollFirst();
        }
    }

    private static void removeSmallerValuesFromBack(int[] nums, Deque<Integer> indices, int currentIndex) {
        while (!indices.isEmpty() && nums[indices.peekLast()] < nums[currentIndex]) {
            indices.pollLast();
        }
    }
}