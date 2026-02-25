package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

public final class MaximumMinimumWindow {

    private MaximumMinimumWindow() {
        // Utility class; prevent instantiation
    }

    public static int[] calculateMaxOfMin(int[] arr, int n) {
        int[] previousSmaller = new int[n];
        int[] nextSmaller = new int[n];

        Arrays.fill(previousSmaller, -1);
        Arrays.fill(nextSmaller, n);

        computePreviousSmallerIndices(arr, previousSmaller);
        computeNextSmallerIndices(arr, nextSmaller);

        int[] maxOfMinForWindowSize = new int[n + 1];

        fillMaxOfMinForEachWindow(arr, previousSmaller, nextSmaller, maxOfMinForWindowSize);
        propagateMaxValuesBackward(maxOfMinForWindowSize, n);

        return maxOfMinForWindowSize;
    }

    private static void computePreviousSmallerIndices(int[] arr, int[] previousSmaller) {
        Stack<Integer> indices = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            while (!indices.isEmpty() && arr[indices.peek()] >= arr[i]) {
                indices.pop();
            }
            previousSmaller[i] = indices.isEmpty() ? -1 : indices.peek();
            indices.push(i);
        }
    }

    private static void computeNextSmallerIndices(int[] arr, int[] nextSmaller) {
        Stack<Integer> indices = new Stack<>();

        for (int i = arr.length - 1; i >= 0; i--) {
            while (!indices.isEmpty() && arr[indices.peek()] >= arr[i]) {
                indices.pop();
            }
            nextSmaller[i] = indices.isEmpty() ? arr.length : indices.peek();
            indices.push(i);
        }
    }

    private static void fillMaxOfMinForEachWindow(
            int[] arr, int[] previousSmaller, int[] nextSmaller, int[] maxOfMinForWindowSize) {

        for (int i = 0; i < arr.length; i++) {
            int windowLength = nextSmaller[i] - previousSmaller[i] - 1;
            int currentValue = arr[i];
            int currentMax = maxOfMinForWindowSize[windowLength];
            maxOfMinForWindowSize[windowLength] = Math.max(currentMax, currentValue);
        }
    }

    private static void propagateMaxValuesBackward(int[] maxOfMinForWindowSize, int n) {
        for (int i = n - 1; i >= 1; i--) {
            int currentMax = maxOfMinForWindowSize[i];
            int nextMax = maxOfMinForWindowSize[i + 1];
            maxOfMinForWindowSize[i] = Math.max(currentMax, nextMax);
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 50, 10, 70, 30};
        int[] expected = {70, 30, 20, 10, 10, 10, 10};
        int[] result = calculateMaxOfMin(arr, arr.length);
        assert Arrays.equals(expected, result);
    }
}