package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

public final class MaximumMinimumWindow {

    private MaximumMinimumWindow() {}

    public static int[] calculateMaxOfMin(int[] array, int length) {
        Stack<Integer> indexStack = new Stack<>();
        int[] previousSmallerIndex = new int[length + 1];
        int[] nextSmallerIndex = new int[length + 1];

        for (int i = 0; i < length; i++) {
            previousSmallerIndex[i] = -1;
            nextSmallerIndex[i] = length;
        }

        for (int i = 0; i < length; i++) {
            while (!indexStack.empty() && array[indexStack.peek()] >= array[i]) {
                indexStack.pop();
            }
            if (!indexStack.empty()) {
                previousSmallerIndex[i] = indexStack.peek();
            }
            indexStack.push(i);
        }

        while (!indexStack.empty()) {
            indexStack.pop();
        }

        for (int i = length - 1; i >= 0; i--) {
            while (!indexStack.empty() && array[indexStack.peek()] >= array[i]) {
                indexStack.pop();
            }
            if (!indexStack.empty()) {
                nextSmallerIndex[i] = indexStack.peek();
            }
            indexStack.push(i);
        }

        int[] maxOfMinForWindowSize = new int[length + 1];
        for (int i = 0; i <= length; i++) {
            maxOfMinForWindowSize[i] = 0;
        }

        for (int i = 0; i < length; i++) {
            int windowSize = nextSmallerIndex[i] - previousSmallerIndex[i] - 1;
            maxOfMinForWindowSize[windowSize] =
                Math.max(maxOfMinForWindowSize[windowSize], array[i]);
        }

        for (int i = length - 1; i >= 1; i--) {
            maxOfMinForWindowSize[i] =
                Math.max(maxOfMinForWindowSize[i], maxOfMinForWindowSize[i + 1]);
        }

        for (int i = 1; i <= length; i++) {
            System.out.print(maxOfMinForWindowSize[i] + " ");
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