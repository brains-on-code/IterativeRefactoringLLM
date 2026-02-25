package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

public final class MaximumSumOfDistinctSubarraysWithLengthK {

    private MaximumSumOfDistinctSubarraysWithLengthK() {
    }

    public static long maximumSubarraySum(int subarrayLength, int... nums) {
        if (nums.length < subarrayLength) {
            return 0;
        }

        long maxSum = 0;
        long currentWindowSum = 0;
        Set<Integer> distinctElementsInWindow = new HashSet<>();

        // Initialize the first window
        for (int index = 0; index < subarrayLength; index++) {
            currentWindowSum += nums[index];
            distinctElementsInWindow.add(nums[index]);
        }

        if (distinctElementsInWindow.size() == subarrayLength) {
            maxSum = currentWindowSum;
        }

        // Slide the window across the array
        for (int windowStart = 1; windowStart <= nums.length - subarrayLength; windowStart++) {
            int outgoingIndex = windowStart - 1;
            int incomingIndex = windowStart + subarrayLength - 1;

            currentWindowSum -= nums[outgoingIndex];
            currentWindowSum += nums[incomingIndex];

            int scanIndex = windowStart;
            boolean outgoingElementStillInWindow = false;

            while (scanIndex < windowStart + subarrayLength && distinctElementsInWindow.size() < subarrayLength) {
                if (nums[outgoingIndex] == nums[scanIndex]) {
                    outgoingElementStillInWindow = true;
                    break;
                }
                scanIndex++;
            }

            if (!outgoingElementStillInWindow) {
                distinctElementsInWindow.remove(nums[outgoingIndex]);
            }

            distinctElementsInWindow.add(nums[incomingIndex]);

            if (distinctElementsInWindow.size() == subarrayLength && maxSum < currentWindowSum) {
                maxSum = currentWindowSum;
            }
        }

        return maxSum;
    }
}