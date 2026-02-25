package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
    }

    public static int[] findSlidingWindowMaximums(int[] numbers, int windowSize) {
        int arrayLength = numbers.length;
        if (arrayLength < windowSize || windowSize == 0) {
            return new int[0];
        }

        int[] windowMaximums = new int[arrayLength - windowSize + 1];
        Deque<Integer> windowIndices = new LinkedList<>();

        for (int currentIndex = 0; currentIndex < arrayLength; currentIndex++) {
            if (!windowIndices.isEmpty() && windowIndices.peekFirst() < currentIndex - windowSize + 1) {
                windowIndices.pollFirst();
            }

            while (!windowIndices.isEmpty() && numbers[windowIndices.peekLast()] < numbers[currentIndex]) {
                windowIndices.pollLast();
            }

            windowIndices.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                int resultIndex = currentIndex - windowSize + 1;
                windowMaximums[resultIndex] = numbers[windowIndices.peekFirst()];
            }
        }

        return windowMaximums;
    }
}