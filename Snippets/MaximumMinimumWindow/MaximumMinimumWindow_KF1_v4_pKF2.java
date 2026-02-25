package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

/**
 * Computes, for every window size in an array, the maximum among all window minimums.
 *
 * For an array {@code arr} of length {@code n}, this class computes an array
 * {@code result} of length {@code n + 1} (1-based for window sizes) where:
 *
 * <pre>
 * result[k] = maximum of all minimum values of every contiguous subarray
 *             (window) of size k, for 1 <= k <= n.
 * </pre>
 *
 * Example:
 * <pre>
 * n = 7
 * arr = {10, 20, 30, 50, 10, 70, 30}
 *
 * Result (for k = 1..7):
 * {70, 30, 20, 10, 10, 10, 10}
 * </pre>
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation.
    }

    /**
     * Computes, for every window size from 1 to n, the maximum of all
     * minimum values of contiguous subarrays (windows) of that size.
     *
     * @param arr the input array
     * @param n   the length of the array
     * @return an array {@code result} of length {@code n + 1} where
     *         {@code result[k]} is the maximum of all window minimums
     *         for window size {@code k}, for {@code 1 <= k <= n}
     */
    public static int[] method1(int[] arr, int n) {
        Stack<Integer> stack = new Stack<>();

        /*
         * For each index i:
         *   prevSmaller[i] = index of the previous element strictly smaller than arr[i],
         *                    or -1 if none exists.
         *   nextSmaller[i] = index of the next element strictly smaller than arr[i],
         *                    or n if none exists.
         *
         * These bounds define the maximum window in which arr[i] is the minimum.
         */
        int[] prevSmaller = new int[n + 1];
        int[] nextSmaller = new int[n + 1];

        // Default: no previous smaller -> -1, no next smaller -> n.
        for (int i = 0; i < n; i++) {
            prevSmaller[i] = -1;
            nextSmaller[i] = n;
        }

        // Previous smaller elements (monotonic increasing stack).
        for (int i = 0; i < n; i++) {
            while (!stack.empty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.empty()) {
                prevSmaller[i] = stack.peek();
            }
            stack.push(i);
        }

        // Next smaller elements (reuse stack).
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.empty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.empty()) {
                nextSmaller[i] = stack.peek();
            }
            stack.push(i);
        }

        /*
         * result[len] = maximum among all minimum values of windows of size len,
         * for 1 <= len <= n (1-based by window size).
         */
        int[] result = new int[n + 1];
        Arrays.fill(result, 0);

        /*
         * For each element arr[i], the largest window in which it is the minimum is:
         *   len = nextSmaller[i] - prevSmaller[i] - 1
         * Update the best (maximum) minimum for that window length.
         */
        for (int i = 0; i < n; i++) {
            int len = nextSmaller[i] - prevSmaller[i] - 1;
            result[len] = Math.max(result[len], arr[i]);
        }

        /*
         * Ensure non-increasing answers as window size decreases:
         * result[len] >= result[len + 1] for all valid len.
         */
        for (int len = n - 1; len >= 1; len--) {
            result[len] = Math.max(result[len], result[len + 1]);
        }

        // Print results for window sizes 1..n.
        for (int len = 1; len <= n; len++) {
            System.out.print(result[len] + " ");
        }

        return result;
    }

    public static void method2(String[] args) {
        int[] input = new int[] {10, 20, 30, 50, 10, 70, 30};
        int[] expected = new int[] {70, 30, 20, 10, 10, 10, 10};
        int[] actual = method1(input, input.length);
        assert Arrays.equals(expected, actual);
    }
}