package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class MaximumSlidingWindow {

    public int[] maxSlidingWindow(int[] numbers, int windowSize) {
        if (numbers == null || numbers.length == 0 || windowSize <= 0 || windowSize > numbers.length) {
            return new int[0];
        }

        int resultLength = numbers.length - windowSize + 1;
        int[] windowMaximums = new int[resultLength];
        Deque<Integer> candidateIndices = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < numbers.length; currentIndex++) {

            int windowStartIndex = currentIndex - windowSize + 1;

            if (!candidateIndices.isEmpty() && candidateIndices.peekFirst() < windowStartIndex) {
                candidateIndices.pollFirst();
            }

            while (!candidateIndices.isEmpty() && numbers[candidateIndices.peekLast()] < numbers[currentIndex]) {
                candidateIndices.pollLast();
            }

            candidateIndices.offerLast(currentIndex);

            if (windowStartIndex >= 0) {
                windowMaximums[windowStartIndex] = numbers[candidateIndices.peekFirst()];
            }
        }

        return windowMaximums;
    }
}