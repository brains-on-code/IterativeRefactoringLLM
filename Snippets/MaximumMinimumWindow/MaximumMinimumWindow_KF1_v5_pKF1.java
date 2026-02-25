package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

public final class WindowMinima {

    private WindowMinima() {}

    public static int[] maxOfMinForEveryWindowSize(int[] values, int length) {
        Stack<Integer> indexStack = new Stack<>();
        int[] previousSmallerIndices = new int[length + 1];
        int[] nextSmallerIndices = new int[length + 1];

        for (int index = 0; index < length; index++) {
            previousSmallerIndices[index] = -1;
            nextSmallerIndices[index] = length;
        }

        for (int currentIndex = 0; currentIndex < length; currentIndex++) {
            while (!indexStack.empty() && values[indexStack.peek()] >= values[currentIndex]) {
                indexStack.pop();
            }

            if (!indexStack.empty()) {
                previousSmallerIndices[currentIndex] = indexStack.peek();
            }

            indexStack.push(currentIndex);
        }

        indexStack.clear();

        for (int currentIndex = length - 1; currentIndex >= 0; currentIndex--) {
            while (!indexStack.empty() && values[indexStack.peek()] >= values[currentIndex]) {
                indexStack.pop();
            }

            if (!indexStack.empty()) {
                nextSmallerIndices[currentIndex] = indexStack.peek();
            }

            indexStack.push(currentIndex);
        }

        int[] maxOfMinForWindowSize = new int[length + 1];
        for (int windowSize = 0; windowSize <= length; windowSize++) {
            maxOfMinForWindowSize[windowSize] = 0;
        }

        for (int index = 0; index < length; index++) {
            int windowSize = nextSmallerIndices[index] - previousSmallerIndices[index] - 1;
            maxOfMinForWindowSize[windowSize] =
                    Math.max(maxOfMinForWindowSize[windowSize], values[index]);
        }

        for (int windowSize = length - 1; windowSize >= 1; windowSize--) {
            maxOfMinForWindowSize[windowSize] =
                    Math.max(maxOfMinForWindowSize[windowSize], maxOfMinForWindowSize[windowSize + 1]);
        }

        for (int windowSize = 1; windowSize <= length; windowSize++) {
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