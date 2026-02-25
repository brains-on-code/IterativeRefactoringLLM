package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Utility class for queue-based algorithms.
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
        if (windowSize == 0 || n < windowSize) {
            return new int[0];
        }

        int[] result = new int[n - windowSize + 1];
        Deque<Integer> indexDeque = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            int windowStart = i - windowSize + 1;

            // Remove indices that are left of the current window
            if (!indexDeque.isEmpty() && indexDeque.peekFirst() < windowStart) {
                indexDeque.pollFirst();
            }

            // Remove indices whose values are smaller than nums[i]
            // to keep deque values in decreasing order
            while (!indexDeque.isEmpty() && nums[indexDeque.peekLast()] < nums[i]) {
                indexDeque.pollLast();
            }

            indexDeque.offerLast(i);

            // Record the maximum once the first full window is formed
            if (i >= windowSize - 1) {
                result[windowStart] = nums[indexDeque.peekFirst()];
            }
        }

        return result;
    }
}