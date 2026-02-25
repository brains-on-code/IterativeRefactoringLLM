package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
    }

    public static int[] findSlidingWindowMaximums(int[] inputArray, int windowSize) {
        int inputLength = inputArray.length;
        if (inputLength < windowSize || windowSize == 0) {
            return new int[0];
        }

        int[] slidingMaximums = new int[inputLength - windowSize + 1];
        Deque<Integer> candidateIndices = new LinkedList<>();

        for (int currentIndex = 0; currentIndex < inputLength; currentIndex++) {
            int windowStartIndex = currentIndex - windowSize + 1;

            if (!candidateIndices.isEmpty() && candidateIndices.peekFirst() < windowStartIndex) {
                candidateIndices.pollFirst();
            }

            while (!candidateIndices.isEmpty()
                    && inputArray[candidateIndices.peekLast()] < inputArray[currentIndex]) {
                candidateIndices.pollLast();
            }

            candidateIndices.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                slidingMaximums[windowStartIndex] = inputArray[candidateIndices.peekFirst()];
            }
        }

        return slidingMaximums;
    }
}