package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

public final class MaximumMinimumWindow {

    private MaximumMinimumWindow() {
        // Utility class; prevent instantiation
    }

    public static int[] calculateMaxOfMin(int[] arr, int n) {
        Stack<Integer> stack = new Stack<>();
        int[] left = new int[n];
        int[] right = new int[n];

        // Initialize left and right boundaries
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

        int[] result = new int[n + 1];
        Arrays.fill(result, 0);

        // Fill result for each window length
        for (int i = 0; i < n; i++) {
            int windowLength = right[i] - left[i] - 1;
            result[windowLength] = Math.max(result[windowLength], arr[i]);
        }

        // Fill remaining entries by propagating maximums backwards
        for (int i = n - 1; i >= 1; i--) {
            result[i] = Math.max(result[i], result[i + 1]);
        }

        // Print results for window sizes 1..n
        for (int i = 1; i <= n; i++) {
            System.out.print(result[i] + " ");
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 50, 10, 70, 30};
        int[] expected = {70, 30, 20, 10, 10, 10, 10};
        int[] result = calculateMaxOfMin(arr, arr.length);
        assert Arrays.equals(expected, result);
    }
}