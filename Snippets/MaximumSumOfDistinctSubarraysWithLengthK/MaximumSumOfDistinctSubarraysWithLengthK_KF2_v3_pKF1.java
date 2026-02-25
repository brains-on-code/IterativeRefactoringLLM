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
        long windowSum = 0;
        Set<Integer> distinctElements = new HashSet<>();

        // Initialize the first window
        for (int i = 0; i < subarrayLength; i++) {
            windowSum += nums[i];
            distinctElements.add(nums[i]);
        }

        if (distinctElements.size() == subarrayLength) {
            maxSum = windowSum;
        }

        // Slide the window across the array
        for (int windowStart = 1; windowStart <= nums.length - subarrayLength; windowStart++) {
            int outgoingIndex = windowStart - 1;
            int incomingIndex = windowStart + subarrayLength - 1;

            windowSum -= nums[outgoingIndex];
            windowSum += nums[incomingIndex];

            int scanIndex = windowStart;
            boolean outgoingStillPresent = false;

            while (scanIndex < windowStart + subarrayLength && distinctElements.size() < subarrayLength) {
                if (nums[outgoingIndex] == nums[scanIndex]) {
                    outgoingStillPresent = true;
                    break;
                }
                scanIndex++;
            }

            if (!outgoingStillPresent) {
                distinctElements.remove(nums[outgoingIndex]);
            }

            distinctElements.add(nums[incomingIndex]);

            if (distinctElements.size() == subarrayLength && maxSum < windowSum) {
                maxSum = windowSum;
            }
        }

        return maxSum;
    }
}