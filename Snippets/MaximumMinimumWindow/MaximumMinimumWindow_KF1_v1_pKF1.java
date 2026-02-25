package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

public final class Class1 {
    private Class1() {
    }

    public static int[] maxOfMinForEveryWindowSize(int[] array, int length) {
        Stack<Integer> indexStack = new Stack<>();
        int[] previousSmaller = new int[length + 1];
        int[] nextSmaller = new int[length + 1];

        for (int i = 0; i < length; i++) {
            previousSmaller[i] = -1;
            nextSmaller[i] = length;
        }

        for (int i = 0; i < length; i++) {
            while (!indexStack.empty() && array[indexStack.peek()] >= array[i]) {
                indexStack.pop();
            }

            if (!indexStack.empty()) {
                previousSmaller[i] = indexStack.peek();
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
                nextSmaller[i] = indexStack.peek();
            }

            indexStack.push(i);
        }

        int[] result = new int[length + 1];
        for (int i = 0; i <= length; i++) {
            result[i] = 0;
        }

        for (int i = 0; i < length; i++) {
            int windowSize = nextSmaller[i] - previousSmaller[i] - 1;
            result[windowSize] = Math.max(result[windowSize], array[i]);
        }

        for (int i = length - 1; i >= 1; i--) {
            result[i] = Math.max(result[i], result[i + 1]);
        }

        for (int i = 1; i <= length; i++) {
            System.out.print(result[i] + " ");
        }
        return result;
    }

    public static void main(String[] args) {
        int[] inputArray = new int[] {10, 20, 30, 50, 10, 70, 30};
        int[] expectedOutput = new int[] {70, 30, 20, 10, 10, 10, 10};
        int[] actualOutput = maxOfMinForEveryWindowSize(inputArray, inputArray.length);
        assert Arrays.equals(expectedOutput, actualOutput);
    }
}