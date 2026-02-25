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
        Deque<Integer> windowIndices = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < nums.length; currentIndex++) {
            removeIndicesOutsideWindow(windowIndices, currentIndex, windowSize);
            removeSmallerElements(nums, windowIndices, currentIndex);

            windowIndices.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                int resultIndex = currentIndex - windowSize + 1;
                result[resultIndex] = nums[windowIndices.peekFirst()];
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

    private void removeIndicesOutsideWindow(Deque<Integer> windowIndices, int currentIndex, int windowSize) {
        int windowStartIndex = currentIndex - windowSize + 1;
        while (!windowIndices.isEmpty() && windowIndices.peekFirst() < windowStartIndex) {
            windowIndices.pollFirst();
        }
    }

    private void removeSmallerElements(int[] nums, Deque<Integer> windowIndices, int currentIndex) {
        while (!windowIndices.isEmpty() && nums[windowIndices.peekLast()] < nums[currentIndex]) {
            windowIndices.pollLast();
        }
    }
}