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

        long maxDistinctSubarraySum = 0;
        long currentWindowSum = 0;
        Set<Integer> distinctWindowElements = new HashSet<>();

        // Initialize the first window
        for (int index = 0; index < subarrayLength; index++) {
            currentWindowSum += nums[index];
            distinctWindowElements.add(nums[index]);
        }

        if (distinctWindowElements.size() == subarrayLength) {
            maxDistinctSubarraySum = currentWindowSum;
        }

        // Slide the window across the array
        for (int windowStartIndex = 1; windowStartIndex <= nums.length - subarrayLength; windowStartIndex++) {
            int outgoingElementIndex = windowStartIndex - 1;
            int incomingElementIndex = windowStartIndex + subarrayLength - 1;

            currentWindowSum -= nums[outgoingElementIndex];
            currentWindowSum += nums[incomingElementIndex];

            int scanIndex = windowStartIndex;
            boolean isOutgoingElementStillPresent = false;

            while (scanIndex < windowStartIndex + subarrayLength && distinctWindowElements.size() < subarrayLength) {
                if (nums[outgoingElementIndex] == nums[scanIndex]) {
                    isOutgoingElementStillPresent = true;
                    break;
                }
                scanIndex++;
            }

            if (!isOutgoingElementStillPresent) {
                distinctWindowElements.remove(nums[outgoingElementIndex]);
            }

            distinctWindowElements.add(nums[incomingElementIndex]);

            if (distinctWindowElements.size() == subarrayLength && maxDistinctSubarraySum < currentWindowSum) {
                maxDistinctSubarraySum = currentWindowSum;
            }
        }

        return maxDistinctSubarraySum;
    }
}