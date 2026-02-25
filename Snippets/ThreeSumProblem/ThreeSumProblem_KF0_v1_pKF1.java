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
        List<List<Integer>> triplets = new ArrayList<>();

        for (int firstIndex = 0; firstIndex < nums.length; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < nums.length; secondIndex++) {
                for (int thirdIndex = secondIndex + 1; thirdIndex < nums.length; thirdIndex++) {
                    if (nums[firstIndex] + nums[secondIndex] + nums[thirdIndex] == target) {
                        List<Integer> triplet = new ArrayList<>();
                        triplet.add(nums[firstIndex]);
                        triplet.add(nums[secondIndex]);
                        triplet.add(nums[thirdIndex]);
                        Collections.sort(triplet);
                        triplets.add(triplet);
                    }
                }
            }
        }
        triplets = new ArrayList<>(new LinkedHashSet<>(triplets));
        return triplets;
    }

    public List<List<Integer>> twoPointer(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> triplets = new ArrayList<>();

        int currentIndex = 0;
        while (currentIndex < nums.length - 1) {
            int left = currentIndex + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[currentIndex] + nums[left] + nums[right];

                if (sum == target) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(nums[currentIndex]);
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
            currentIndex++;
        }

        Set<List<Integer>> uniqueTriplets = new LinkedHashSet<>(triplets);
        return new ArrayList<>(uniqueTriplets);
    }

    public List<List<Integer>> hashMap(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> uniqueTriplets = new HashSet<>();
        HashMap<Integer, Integer> valueToIndexMap = new HashMap<>();

        for (int index = 0; index < nums.length; index++) {
            valueToIndexMap.put(nums[index], index);
        }

        for (int firstIndex = 0; firstIndex < nums.length; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < nums.length; secondIndex++) {
                int requiredValue = target - nums[firstIndex] - nums[secondIndex];
                if (valueToIndexMap.containsKey(requiredValue) && valueToIndexMap.get(requiredValue) > secondIndex) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(nums[firstIndex]);
                    triplet.add(nums[secondIndex]);
                    triplet.add(requiredValue);
                    uniqueTriplets.add(triplet);
                }
            }
        }
        return new ArrayList<>(uniqueTriplets);
    }
}