package com.thealgorithms.others;

import java.util.HashSet;
import java.util.Set;


public final class MaximumSumOfDistinctSubarraysWithLengthK {
    private MaximumSumOfDistinctSubarraysWithLengthK() {
    }


    public static long maximumSubarraySum(int k, int... nums) {
        if (nums.length < k) {
            return 0;
        }
        long masSum = 0;
        long currentSum = 0;
        Set<Integer> currentSet = new HashSet<>();

        for (int i = 0; i < k; i++) {
            currentSum += nums[i];
            currentSet.add(nums[i]);
        }
        if (currentSet.size() == k) {
            masSum = currentSum;
        }
        for (int i = 1; i < nums.length - k + 1; i++) {
            currentSum = currentSum - nums[i - 1];
            currentSum = currentSum + nums[i + k - 1];
            int j = i;
            boolean flag = false;
            while (j < i + k && currentSet.size() < k) {
                if (nums[i - 1] == nums[j]) {
                    flag = true;
                    break;
                } else {
                    j++;
                }
            }
            if (!flag) {
                currentSet.remove(nums[i - 1]);
            }
            currentSet.add(nums[i + k - 1]);
            if (currentSet.size() == k && masSum < currentSum) {
                masSum = currentSum;
            }
        }
        return masSum;
    }
}
