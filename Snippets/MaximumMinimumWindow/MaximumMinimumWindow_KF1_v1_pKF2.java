package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

/**
 * Utility class for computing, for every possible window size in an array,
 * the maximum among all window minimums.
 *
 * Given an array arr of length n, this class can compute an array result
 * of length n + 1 (1-based for window sizes) where:
 *   result[k] = maximum of all minimum values of every contiguous subarray
 *               (window) of size k, for 1 <= k <= n.
 *
 * Example:
 *   n = 7
 *   arr = {10, 20, 30, 50, 10, 70, 30}
 *
 *   All windows and their minimums:
 *   size 1: [10], [20], [30], [50], [10], [70], [30]
 *           mins -> 10, 20, 30, 50, 10, 70, 30
 *           max of mins -> 70
 *
 *   size 2: [10,20], [20,30], [30,50], [50,10], [10,70], [70,30]
 *           mins -> 10, 20, 30, 10, 10, 30
 *           max of mins -> 30
 *
 *   size 3: [10,20,30], [20,30,50], [30,50,10], [50,10,70], [10,70,30]
 *           mins -> 10, 20, 10, 10, 10
 *           max of mins -> 20
 *
 *   size 4: [10,20,30,50], [20,30,50,10], [30,50,10,70], [50,10,70,30]
 *           mins -> 10, 10, 10, 10
 *           max of mins -> 10
 *
 *   size 5: [10,20,30,50,10], [20,30,50,10,70], [30,50,10,70,30]
 *           mins -> 10, 10, 10
 *           max of mins -> 10
 *
 *   size 6: [10,20,30,50,10,70], [20,30,50,10,70,30]
 *           mins -> 10, 10
 *           max of mins -> 10
 *
 *   size 7: [10,20,30,50,10,70,30]
 *           mins -> 10
 *           max of mins -> 10
 *
 *   Result (for k = 1..7):
 *   {70, 30, 20, 10, 10, 10, 10}
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes, for every window size from 1 to n, the maximum of all
     * minimum values of contiguous subarrays (windows) of that size.
     *
     * @param arr the input array
     * @param n   the length of the array
     * @return an array result of length n + 1 where result[k] is the
     *         maximum of all window minimums for window size k
     */
    public static int[] method1(int[] arr, int n) {
        Stack<Integer> stack = new Stack<>();

        // Arrays to store indices of previous and next smaller elements
        int[] prevSmaller = new int[n + 1];
        int[] nextSmaller = new int[n + 1];

        // Initialize previous smaller as -1 and next smaller as n
        for (int i = 0; i < n; i++) {
            prevSmaller[i] = -1;
            nextSmaller[i] = n;
        }

        // Compute previous smaller element indices
        for (int i = 0; i < n; i++) {
            while (!stack.empty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.empty()) {
                prevSmaller[i] = stack.peek();
            }
            stack.push(i);
        }

        // Clear stack to reuse for next smaller computation
        stack.clear();

        // Compute next smaller element indices
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.empty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.empty()) {
                nextSmaller[i] = stack.peek();
            }
            stack.push(i);
        }

        // result[len] will store the maximum of minimums for windows of size len
        int[] result = new int[n + 1];
        Arrays.fill(result, 0);

        // For each element, determine the length of the window where it is minimum
        for (int i = 0; i < n; i++) {
            int len = nextSmaller[i] - prevSmaller[i] - 1;
            result[len] = Math.max(result[len], arr[i]);
        }

        // Fill remaining entries: for smaller window sizes, the answer
        // cannot be smaller than for larger window sizes
        for (int len = n - 1; len >= 1; len--) {
            result[len] = Math.max(result[len], result[len + 1]);
        }

        // Print results for window sizes 1..n
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