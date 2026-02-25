package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

public final class MaximumMinimumWindow {

    private MaximumMinimumWindow() {
        // Utility class; prevent instantiation
    }

    /**
     * For each window size from 1 to n, computes the maximum among all
     * minimum values of subarrays (windows) of that size.
     *
     * @param arr input array
     * @param n   length of the input array
     * @return array ans where ans[len] is the maximum of minimums for windows of size len.
     *         Note: ans[0] is unused.
     */
    public static int[] calculateMaxOfMin(int[] arr, int n) {
        Stack<Integer> stack = new Stack<>();

        // left[i]  = index of previous smaller element for arr[i]
        // right[i] = index of next smaller element for arr[i]
        int[] left = new int[n];
        int[] right = new int[n];

        Arrays.fill(left, -1);
        Arrays.fill(right, n);

        // Compute previous smaller element indices
        for (int i = 0; i < n; i++) {
            while (!stack.empty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.empty()) {
                left[i] = stack.peek();
            }
            stack.push(i);
        }

        stack.clear();

        // Compute next smaller element indices
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.empty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.empty()) {
                right[i] = stack.peek();
            }
            stack.push(i);
        }

        // ans[len] will store the maximum of minimums for windows of size len
        int[] ans = new int[n + 1];

        // For each element arr[i], determine the length of the window
        // in which it is the minimum, and update ans for that length.
        for (int i = 0; i < n; i++) {
            int len = right[i] - left[i] - 1;
            ans[len] = Math.max(ans[len], arr[i]);
        }

        // Fill remaining entries: for smaller window sizes, the answer
        // cannot be smaller than for larger window sizes.
        for (int i = n - 1; i >= 1; i--) {
            ans[i] = Math.max(ans[i], ans[i + 1]);
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {10, 20, 30, 50, 10, 70, 30};
        int[] expected = new int[] {0, 70, 30, 20, 10, 10, 10, 10};
        int[] result = calculateMaxOfMin(arr, arr.length);
        assert Arrays.equals(expected, result);
    }
}