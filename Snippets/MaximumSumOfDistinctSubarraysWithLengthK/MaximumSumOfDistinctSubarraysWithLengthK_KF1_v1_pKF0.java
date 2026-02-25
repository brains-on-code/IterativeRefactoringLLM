package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for array-based operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Finds the maximum sum of any contiguous subarray of length {@code windowSize}
     * that contains all distinct elements. If no such window exists, returns 0.
     *
     * @param windowSize the size of the sliding window
     * @param values     the input array
     * @return the maximum sum of a distinct-element window of size {@code windowSize},
     *         or 0 if no such window exists or if {@code values.length < windowSize}
     */
    public static long method1(int windowSize, int... values) {
        if (values.length < windowSize || windowSize <= 0) {
            return 0;
        }

        long maxSum = 0;
        long currentSum = 0;
        Set<Integer> windowElements = new HashSet<>();

        // Initialize the first window
        for (int i = 0; i < windowSize; i++) {
            currentSum += values[i];
            windowElements.add(values[i]);
        }

        if (windowElements.size() == windowSize) {
            maxSum = currentSum;
        }

        // Slide the window across the array
        for (int start = 1; start <= values.length - windowSize; start++) {
            int outgoing = values[start - 1];
            int incoming = values[start + windowSize - 1];

            currentSum = currentSum - outgoing + incoming;

            boolean outgoingStillInWindow = false;
            for (int i = start; i < start + windowSize; i++) {
                if (values[i] == outgoing) {
                    outgoingStillInWindow = true;
                    break;
                }
            }

            if (!outgoingStillInWindow) {
                windowElements.remove(outgoing);
            }
            windowElements.add(incoming);

            if (windowElements.size() == windowSize && currentSum > maxSum) {
                maxSum = currentSum;
            }
        }

        return maxSum;
    }
}