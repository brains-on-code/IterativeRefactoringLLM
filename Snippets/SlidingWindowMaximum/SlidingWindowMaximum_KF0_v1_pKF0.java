package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

/**
 * The {@code SlidingWindowMaximum} class provides a method to efficiently compute
 * the maximum element within every sliding window of size {@code k} in a given array.
 *
 * <p>The algorithm uses a deque to maintain the indices of useful elements within
 * the current sliding window. The time complexity of this approach is O(n) since
 * each element is processed at most twice.
 *
 * author Hardvan
 */
public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns an array of the maximum values for each sliding window of size {@code k}.
     *
     * <p>If {@code nums} has fewer elements than {@code k}, the result will be an empty array.
     *
     * <p>Example:
     * <pre>
     * Input: nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
     * Output: [3, 3, 5, 5, 6, 7]
     * </pre>
     *
     * @param nums the input array of integers
     * @param k    the size of the sliding window
     * @return an array containing the maximum element for each sliding window
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || k <= 0 || nums.length < k) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> windowIndices = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            int windowStart = i - k + 1;

            // Remove indices that are out of the current window
            if (!windowIndices.isEmpty() && windowIndices.peekFirst() < windowStart) {
                windowIndices.pollFirst();
            }

            // Remove indices whose corresponding values are less than current value
            while (!windowIndices.isEmpty() && nums[windowIndices.peekLast()] < nums[i]) {
                windowIndices.pollLast();
            }

            // Add current index
            windowIndices.offerLast(i);

            // Record the maximum for the current window
            if (windowStart >= 0) {
                result[windowStart] = nums[windowIndices.peekFirst()];
            }
        }

        return result;
    }
}