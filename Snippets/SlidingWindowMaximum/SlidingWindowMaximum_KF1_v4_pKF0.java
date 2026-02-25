package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Utility class for queue-related algorithms.
 */
public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
        // Prevent instantiation
    }

    /**
     * Returns an array of the maximum values in each sliding window of size {@code windowSize}
     * over the input array {@code nums}.
     *
     * Example:
     * nums = [1, 3, -1, -3, 5, 3, 6, 7], windowSize = 3
     * result = [3, 3, 5, 5, 6, 7]
     *
     * @param nums       the input array
     * @param windowSize the size of the sliding window
     * @return an array of maximums for each window; empty if windowSize is 0 or greater than nums.length
     */
    public static int[] maxSlidingWindow(int[] nums, int windowSize) {
        if (isInvalidInput(nums, windowSize)) {
            return new int[0];
        }

        final int length = nums.length;
        final int resultLength = length - windowSize + 1;
        int[] result = new int[resultLength];
        Deque<Integer> indices = new LinkedList<>();

        for (int i = 0; i < length; i++) {
            int windowStart = i - windowSize + 1;

            removeOutOfWindowIndices(indices, windowStart);
            removeSmallerValuesFromBack(nums, indices, i);

            indices.offerLast(i);

            if (windowStart >= 0) {
                result[windowStart] = nums[indices.peekFirst()];
            }
        }

        return result;
    }

    private static boolean isInvalidInput(int[] nums, int windowSize) {
        return nums == null || windowSize <= 0 || nums.length < windowSize;
    }

    private static void removeOutOfWindowIndices(Deque<Integer> indices, int windowStart) {
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