package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for array-related algorithms.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Returns the maximum sum of any contiguous subarray of length {@code windowSize}
     * that contains only distinct elements. If the array length is smaller than
     * {@code windowSize}, returns 0.
     *
     * @param windowSize the size of the sliding window
     * @param nums       the input array
     * @return the maximum sum of a window of size {@code windowSize} with all
     *         distinct elements, or 0 if no such window exists
     */
    public static long method1(int windowSize, int... nums) {
        if (nums.length < windowSize) {
            return 0;
        }

        long maxSum = 0;
        long currentSum = 0;
        Set<Integer> windowElements = new HashSet<>();

        // Initialize the first window
        for (int i = 0; i < windowSize; i++) {
            currentSum += nums[i];
            windowElements.add(nums[i]);
        }

        if (windowElements.size() == windowSize) {
            maxSum = currentSum;
        }

        // Slide the window across the array
        for (int start = 1; start <= nums.length - windowSize; start++) {
            // Remove the element leaving the window and add the new element
            currentSum -= nums[start - 1];
            currentSum += nums[start + windowSize - 1];

            int index = start;
            boolean duplicateFound = false;

            // Check if the element leaving the window still exists within the new window
            while (index < start + windowSize && windowElements.size() < windowSize) {
                if (nums[start - 1] == nums[index]) {
                    duplicateFound = true;
                    break;
                } else {
                    index++;
                }
            }

            if (!duplicateFound) {
                windowElements.remove(nums[start - 1]);
            }

            windowElements.add(nums[start + windowSize - 1]);

            if (windowElements.size() == windowSize && maxSum < currentSum) {
                maxSum = currentSum;
            }
        }

        return maxSum;
    }
}