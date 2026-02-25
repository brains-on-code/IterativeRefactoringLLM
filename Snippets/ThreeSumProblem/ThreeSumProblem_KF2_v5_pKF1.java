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
        List<List<Integer>> resultTriplets = new ArrayList<>();

        for (int first = 0; first < nums.length; first++) {
            for (int second = first + 1; second < nums.length; second++) {
                for (int third = second + 1; third < nums.length; third++) {
                    int sum = nums[first] + nums[second] + nums[third];
                    if (sum == target) {
                        List<Integer> triplet = new ArrayList<>();
                        triplet.add(nums[first]);
                        triplet.add(nums[second]);
                        triplet.add(nums[third]);
                        Collections.sort(triplet);
                        resultTriplets.add(triplet);
                    }
                }
            }
        }
        return new ArrayList<>(new LinkedHashSet<>(resultTriplets));
    }

    public List<List<Integer>> twoPointer(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> resultTriplets = new ArrayList<>();

        for (int base = 0; base < nums.length - 1; base++) {
            int left = base + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[base] + nums[left] + nums[right];

                if (sum == target) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(nums[base]);
                    triplet.add(nums[left]);
                    triplet.add(nums[right]);
                    resultTriplets.add(triplet);
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        Set<List<Integer>> uniqueTriplets = new LinkedHashSet<>(resultTriplets);
        return new ArrayList<>(uniqueTriplets);
    }

    public List<List<Integer>> hashMap(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> uniqueTriplets = new HashSet<>();
        HashMap<Integer, Integer> valueToLastIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            valueToLastIndex.put(nums[i], i);
        }

        for (int first = 0; first < nums.length; first++) {
            for (int second = first + 1; second < nums.length; second++) {
                int required = target - nums[first] - nums[second];
                Integer thirdIndex = valueToLastIndex.get(required);

                if (thirdIndex != null && thirdIndex > second) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(nums[first]);
                    triplet.add(nums[second]);
                    triplet.add(required);
                    uniqueTriplets.add(triplet);
                }
            }
        }
        return new ArrayList<>(uniqueTriplets);
    }
}