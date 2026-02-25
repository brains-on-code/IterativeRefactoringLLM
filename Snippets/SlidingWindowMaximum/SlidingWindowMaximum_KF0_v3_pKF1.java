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
        int length = numbers.length;
        if (length < windowSize || windowSize == 0) {
            return new int[0];
        }

        int[] windowMaxValues = new int[length - windowSize + 1];
        Deque<Integer> maxCandidateIndices = new LinkedList<>();

        for (int index = 0; index < length; index++) {
            int windowStartIndex = index - windowSize + 1;

            // Remove indices from the front if they are out of the current window
            if (!maxCandidateIndices.isEmpty()
                    && maxCandidateIndices.peekFirst() < windowStartIndex) {
                maxCandidateIndices.pollFirst();
            }

            // Remove indices from the back if their corresponding values are smaller
            // than the current element
            while (!maxCandidateIndices.isEmpty()
                    && numbers[maxCandidateIndices.peekLast()] < numbers[index]) {
                maxCandidateIndices.pollLast();
            }

            // Add the current element's index to the deque
            maxCandidateIndices.offerLast(index);

            // Store the maximum element for the current window
            if (index >= windowSize - 1) {
                windowMaxValues[windowStartIndex] = numbers[maxCandidateIndices.peekFirst()];
            }
        }

        return windowMaxValues;
    }
}