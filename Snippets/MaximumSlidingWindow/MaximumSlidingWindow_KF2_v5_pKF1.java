package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumSlidingWindow {

    public int[] maxSlidingWindow(int[] inputArray, int windowSize) {
        if (inputArray == null || inputArray.length == 0 || windowSize <= 0 || windowSize > inputArray.length) {
            return new int[0];
        }

        int resultLength = inputArray.length - windowSize + 1;
        int[] maxValuesPerWindow = new int[resultLength];
        Deque<Integer> indexDeque = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < inputArray.length; currentIndex++) {

            int windowStartIndex = currentIndex - windowSize + 1;

            if (!indexDeque.isEmpty() && indexDeque.peekFirst() < windowStartIndex) {
                indexDeque.pollFirst();
            }

            while (!indexDeque.isEmpty() && inputArray[indexDeque.peekLast()] < inputArray[currentIndex]) {
                indexDeque.pollLast();
            }

            indexDeque.offerLast(currentIndex);

            if (windowStartIndex >= 0) {
                maxValuesPerWindow[windowStartIndex] = inputArray[indexDeque.peekFirst()];
            }
        }

        return maxValuesPerWindow;
    }
}