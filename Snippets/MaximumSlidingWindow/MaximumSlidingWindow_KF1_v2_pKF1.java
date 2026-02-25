package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowMaximum {

    public int[] findSlidingWindowMaximums(int[] numbers, int windowSize) {
        if (numbers == null || numbers.length == 0 || windowSize <= 0 || windowSize > numbers.length) {
            return new int[0];
        }

        int resultLength = numbers.length - windowSize + 1;
        int[] windowMaximums = new int[resultLength];
        Deque<Integer> windowIndices = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < numbers.length; currentIndex++) {

            if (!windowIndices.isEmpty() && windowIndices.peekFirst() == currentIndex - windowSize) {
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