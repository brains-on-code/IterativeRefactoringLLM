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
        Deque<Integer> windowIndices = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {
            removeIndicesOutOfWindow(windowIndices, currentIndex, windowSize);
            removeSmallerElements(nums, windowIndices, currentIndex);

            windowIndices.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                int resultIndex = currentIndex - windowSize + 1;
                maxValues[resultIndex] = nums[windowIndices.peekFirst()];
            }
        }

        return maxValues;
    }

    private boolean isValidInput(int[] nums, int windowSize) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        if (windowSize <= 0 || windowSize > nums.length) {
            return false;
        }
        return true;
    }

    private void removeIndicesOutOfWindow(Deque<Integer> windowIndices, int currentIndex, int windowSize) {
        int windowStart = currentIndex - windowSize + 1;
        while (!windowIndices.isEmpty() && windowIndices.peekFirst() < windowStart) {
            windowIndices.pollFirst();
        }
    }

    private void removeSmallerElements(int[] nums, Deque<Integer> windowIndices, int currentIndex) {
        while (!windowIndices.isEmpty() && nums[windowIndices.peekLast()] < nums[currentIndex]) {
            windowIndices.pollLast();
        }
    }
}