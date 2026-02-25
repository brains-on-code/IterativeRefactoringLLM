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
     * @return an array of length n where result[i] is the maximum of minimums
     *         for window size (i + 1)
     */
    public static int[] calculateMaxOfMin(int[] arr, int n) {
        if (arr == null || n <= 0) {
            return new int[0];
        }

        int[] leftSmallerIndex = new int[n];
        int[] rightSmallerIndex = new int[n];

        Arrays.fill(leftSmallerIndex, -1);
        Arrays.fill(rightSmallerIndex, n);

        computePreviousSmallerIndices(arr, leftSmallerIndex);
        computeNextSmallerIndices(arr, rightSmallerIndex);

        int[] windowMaxOfMin = new int[n + 1];
        Arrays.fill(windowMaxOfMin, 0);

        populateWindowMaxOfMin(arr, leftSmallerIndex, rightSmallerIndex, windowMaxOfMin);
        propagateMaxValues(windowMaxOfMin, n);

        // Return only window sizes 1..n as a zero-based array of length n
        return Arrays.copyOfRange(windowMaxOfMin, 1, n + 1);
    }

    private static void computePreviousSmallerIndices(int[] arr, int[] leftSmallerIndex) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                leftSmallerIndex[i] = stack.peek();
            }
            stack.push(i);
        }
    }

    private static void computeNextSmallerIndices(int[] arr, int[] rightSmallerIndex) {
        Stack<Integer> stack = new Stack<>();

        for (int i = arr.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                rightSmallerIndex[i] = stack.peek();
            }
            stack.push(i);
        }
    }

    private static void populateWindowMaxOfMin(
            int[] arr, int[] leftSmallerIndex, int[] rightSmallerIndex, int[] windowMaxOfMin) {

        for (int i = 0; i < arr.length; i++) {
            int windowLength = rightSmallerIndex[i] - leftSmallerIndex[i] - 1;
            windowMaxOfMin[windowLength] = Math.max(windowMaxOfMin[windowLength], arr[i]);
        }
    }

    private static void propagateMaxValues(int[] windowMaxOfMin, int n) {
        for (int i = n - 1; i >= 1; i--) {
            windowMaxOfMin[i] = Math.max(windowMaxOfMin[i], windowMaxOfMin[i + 1]);
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 50, 10, 70, 30};
        int[] expected = {70, 30, 20, 10, 10, 10, 10};
        int[] actual = calculateMaxOfMin(arr, arr.length);
        assert Arrays.equals(expected, actual);
    }
}