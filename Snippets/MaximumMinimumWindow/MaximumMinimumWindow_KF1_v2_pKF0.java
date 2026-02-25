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

        // Compute previous smaller element indices
        for (int i = 0; i < length; i++) {
            while (!indicesStack.isEmpty() && arr[indicesStack.peek()] >= arr[i]) {
                indicesStack.pop();
            }
            if (!indicesStack.isEmpty()) {
                previousSmallerIndex[i] = indicesStack.peek();
            }
            indicesStack.push(i);
        }

        indicesStack.clear();

        // Compute next smaller element indices
        for (int i = length - 1; i >= 0; i--) {
            while (!indicesStack.isEmpty() && arr[indicesStack.peek()] >= arr[i]) {
                indicesStack.pop();
            }
            if (!indicesStack.isEmpty()) {
                nextSmallerIndex[i] = indicesStack.peek();
            }
            indicesStack.push(i);
        }

        int[] result = new int[length + 1];
        Arrays.fill(result, 0);

        // Fill result for window sizes where arr[i] is the minimum
        for (int i = 0; i < length; i++) {
            int windowSize = nextSmallerIndex[i] - previousSmallerIndex[i] - 1;
            result[windowSize] = Math.max(result[windowSize], arr[i]);
        }

        // Propagate maximums to smaller window sizes
        for (int i = length - 1; i >= 1; i--) {
            result[i] = Math.max(result[i], result[i + 1]);
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