package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

public final class WindowMinima {

    private WindowMinima() {}

    public static int[] maxOfMinForEveryWindowSize(int[] array, int n) {
        Stack<Integer> indexStack = new Stack<>();
        int[] previousSmallerIndex = new int[n + 1];
        int[] nextSmallerIndex = new int[n + 1];

        for (int i = 0; i < n; i++) {
            previousSmallerIndex[i] = -1;
            nextSmallerIndex[i] = n;
        }

        for (int currentIndex = 0; currentIndex < n; currentIndex++) {
            while (!indexStack.empty() && array[indexStack.peek()] >= array[currentIndex]) {
                indexStack.pop();
            }

            if (!indexStack.empty()) {
                previousSmallerIndex[currentIndex] = indexStack.peek();
            }

            indexStack.push(currentIndex);
        }

        indexStack.clear();

        for (int currentIndex = n - 1; currentIndex >= 0; currentIndex--) {
            while (!indexStack.empty() && array[indexStack.peek()] >= array[currentIndex]) {
                indexStack.pop();
            }

            if (!indexStack.empty()) {
                nextSmallerIndex[currentIndex] = indexStack.peek();
            }

            indexStack.push(currentIndex);
        }

        int[] maxOfMinForWindowSize = new int[n + 1];
        for (int windowSize = 0; windowSize <= n; windowSize++) {
            maxOfMinForWindowSize[windowSize] = 0;
        }

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
        int[] expectedOutput = new int[] {70, 30, 20, 10, 10, 10, 10};
        int[] actualOutput = maxOfMinForEveryWindowSize(inputArray, inputArray.length);
        assert Arrays.equals(expectedOutput, actualOutput);
    }
}