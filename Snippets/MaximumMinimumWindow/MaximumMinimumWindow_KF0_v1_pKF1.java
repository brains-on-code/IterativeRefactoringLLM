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
     * This function contains the logic of finding maximum of minimum for every
     * window size using Stack Data Structure.
     *
     * @param values Array containing the numbers
     * @param length Length of the array
     * @return result array
     */
    public static int[] calculateMaxOfMin(int[] values, int length) {
        Stack<Integer> indexStack = new Stack<>();
        int[] previousSmallerIndex = new int[length + 1];
        int[] nextSmallerIndex = new int[length + 1];

        for (int i = 0; i < length; i++) {
            previousSmallerIndex[i] = -1;
            nextSmallerIndex[i] = length;
        }

        for (int i = 0; i < length; i++) {
            while (!indexStack.empty() && values[indexStack.peek()] >= values[i]) {
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
            while (!indexStack.empty() && values[indexStack.peek()] >= values[i]) {
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
                Math.max(maxOfMinForWindowSize[windowSize], values[i]);
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
        int[] input = new int[] {10, 20, 30, 50, 10, 70, 30};
        int[] expected = new int[] {70, 30, 20, 10, 10, 10, 10};
        int[] result = calculateMaxOfMin(input, input.length);
        assert Arrays.equals(expected, result);
    }
}