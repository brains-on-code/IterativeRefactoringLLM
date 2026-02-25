package com.thealgorithms.others;

import java.util.HashMap;
import java.util.Map;

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
        if (windowSize <= 0 || values.length < windowSize) {
            return 0;
        }

        long maxSum = 0;
        long currentSum = 0;
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        // Initialize the first window
        for (int i = 0; i < windowSize; i++) {
            int value = values[i];
            currentSum += value;
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }

        if (frequencyMap.size() == windowSize) {
            maxSum = currentSum;
        }

        // Slide the window across the array
        for (int start = 1; start <= values.length - windowSize; start++) {
            int outgoing = values[start - 1];
            int incoming = values[start + windowSize - 1];

            currentSum = currentSum - outgoing + incoming;

            // Update frequency for outgoing element
            frequencyMap.put(outgoing, frequencyMap.get(outgoing) - 1);
            if (frequencyMap.get(outgoing) == 0) {
                frequencyMap.remove(outgoing);
            }

            // Update frequency for incoming element
            frequencyMap.put(incoming, frequencyMap.getOrDefault(incoming, 0) + 1);

            if (frequencyMap.size() == windowSize && currentSum > maxSum) {
                maxSum = currentSum;
            }
        }

        return maxSum;
    }
}