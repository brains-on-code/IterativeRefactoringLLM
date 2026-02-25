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
        Map<Integer, Integer> frequencyByValue = new HashMap<>();

        // Initialize first window
        for (int i = 0; i < windowSize; i++) {
            int value = values[i];
            currentSum += value;
            incrementFrequency(frequencyByValue, value);
        }

        if (hasAllDistinctElements(frequencyByValue, windowSize)) {
            maxSum = currentSum;
        }

        // Slide the window across the array
        for (int start = 1; start <= values.length - windowSize; start++) {
            int outgoingValue = values[start - 1];
            int incomingValue = values[start + windowSize - 1];

            currentSum = currentSum - outgoingValue + incomingValue;

            decrementFrequency(frequencyByValue, outgoingValue);
            incrementFrequency(frequencyByValue, incomingValue);

            if (hasAllDistinctElements(frequencyByValue, windowSize) && currentSum > maxSum) {
                maxSum = currentSum;
            }
        }

        return maxSum;
    }

    private static void incrementFrequency(Map<Integer, Integer> frequencyByValue, int value) {
        frequencyByValue.put(value, frequencyByValue.getOrDefault(value, 0) + 1);
    }

    private static void decrementFrequency(Map<Integer, Integer> frequencyByValue, int value) {
        int updatedCount = frequencyByValue.get(value) - 1;
        if (updatedCount == 0) {
            frequencyByValue.remove(value);
        } else {
            frequencyByValue.put(value, updatedCount);
        }
    }

    private static boolean hasAllDistinctElements(Map<Integer, Integer> frequencyByValue, int windowSize) {
        return frequencyByValue.size() == windowSize;
    }
}