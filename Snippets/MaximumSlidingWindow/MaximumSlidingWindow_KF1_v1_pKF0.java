package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class Class1 {

    /**
     * Returns an array of the maximum values in each sliding window of size windowSize
     * over the input array nums.
     *
     * @param nums       the input array
     * @param windowSize the size of the sliding window
     * @return an array of maximums for each window; empty array if input is invalid
     */
    public int[] slidingWindowMaximum(int[] nums, int windowSize) {
        if (nums == null || nums.length == 0 || windowSize <= 0 || windowSize > nums.length) {
            return new int[0];
        }

        int[] result = new int[nums.length - windowSize + 1];
        Deque<Integer> indexDeque = new ArrayDeque<>();

        for (int i = 0; i < nums.length; i++) {
            // Remove indices that are out of the current window
            if (!indexDeque.isEmpty() && indexDeque.peekFirst() == i - windowSize) {
                indexDeque.pollFirst();
            }

            // Remove indices whose corresponding values are less than current value
            while (!indexDeque.isEmpty() && nums[indexDeque.peekLast()] < nums[i]) {
                indexDeque.pollLast();
            }

            // Add current index
            indexDeque.offerLast(i);

            // Record the maximum for the current window
            if (i >= windowSize - 1) {
                result[i - windowSize + 1] = nums[indexDeque.peekFirst()];
            }
        }

        return result;
    }
}