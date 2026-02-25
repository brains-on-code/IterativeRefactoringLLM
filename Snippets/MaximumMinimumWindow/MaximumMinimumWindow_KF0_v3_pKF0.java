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

        int[] previousSmaller = new int[n];
        int[] nextSmaller = new int[n];

        Arrays.fill(previousSmaller, -1);
        Arrays.fill(nextSmaller, n);

        fillPreviousSmallerIndices(arr, previousSmaller);
        fillNextSmallerIndices(arr, nextSmaller);

        int[] windowMaxOfMin = new int[n + 1];

        fillWindowMaxOfMin(arr, previousSmaller, nextSmaller, windowMaxOfMin);
        propagateMaxValues(windowMaxOfMin, n);

        return Arrays.copyOfRange(windowMaxOfMin, 1, n + 1);
    }

    private static void fillPreviousSmallerIndices(int[] arr, int[] previousSmaller) {
        Stack<Integer> indices = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            while (!indices.isEmpty() && arr[indices.peek()] >= arr[i]) {
                indices.pop();
            }
            if (!indices.isEmpty()) {
                previousSmaller[i] = indices.peek();
            }
            indices.push(i);
        }
    }

    private static void fillNextSmallerIndices(int[] arr, int[] nextSmaller) {
        Stack<Integer> indices = new Stack<>();

        for (int i = arr.length - 1; i >= 0; i--) {
            while (!indices.isEmpty() && arr[indices.peek()] >= arr[i]) {
                indices.pop();
            }
            if (!indices.isEmpty()) {
                nextSmaller[i] = indices.peek();
            }
            indices.push(i);
        }
    }

    private static void fillWindowMaxOfMin(
            int[] arr, int[] previousSmaller, int[] nextSmaller, int[] windowMaxOfMin) {

        for (int i = 0; i < arr.length; i++) {
            int windowLength = nextSmaller[i] - previousSmaller[i] - 1;
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