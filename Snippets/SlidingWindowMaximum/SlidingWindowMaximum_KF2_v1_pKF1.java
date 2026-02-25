package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
    }

    public static int[] maxSlidingWindow(int[] values, int windowSize) {
        int length = values.length;
        if (length < windowSize || windowSize == 0) {
            return new int[0];
        }

        int[] windowMaximums = new int[length - windowSize + 1];
        Deque<Integer> indexDeque = new LinkedList<>();

        for (int currentIndex = 0; currentIndex < length; currentIndex++) {
            int windowStartIndex = currentIndex - windowSize + 1;

            if (!indexDeque.isEmpty() && indexDeque.peekFirst() < windowStartIndex) {
                indexDeque.pollFirst();
            }

            while (!indexDeque.isEmpty()
                    && values[indexDeque.peekLast()] < values[currentIndex]) {
                indexDeque.pollLast();
            }

            indexDeque.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                windowMaximums[windowStartIndex] = values[indexDeque.peekFirst()];
            }
        }

        return windowMaximums;
    }
}