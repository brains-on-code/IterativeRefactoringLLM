package com.thealgorithms.others;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowMaximum {

    public int[] findSlidingWindowMaximums(int[] numbers, int windowSize) {
        if (numbers == null || numbers.length == 0 || windowSize <= 0 || windowSize > numbers.length) {
            return new int[0];
        }

        int resultLength = numbers.length - windowSize + 1;
        int[] slidingWindowMaximums = new int[resultLength];
        Deque<Integer> indexDeque = new ArrayDeque<>();

        for (int currentIndex = 0; currentIndex < numbers.length; currentIndex++) {

            if (!indexDeque.isEmpty() && indexDeque.peekFirst() == currentIndex - windowSize) {
                indexDeque.pollFirst();
            }

            while (!indexDeque.isEmpty() && numbers[indexDeque.peekLast()] < numbers[currentIndex]) {
                indexDeque.pollLast();
            }

            indexDeque.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                int windowStartIndex = currentIndex - windowSize + 1;
                slidingWindowMaximums[windowStartIndex] = numbers[indexDeque.peekFirst()];
            }
        }

        return slidingWindowMaximums;
    }
}