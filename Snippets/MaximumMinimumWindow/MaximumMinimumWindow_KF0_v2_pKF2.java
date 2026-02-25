package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

/**
 * For every window size from 1 to n, computes the maximum among all
 * window-minimums of that size.
 *
 * <p>Example:
 * <pre>
 * N = 7
 * arr = {10, 20, 30, 50, 10, 70, 30}
 * result = {70, 30, 20, 10, 10, 10, 10}
 * </pre>
 */
public final class MaximumMinimumWindow {

    private MaximumMinimumWindow() {
        // Prevent instantiation
    }

    /**
     * Returns an array {@code ans} of length {@code n + 1} where
     * {@code ans[len]} is the maximum of the minimum values of all
     * subarrays (windows) of length {@code len}, for {@code 1 <= len <= n}.
     * Index 0 is unused.
     *
     * @param arr the input array
     * @param n   the length of the array
     * @return result array of size {@code n + 1}; index 0 is unused
     */
    public static int[] calculateMaxOfMin(int[] arr, int n) {
        Stack<Integer> stack = new Stack<>();

        /*
         * left[i]  = index of previous smaller element for arr[i], or -1 if none
         * right[i] = index of next smaller element for arr[i], or n if none
         */
        int[] left = new int[n];
        int[] right = new int[n];

        Arrays.fill(left, -1);
        Arrays.fill(right, n);

        // Compute indices of previous smaller elements
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                left[i] = stack.peek();
            }
            stack.push(i);
        }

        stack.clear();

        // Compute indices of next smaller elements
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                right[i] = stack.peek();
            }
            stack.push(i);
        }

        int[] ans = new int[n + 1];

        /*
         * For each element arr[i], determine the length of the window
         * in which it is the minimum, and update the answer for that length.
         */
        for (int i = 0; i < n; i++) {
            int len = right[i] - left[i] - 1;
            ans[len] = Math.max(ans[len], arr[i]);
        }

        /*
         * For smaller window sizes, the answer cannot be less than that
         * for larger window sizes. Propagate values backwards.
         */
        for (int len = n - 1; len >= 1; len--) {
            ans[len] = Math.max(ans[len], ans[len + 1]);
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 50, 10, 70, 30};
        int[] target = {0, 70, 30, 20, 10, 10, 10, 10};
        int[] res = calculateMaxOfMin(arr, arr.length);
        assert Arrays.equals(target, res);
    }
}