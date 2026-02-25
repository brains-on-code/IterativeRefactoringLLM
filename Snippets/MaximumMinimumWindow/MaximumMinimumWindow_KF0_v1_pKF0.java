package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

/**
 * Given an integer array, find the maximum of the minimum of every window size
 * in the array. Window size varies from 1 to the size of the array.
 *
 * <p>Example:
 *
 * <pre>
 * N = 7
 * arr[] = {10, 20, 30, 50, 10, 70, 30}
 * Output: 70 30 20 10 10 10 10
 * </pre>
 *
 * @author sahil
 */
public final class MaximumMinimumWindow {

    private MaximumMinimumWindow() {
        // Utility class
    }

    /**
     * Finds the maximum of minimum values for every window size using a stack.
     *
     * @param arr the input array
     * @param n   the length of the array
     * @return an array where result[i] is the maximum of minimums for window size i
     */
    public static int[] calculateMaxOfMin(int[] arr, int n) {
        Stack<Integer> stack = new Stack<>();
        int[] left = new int[n];
        int[] right = new int[n];

        // Initialize left and right boundaries
        Arrays.fill(left, -1);
        Arrays.fill(right, n);

        // Compute previous smaller element indices
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

        // Compute next smaller element indices
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                right[i] = stack.peek();
            }
            stack.push(i);
        }

        int[] result = new int[n + 1];
        Arrays.fill(result, 0);

        // Fill result for lengths where arr[i] is the minimum
        for (int i = 0; i < n; i++) {
            int windowLength = right[i] - left[i] - 1;
            result[windowLength] = Math.max(result[windowLength], arr[i]);
        }

        // Fill remaining entries by propagating maximums backwards
        for (int i = n - 1; i >= 1; i--) {
            result[i] = Math.max(result[i], result[i + 1]);
        }

        // Print the result for window sizes 1..n
        for (int i = 1; i <= n; i++) {
            System.out.print(result[i] + " ");
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 50, 10, 70, 30};
        int[] expected = {70, 30, 20, 10, 10, 10, 10};
        int[] actual = calculateMaxOfMin(arr, arr.length);
        assert Arrays.equals(expected, actual);
    }
}