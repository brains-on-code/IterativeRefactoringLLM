package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowMaximum {

    public int[] findSlidingWindowMaximums(int[] inputArray, int windowSize) {
        if (inputArray == null || inputArray.length == 0 || windowSize <= 0 || windowSize > inputArray.length) {
            return new int[0];
        }

        int numberOfWindows = inputArray.length - windowSize + 1;
        int[] maxValuesPerWindow = new int[numberOfWindows];
        Deque<Integer> indexDeque = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < inputArray.length; currentIndex++) {

            if (!indexDeque.isEmpty() && indexDeque.peekFirst() == currentIndex - windowSize) {
                indexDeque.pollFirst();
            }

            while (!indexDeque.isEmpty() && inputArray[indexDeque.peekLast()] < inputArray[currentIndex]) {
                indexDeque.pollLast();
            }

            indexDeque.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                int windowStartIndex = currentIndex - windowSize + 1;
                maxValuesPerWindow[windowStartIndex] = inputArray[indexDeque.peekFirst()];
            }
        }

        return maxValuesPerWindow;
    }
}