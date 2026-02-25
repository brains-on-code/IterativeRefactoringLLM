package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

public final class MaximumMinimumWindow {

    private MaximumMinimumWindow() {}

    public static int[] calculateMaxOfMin(int[] values, int length) {
        Stack<Integer> indexStack = new Stack<>();
        int[] previousSmallerIndex = new int[length + 1];
        int[] nextSmallerIndex = new int[length + 1];

        for (int i = 0; i < length; i++) {
            previousSmallerIndex[i] = -1;
            nextSmallerIndex[i] = length;
        }

        for (int currentIndex = 0; currentIndex < length; currentIndex++) {
            while (!indexStack.empty() && values[indexStack.peek()] >= values[currentIndex]) {
                indexStack.pop();
            }
            if (!indexStack.empty()) {
                previousSmallerIndex[currentIndex] = indexStack.peek();
            }
            indexStack.push(currentIndex);
        }

        indexStack.clear();

        for (int currentIndex = length - 1; currentIndex >= 0; currentIndex--) {
            while (!indexStack.empty() && values[indexStack.peek()] >= values[currentIndex]) {
                indexStack.pop();
            }
            if (!indexStack.empty()) {
                nextSmallerIndex[currentIndex] = indexStack.peek();
            }
            indexStack.push(currentIndex);
        }

        int[] maxOfMinForWindowSize = new int[length + 1];
        Arrays.fill(maxOfMinForWindowSize, 0);

        for (int i = 0; i < length; i++) {
            int windowSize = nextSmallerIndex[i] - previousSmallerIndex[i] - 1;
            maxOfMinForWindowSize[windowSize] =
                Math.max(maxOfMinForWindowSize[windowSize], values[i]);
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
        int[] expectedResult = new int[] {70, 30, 20, 10, 10, 10, 10};
        int[] result = calculateMaxOfMin(inputArray, inputArray.length);
        assert Arrays.equals(expectedResult, result);
    }
}