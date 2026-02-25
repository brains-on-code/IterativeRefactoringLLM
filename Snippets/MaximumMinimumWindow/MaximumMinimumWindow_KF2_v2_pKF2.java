package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

public final class MaximumMinimumWindow {

    private MaximumMinimumWindow() {
        // Prevent instantiation
    }

    /**
     * For each window size from 1 to n, computes the maximum among all
     * minimum values of subarrays (windows) of that size.
     *
     * @param arr input array
     * @param n   length of the input array
     * @return array result where result[len] is the maximum of minimums for windows of size len.
     *         Note: result[0] is unused.
     */
    public static int[] calculateMaxOfMin(int[] arr, int n) {
        Stack<Integer> stack = new Stack<>();

        // left[i]  = index of previous smaller element for arr[i]
        // right[i] = index of next smaller element for arr[i]
        int[] left = new int[n];
        int[] right = new int[n];

        Arrays.fill(left, -1);
        Arrays.fill(right, n);

        // Compute indices of previous smaller elements
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

        // Compute indices of next smaller elements
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.empty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.empty()) {
                right[i] = stack.peek();
            }
            stack.push(i);
        }

        // result[len] stores the maximum of minimums for windows of size len
        int[] result = new int[n + 1];

        // For each element arr[i], determine the window length in which it is the minimum
        // and update result for that length.
        for (int i = 0; i < n; i++) {
            int len = right[i] - left[i] - 1;
            result[len] = Math.max(result[len], arr[i]);
        }

        // Ensure non-increasing behavior for smaller window sizes:
        // result[i] >= result[i + 1]
        for (int i = n - 1; i >= 1; i--) {
            result[i] = Math.max(result[i], result[i + 1]);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {10, 20, 30, 50, 10, 70, 30};
        int[] expected = new int[] {0, 70, 30, 20, 10, 10, 10, 10};
        int[] result = calculateMaxOfMin(arr, arr.length);
        assert Arrays.equals(expected, result);
    }
}