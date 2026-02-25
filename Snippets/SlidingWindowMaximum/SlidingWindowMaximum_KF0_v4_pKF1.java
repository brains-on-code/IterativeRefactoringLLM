package com.thealgorithms.datastructures.queues;

import java.util.Deque;
import java.util.LinkedList;

/**
 * The {@code SlidingWindowMaximum} class provides a method to efficiently compute
 * the maximum element within every sliding window of size {@code windowSize} in a given array.
 *
 * <p>The algorithm uses a deque to maintain the indices of useful elements within
 * the current sliding window. The time complexity of this approach is O(n) since
 * each element is processed at most twice.
 *
 * @author Hardvan
 */
public final class SlidingWindowMaximum {

    private SlidingWindowMaximum() {
    }

    /**
     * Returns an array of the maximum values for each sliding window of size {@code windowSize}.
     * <p>If {@code numbers} has fewer elements than {@code windowSize}, the result will be an empty array.
     * <p>Example:
     * <pre>
     * Input: numbers = [1, 3, -1, -3, 5, 3, 6, 7], windowSize = 3
     * Output: [3, 3, 5, 5, 6, 7]
     * </pre>
     *
     * @param numbers the input array of integers
     * @param windowSize the size of the sliding window
     * @return an array containing the maximum element for each sliding window
     */
    public static int[] maxSlidingWindow(int[] numbers, int windowSize) {
        int arrayLength = numbers.length;
        if (arrayLength < windowSize || windowSize == 0) {
            return new int[0];
        }

        int[] slidingWindowMaximums = new int[arrayLength - windowSize + 1];
        Deque<Integer> candidateIndexDeque = new LinkedList<>();

        for (int currentIndex = 0; currentIndex < arrayLength; currentIndex++) {
            int currentWindowStartIndex = currentIndex - windowSize + 1;

            // Remove indices from the front if they are out of the current window
            if (!candidateIndexDeque.isEmpty()
                    && candidateIndexDeque.peekFirst() < currentWindowStartIndex) {
                candidateIndexDeque.pollFirst();
            }

            // Remove indices from the back if their corresponding values are smaller
            // than the current element
            while (!candidateIndexDeque.isEmpty()
                    && numbers[candidateIndexDeque.peekLast()] < numbers[currentIndex]) {
                candidateIndexDeque.pollLast();
            }

            // Add the current element's index to the deque
            candidateIndexDeque.offerLast(currentIndex);

            // Store the maximum element for the current window
            if (currentIndex >= windowSize - 1) {
                slidingWindowMaximums[currentWindowStartIndex] =
                        numbers[candidateIndexDeque.peekFirst()];
            }
        }

        return slidingWindowMaximums;
    }
}