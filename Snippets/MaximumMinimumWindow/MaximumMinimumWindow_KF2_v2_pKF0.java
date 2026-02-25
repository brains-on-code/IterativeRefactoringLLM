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
        Arrays.fill(maxOfMinForWindowSize, 0);

        fillMaxOfMinForEachWindow(arr, previousSmaller, nextSmaller, maxOfMinForWindowSize);
        propagateMaxValuesBackward(maxOfMinForWindowSize, n);

        printResults(maxOfMinForWindowSize, n);

        return maxOfMinForWindowSize;
    }

    private static void computePreviousSmallerIndices(int[] arr, int[] previousSmaller) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            while (!stack.empty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.empty()) {
                previousSmaller[i] = stack.peek();
            }
            stack.push(i);
        }
    }

    private static void computeNextSmallerIndices(int[] arr, int[] nextSmaller) {
        Stack<Integer> stack = new Stack<>();

        for (int i = arr.length - 1; i >= 0; i--) {
            while (!stack.empty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            if (!stack.empty()) {
                nextSmaller[i] = stack.peek();
            }
            stack.push(i);
        }
    }

    private static void fillMaxOfMinForEachWindow(
            int[] arr, int[] previousSmaller, int[] nextSmaller, int[] maxOfMinForWindowSize) {

        for (int i = 0; i < arr.length; i++) {
            int windowLength = nextSmaller[i] - previousSmaller[i] - 1;
            maxOfMinForWindowSize[windowLength] =
                    Math.max(maxOfMinForWindowSize[windowLength], arr[i]);
        }
    }

    private static void propagateMaxValuesBackward(int[] maxOfMinForWindowSize, int n) {
        for (int i = n - 1; i >= 1; i--) {
            maxOfMinForWindowSize[i] =
                    Math.max(maxOfMinForWindowSize[i], maxOfMinForWindowSize[i + 1]);
        }
    }

    private static void printResults(int[] maxOfMinForWindowSize, int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print(maxOfMinForWindowSize[i] + " ");
        }
    }

    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 50, 10, 70, 30};
        int[] expected = {70, 30, 20, 10, 10, 10, 10};
        int[] result = calculateMaxOfMin(arr, arr.length);
        assert Arrays.equals(expected, result);
    }
}