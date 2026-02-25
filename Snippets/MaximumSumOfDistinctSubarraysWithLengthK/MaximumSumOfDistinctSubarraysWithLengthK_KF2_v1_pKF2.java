package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

public final class MaximumSumOfDistinctSubarraysWithLengthK {

    private MaximumSumOfDistinctSubarraysWithLengthK() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the maximum sum of any subarray of length {@code k} such that all
     * elements in the subarray are distinct. If no such subarray exists, returns 0.
     *
     * @param k    the required subarray length
     * @param nums the input array
     * @return the maximum sum of a distinct-element subarray of length k
     */
    public static long maximumSubarraySum(int k, int... nums) {
        if (k <= 0 || nums == null || nums.length < k) {
            return 0;
        }

        long maxSum = 0;
        long currentSum = 0;
        Set<Integer> windowElements = new HashSet<>();

        int left = 0;

        for (int right = 0; right < nums.length; right++) {
            int current = nums[right];

            // Shrink window from the left until current element is unique in the window
            while (windowElements.contains(current)) {
                windowElements.remove(nums[left]);
                currentSum -= nums[left];
                left++;
            }

            // Add current element to the window
            windowElements.add(current);
            currentSum += current;

            // If window size exceeds k, shrink it from the left
            while (right - left + 1 > k) {
                windowElements.remove(nums[left]);
                currentSum -= nums[left];
                left++;
            }

            // When window size is exactly k and all elements are distinct, update maxSum
            if (right - left + 1 == k) {
                maxSum = Math.max(maxSum, currentSum);
            }
        }

        return maxSum;
    }
}