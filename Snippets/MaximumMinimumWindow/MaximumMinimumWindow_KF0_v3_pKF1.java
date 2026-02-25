package com.thealgorithms.stacks;

import java.util.Arrays;
import java.util.Stack;

/**
 * Given an integer array. The task is to find the maximum of the minimum of
 * every window size in the array. Note: Window size varies from 1 to the size
 * of the Array.
 *
 * For example,
 *
 * N = 7
 * arr[] = {10,20,30,50,10,70,30}
 *
 * So the answer for the above would be : 70 30 20 10 10 10 10
 *
 * We need to consider window sizes from 1 to length of array in each iteration.
 * So in the iteration 1 the windows would be [10], [20], [30], [50], [10],
 * [70], [30]. Now we need to check the minimum value in each window. Since the
 * window size is 1 here the minimum element would be the number itself. Now the
 * maximum out of these is the result in iteration 1. In the second iteration we
 * need to consider window size 2, so there would be [10,20], [20,30], [30,50],
 * [50,10], [10,70], [70,30]. Now the minimum of each window size would be
 * [10,20,30,10,10] and the maximum out of these is 30. Similarly we solve for
 * other window sizes.
 *
 * @author sahil
 */
public final class MaximumMinimumWindow {

    private MaximumMinimumWindow() {
    }

    /**
     * Finds the maximum of minimum values for every window size using a stack.
     *
     * @param values array containing the numbers
     * @param length length of the array
     * @return array where result[k] is the maximum of minimums for window size k
     */
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

        for (int index = 0; index < length; index++) {
            int windowSize = nextSmallerIndex[index] - previousSmallerIndex[index] - 1;
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
        int[] input = new int[] {10, 20, 30, 50, 10, 70, 30};
        int[] expected = new int[] {70, 30, 20, 10, 10, 10, 10};
        int[] result = calculateMaxOfMin(input, input.length);
        assert Arrays.equals(expected, result);
    }
}