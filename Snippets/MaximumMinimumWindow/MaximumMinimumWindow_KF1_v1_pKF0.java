package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

/**
 * Utility class for computing, for every window size, the maximum of the minimum
 * values of all subarrays of that size.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * For each window size from 1 to n (where n = length), computes the maximum
     * among all minimums of subarrays of that size.
     *
     * @param arr    input array
     * @param length length of the input array
     * @return array result where result[k] is the maximum of minimums of all
     *         subarrays of size k (1 <= k <= length). result[0] is unused.
     */
    public static int[] method1(int[] arr, int length) {
        Stack<Integer> stack = new Stack<>();

        int[] previousSmaller = new int[length + 1];
        int[] nextSmaller = new int[length + 1];

        // Initialize previousSmaller and nextSmaller
        for (int i = 0; i < length; i++) {
            previousSmaller[i] = -1;
            nextSmaller[i] = length;
        }

        // Compute previous smaller element indices
        for (int i = 0; i < length; i++) {
            while (!stack.empty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.empty()) {
                previousSmaller[i] = stack.peek();
            }
            stack.push(i);
        }

        stack.clear();

        // Compute next smaller element indices
        for (int i = length - 1; i >= 0; i--) {
            while (!stack.empty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.empty()) {
                nextSmaller[i] = stack.peek();
            }
            stack.push(i);
        }

        int[] result = new int[length + 1];
        Arrays.fill(result, 0);

        // Fill result for window sizes where arr[i] is the minimum
        for (int i = 0; i < length; i++) {
            int windowSize = nextSmaller[i] - previousSmaller[i] - 1;
            result[windowSize] = Math.max(result[windowSize], arr[i]);
        }

        // Propagate maximums to smaller window sizes
        for (int i = length - 1; i >= 1; i--) {
            result[i] = Math.max(result[i], result[i + 1]);
        }

        for (int i = 1; i <= length; i++) {
            System.out.print(result[i] + " ");
        }

        return result;
    }

    public static void method2(String[] args) {
        int[] input = {10, 20, 30, 50, 10, 70, 30};
        int[] expected = {70, 30, 20, 10, 10, 10, 10};
        int[] actual = method1(input, input.length);
        assert Arrays.equals(expected, actual);
    }
}