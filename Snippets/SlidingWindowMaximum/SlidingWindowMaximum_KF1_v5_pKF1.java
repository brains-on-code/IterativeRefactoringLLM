package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
    }

    public static int[] findSlidingWindowMaximums(int[] values, int windowSize) {
        int length = values.length;
        if (length < windowSize || windowSize == 0) {
            return new int[0];
        }

        int[] windowMaximums = new int[length - windowSize + 1];
        Deque<Integer> candidateIndices = new LinkedList<>();

        for (int currentIndex = 0; currentIndex < length; currentIndex++) {
            int windowStartIndex = currentIndex - windowSize + 1;

            if (!candidateIndices.isEmpty() && candidateIndices.peekFirst() < windowStartIndex) {
                candidateIndices.pollFirst();
            }

            while (!candidateIndices.isEmpty()
                    && values[candidateIndices.peekLast()] < values[currentIndex]) {
                candidateIndices.pollLast();
            }

            candidateIndices.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                windowMaximums[windowStartIndex] = values[candidateIndices.peekFirst()];
            }
        }

        return windowMaximums;
    }
}