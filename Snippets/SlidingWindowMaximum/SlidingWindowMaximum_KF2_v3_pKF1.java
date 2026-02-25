package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
    }

    public static int[] maxSlidingWindow(int[] inputArray, int windowSize) {
        int arrayLength = inputArray.length;
        if (arrayLength < windowSize || windowSize == 0) {
            return new int[0];
        }

        int[] windowMaximums = new int[arrayLength - windowSize + 1];
        Deque<Integer> windowIndices = new LinkedList<>();

        for (int currentIndex = 0; currentIndex < arrayLength; currentIndex++) {
            int windowStartIndex = currentIndex - windowSize + 1;

            if (!windowIndices.isEmpty() && windowIndices.peekFirst() < windowStartIndex) {
                windowIndices.pollFirst();
            }

            while (!windowIndices.isEmpty()
                    && inputArray[windowIndices.peekLast()] < inputArray[currentIndex]) {
                windowIndices.pollLast();
            }

            windowIndices.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                windowMaximums[windowStartIndex] = inputArray[windowIndices.peekFirst()];
            }
        }

        return windowMaximums;
    }
}