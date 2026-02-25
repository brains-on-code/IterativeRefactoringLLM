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
        Stack<Integer> indicesStack = new Stack<>();

        int[] previousSmallerIndex = new int[length];
        int[] nextSmallerIndex = new int[length];

        Arrays.fill(previousSmallerIndex, -1);
        Arrays.fill(nextSmallerIndex, length);

        computePreviousSmallerIndices(arr, indicesStack, previousSmallerIndex);
        indicesStack.clear();
        computeNextSmallerIndices(arr, indicesStack, nextSmallerIndex);

        int[] result = new int[length + 1];
        Arrays.fill(result, 0);

        fillResultWithWindowMinimums(arr, previousSmallerIndex, nextSmallerIndex, result);
        propagateMaximumsToSmallerWindows(result);

        return result;
    }

    private static void computePreviousSmallerIndices(
            int[] arr, Stack<Integer> stack, int[] previousSmallerIndex) {

        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            previousSmallerIndex[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
    }

    private static void computeNextSmallerIndices(
            int[] arr, Stack<Integer> stack, int[] nextSmallerIndex) {

        for (int i = arr.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            nextSmallerIndex[i] = stack.isEmpty() ? arr.length : stack.peek();
            stack.push(i);
        }
    }

    private static void fillResultWithWindowMinimums(
            int[] arr,
            int[] previousSmallerIndex,
            int[] nextSmallerIndex,
            int[] result) {

        for (int i = 0; i < arr.length; i++) {
            int windowSize = nextSmallerIndex[i] - previousSmallerIndex[i] - 1;
            result[windowSize] = Math.max(result[windowSize], arr[i]);
        }
    }

    private static void propagateMaximumsToSmallerWindows(int[] result) {
        for (int i = result.length - 2; i >= 1; i--) {
            result[i] = Math.max(result[i], result[i + 1]);
        }
    }

    public static void method2(String[] args) {
        int[] input = {10, 20, 30, 50, 10, 70, 30};
        int[] expected = {70, 30, 20, 10, 10, 10, 10};
        int[] actual = method1(input, input.length);
        assert Arrays.equals(expected, actual);
    }
}