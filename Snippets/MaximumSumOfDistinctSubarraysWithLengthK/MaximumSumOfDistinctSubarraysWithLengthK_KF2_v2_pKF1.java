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
        for (int windowStartIndex = 1; windowStartIndex <= nums.length - subarrayLength; windowStartIndex++) {
            int outgoingElementIndex = windowStartIndex - 1;
            int incomingElementIndex = windowStartIndex + subarrayLength - 1;

            currentWindowSum -= nums[outgoingElementIndex];
            currentWindowSum += nums[incomingElementIndex];

            int scanIndex = windowStartIndex;
            boolean isOutgoingElementStillInWindow = false;

            while (scanIndex < windowStartIndex + subarrayLength && distinctElementsInWindow.size() < subarrayLength) {
                if (nums[outgoingElementIndex] == nums[scanIndex]) {
                    isOutgoingElementStillInWindow = true;
                    break;
                }
                scanIndex++;
            }

            if (!isOutgoingElementStillInWindow) {
                distinctElementsInWindow.remove(nums[outgoingElementIndex]);
            }

            distinctElementsInWindow.add(nums[incomingElementIndex]);

            if (distinctElementsInWindow.size() == subarrayLength && maxSum < currentWindowSum) {
                maxSum = currentWindowSum;
            }
        }

        return maxSum;
    }
}