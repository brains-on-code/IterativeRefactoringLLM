package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumSlidingWindow {

    public int[] maxSlidingWindow(int[] inputArray, int windowSize) {
        if (inputArray == null || inputArray.length == 0 || windowSize <= 0 || windowSize > inputArray.length) {
            return new int[0];
        }

        int[] windowMaximums = new int[inputArray.length - windowSize + 1];
        Deque<Integer> windowIndices = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < inputArray.length; currentIndex++) {

            if (!windowIndices.isEmpty() && windowIndices.peekFirst() == currentIndex - windowSize) {
                windowIndices.pollFirst();
            }

            while (!windowIndices.isEmpty() && inputArray[windowIndices.peekLast()] < inputArray[currentIndex]) {
                windowIndices.pollLast();
            }

            windowIndices.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                int windowStartIndex = currentIndex - windowSize + 1;
                windowMaximums[windowStartIndex] = inputArray[windowIndices.peekFirst()];
            }
        }
        return windowMaximums;
    }
}