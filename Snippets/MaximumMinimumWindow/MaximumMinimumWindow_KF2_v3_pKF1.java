package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

public final class MaximumMinimumWindow {

    private MaximumMinimumWindow() {}

    public static int[] calculateMaxOfMin(int[] array, int n) {
        Stack<Integer> indexStack = new Stack<>();
        int[] previousSmallerIndex = new int[n + 1];
        int[] nextSmallerIndex = new int[n + 1];

        for (int i = 0; i < n; i++) {
            previousSmallerIndex[i] = -1;
            nextSmallerIndex[i] = n;
        }

        for (int i = 0; i < n; i++) {
            while (!indexStack.empty() && array[indexStack.peek()] >= array[i]) {
                indexStack.pop();
            }
            if (!indexStack.empty()) {
                previousSmallerIndex[i] = indexStack.peek();
            }
            indexStack.push(i);
        }

        indexStack.clear();

        for (int i = n - 1; i >= 0; i--) {
            while (!indexStack.empty() && array[indexStack.peek()] >= array[i]) {
                indexStack.pop();
            }
            if (!indexStack.empty()) {
                nextSmallerIndex[i] = indexStack.peek();
            }
            indexStack.push(i);
        }

        int[] maxOfMinForWindowSize = new int[n + 1];
        Arrays.fill(maxOfMinForWindowSize, 0);

        for (int i = 0; i < n; i++) {
            int windowSize = nextSmallerIndex[i] - previousSmallerIndex[i] - 1;
            maxOfMinForWindowSize[windowSize] =
                Math.max(maxOfMinForWindowSize[windowSize], array[i]);
        }

        for (int windowSize = n - 1; windowSize >= 1; windowSize--) {
            maxOfMinForWindowSize[windowSize] =
                Math.max(maxOfMinForWindowSize[windowSize], maxOfMinForWindowSize[windowSize + 1]);
        }

        for (int windowSize = 1; windowSize <= n; windowSize++) {
            System.out.print(maxOfMinForWindowSize[windowSize] + " ");
        }

        return maxOfMinForWindowSize;
    }

    public static void main(String[] args) {
        int[] inputArray = new int[] {10, 20, 30, 50, 10, 70, 30};
        int[] expectedResult = new int[] {70, 30, 20, 10, 10, 10, 10};
        int[] result = calculateMaxOfMin(inputArray, inputArray.length);
        assert Arrays.equals(expectedResult, result);
    }
}