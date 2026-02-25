package com.thealgorithms.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ThreeSum {

    public List<List<Integer>> findTripletsBruteForce(int[] nums, int target) {
        List<List<Integer>> triplets = new ArrayList<>();

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
                        triplets.add(triplet);
                    }
                }
            }
        }

        return new ArrayList<>(new LinkedHashSet<>(triplets));
    }

    public List<List<Integer>> findTripletsTwoPointers(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> triplets = new ArrayList<>();

        int i = 0;
        while (i < nums.length - 1) {
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == target) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(nums[i]);
                    triplet.add(nums[left]);
                    triplet.add(nums[right]);
                    triplets.add(triplet);
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
            i++;
        }

        Set<List<Integer>> uniqueTriplets = new LinkedHashSet<>(triplets);
        return new ArrayList<>(uniqueTriplets);
    }

    public List<List<Integer>> findTripletsHashMap(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> uniqueTriplets = new HashSet<>();
        HashMap<Integer, Integer> valueToLastIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            valueToLastIndex.put(nums[i], i);
        }

        for (int first = 0; first < nums.length; first++) {
            for (int second = first + 1; second < nums.length; second++) {
                int requiredValue = target - nums[first] - nums[second];
                Integer requiredIndex = valueToLastIndex.get(requiredValue);

                if (requiredIndex != null && requiredIndex > second) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(nums[first]);
                    triplet.add(nums[second]);
                    triplet.add(requiredValue);
                    uniqueTriplets.add(triplet);
                }
            }
        }

        return new ArrayList<>(uniqueTriplets);
    }
}