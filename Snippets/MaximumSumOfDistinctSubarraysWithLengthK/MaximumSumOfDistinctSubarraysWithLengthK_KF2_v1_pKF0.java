package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

public final class MaximumSumOfDistinctSubarraysWithLengthK {

    private MaximumSumOfDistinctSubarraysWithLengthK() {
        // Utility class
    }

    public static long maximumSubarraySum(int k, int... nums) {
        if (k <= 0 || nums == null || nums.length < k) {
            return 0;
        }

        long maxSum = 0;
        long currentSum = 0;
        Set<Integer> windowElements = new HashSet<>();

        // Initialize the first window
        for (int i = 0; i < k; i++) {
            currentSum += nums[i];
            windowElements.add(nums[i]);
        }

        if (windowElements.size() == k) {
            maxSum = currentSum;
        }

        // Slide the window
        for (int start = 1; start <= nums.length - k; start++) {
            int outgoing = nums[start - 1];
            int incoming = nums[start + k - 1];

            currentSum = currentSum - outgoing + incoming;

            boolean outgoingStillInWindow = false;
            for (int i = start; i < start + k; i++) {
                if (nums[i] == outgoing) {
                    outgoingStillInWindow = true;
                    break;
                }
            }

            if (!outgoingStillInWindow) {
                windowElements.remove(outgoing);
            }

            windowElements.add(incoming);

            if (windowElements.size() == k && currentSum > maxSum) {
                maxSum = currentSum;
            }
        }

        return maxSum;
    }
}