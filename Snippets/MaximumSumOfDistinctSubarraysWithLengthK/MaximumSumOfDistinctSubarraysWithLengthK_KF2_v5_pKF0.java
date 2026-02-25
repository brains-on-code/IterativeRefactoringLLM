package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;

public final class MaximumSumOfDistinctSubarraysWithLengthK {

    private MaximumSumOfDistinctSubarraysWithLengthK() {
        // Utility class
    }

    public static long maximumSubarraySum(int k, int... nums) {
        if (!isValidInput(k, nums)) {
            return 0;
        }

        long maxSum = 0;
        long currentSum = 0;
        Set<Integer> windowElements = new HashSet<>();

        initializeFirstWindow(nums, k, windowElements);

        if (isDistinctWindow(windowElements, k)) {
            maxSum = currentSum = sumWindow(nums, 0, k);
        } else {
            currentSum = sumWindow(nums, 0, k);
        }

        for (int start = 1; start <= nums.length - k; start++) {
            int outgoing = nums[start - 1];
            int incoming = nums[start + k - 1];

            currentSum = currentSum - outgoing + incoming;

            rebuildWindowSet(windowElements, nums, start, k);

            if (isDistinctWindow(windowElements, k) && currentSum > maxSum) {
                maxSum = currentSum;
            }
        }

        return maxSum;
    }

    private static boolean isValidInput(int k, int[] nums) {
        return k > 0 && nums != null && nums.length >= k;
    }

    private static boolean isDistinctWindow(Set<Integer> windowElements, int k) {
        return windowElements.size() == k;
    }

    private static void rebuildWindowSet(Set<Integer> windowElements, int[] nums, int start, int k) {
        windowElements.clear();
        int end = start + k;
        for (int i = start; i < end; i++) {
            windowElements.add(nums[i]);
        }
    }

    private static void initializeFirstWindow(int[] nums, int k, Set<Integer> windowElements) {
        for (int i = 0; i < k; i++) {
            windowElements.add(nums[i]);
        }
    }

    private static long sumWindow(int[] nums, int start, int k) {
        long sum = 0;
        int end = start + k;
        for (int i = start; i < end; i++) {
            sum += nums[i];
        }
        return sum;
    }
}