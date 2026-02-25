package com.thealgorithms.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ThreeSumProblem {

    public List<List<Integer>> bruteForce(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();

        int n = nums.length;
        for (int first = 0; first < n; first++) {
            for (int second = first + 1; second < n; second++) {
                for (int third = second + 1; third < n; third++) {
                    if (nums[first] + nums[second] + nums[third] == target) {
                        List<Integer> triplet = new ArrayList<>();
                        triplet.add(nums[first]);
                        triplet.add(nums[second]);
                        triplet.add(nums[third]);
                        Collections.sort(triplet);
                        result.add(triplet);
                    }
                }
            }
        }

        return new ArrayList<>(new LinkedHashSet<>(result));
    }

    public List<List<Integer>> twoPointer(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        int n = nums.length;
        for (int i = 0; i < n - 2; i++) {
            int left = i + 1;
            int right = n - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == target) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(nums[i]);
                    triplet.add(nums[left]);
                    triplet.add(nums[right]);
                    result.add(triplet);
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        Set<List<Integer>> uniqueTriplets = new LinkedHashSet<>(result);
        return new ArrayList<>(uniqueTriplets);
    }

    public List<List<Integer>> hashMap(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> triplets = new HashSet<>();
        HashMap<Integer, Integer> valueToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            valueToIndex.put(nums[i], i);
        }

        int n = nums.length;
        for (int first = 0; first < n; first++) {
            for (int second = first + 1; second < n; second++) {
                int required = target - nums[first] - nums[second];
                Integer index = valueToIndex.get(required);

                if (index != null && index > second) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(nums[first]);
                    triplet.add(nums[second]);
                    triplet.add(required);
                    triplets.add(triplet);
                }
            }
        }

        return new ArrayList<>(triplets);
    }
}