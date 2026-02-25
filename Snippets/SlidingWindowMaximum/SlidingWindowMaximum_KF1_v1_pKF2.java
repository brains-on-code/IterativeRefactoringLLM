package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Utility class for queue-related algorithms.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Computes the maximum value in every contiguous subarray (sliding window) of size {@code windowSize}.
     *
     * <p>Example:
     * <pre>
     * nums       = [1, 3, -1, -3, 5, 3, 6, 7]
     * windowSize = 3
     * result     = [3, 3, 5, 5, 6, 7]
     * </pre>
     *
     * @param nums       the input array
     * @param windowSize the size of the sliding window
     * @return an array where each element is the maximum of a window; empty array if
     *         {@code windowSize} is 0 or greater than {@code nums.length}
     */
    public static int[] method1(int[] nums, int windowSize) {
        int n = nums.length;
        if (n < windowSize || windowSize == 0) {
            return new int[0];
        }

        int[] result = new int[n - windowSize + 1];
        Deque<Integer> deque = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            // Remove indices that are out of the current window
            if (!deque.isEmpty() && deque.peekFirst() < i - windowSize + 1) {
                deque.pollFirst();
            }

            // Remove indices whose corresponding values are less than nums[i]
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add current index
            deque.offerLast(i);

            // Record the maximum for the current window
            if (i >= windowSize - 1) {
                result[i - windowSize + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}