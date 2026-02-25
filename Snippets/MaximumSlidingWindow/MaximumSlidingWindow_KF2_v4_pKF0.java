package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumSlidingWindow {

    public int[] maxSlidingWindow(int[] nums, int windowSize) {
        if (!isValidInput(nums, windowSize)) {
            return new int[0];
        }

        int resultLength = nums.length - windowSize + 1;
        int[] maxValues = new int[resultLength];
        Deque<Integer> indices = new ArrayDeque<>();

        for (int i = 0; i < nums.length; i++) {
            removeIndicesOutOfWindow(indices, i, windowSize);
            removeSmallerElements(nums, indices, i);

            indices.offerLast(i);

            if (i >= windowSize - 1) {
                int resultIndex = i - windowSize + 1;
                maxValues[resultIndex] = nums[indices.peekFirst()];
            }
        }

        return maxValues;
    }

    private boolean isValidInput(int[] nums, int windowSize) {
        return nums != null
            && nums.length > 0
            && windowSize > 0
            && windowSize <= nums.length;
    }

    private void removeIndicesOutOfWindow(Deque<Integer> indices, int currentIndex, int windowSize) {
        int windowStart = currentIndex - windowSize + 1;
        while (!indices.isEmpty() && indices.peekFirst() < windowStart) {
            indices.pollFirst();
        }
    }

    private void removeSmallerElements(int[] nums, Deque<Integer> indices, int currentIndex) {
        while (!indices.isEmpty() && nums[indices.peekLast()] < nums[currentIndex]) {
            indices.pollLast();
        }
    }
}