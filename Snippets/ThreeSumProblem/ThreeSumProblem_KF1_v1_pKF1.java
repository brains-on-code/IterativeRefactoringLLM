package com.thealgorithms.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Class1 {

    public List<List<Integer>> method1(int[] nums, int targetSum) {
        List<List<Integer>> triplets = new ArrayList<>();

        for (int firstIndex = 0; firstIndex < nums.length; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < nums.length; secondIndex++) {
                for (int thirdIndex = secondIndex + 1; thirdIndex < nums.length; thirdIndex++) {
                    if (nums[firstIndex] + nums[secondIndex] + nums[thirdIndex] == targetSum) {
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

    public List<List<Integer>> method2(int[] nums, int targetSum) {
        Arrays.sort(nums);
        List<List<Integer>> triplets = new ArrayList<>();
        int leftPointer;
        int rightPointer;
        int currentIndex = 0;

        while (currentIndex < nums.length - 1) {
            leftPointer = currentIndex + 1;
            rightPointer = nums.length - 1;

            while (leftPointer < rightPointer) {
                int currentSum = nums[currentIndex] + nums[leftPointer] + nums[rightPointer];

                if (currentSum == targetSum) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(nums[currentIndex]);
                    triplet.add(nums[leftPointer]);
                    triplet.add(nums[rightPointer]);
                    triplets.add(triplet);
                    leftPointer++;
                    rightPointer--;
                } else if (currentSum < targetSum) {
                    leftPointer++;
                } else {
                    rightPointer--;
                }
            }
            currentIndex++;
        }

        Set<List<Integer>> uniqueTriplets = new LinkedHashSet<>(triplets);
        return new ArrayList<>(uniqueTriplets);
    }

    public List<List<Integer>> method3(int[] nums, int targetSum) {
        Arrays.sort(nums);
        Set<List<Integer>> uniqueTriplets = new HashSet<>();
        HashMap<Integer, Integer> valueToIndexMap = new HashMap<>();

        for (int index = 0; index < nums.length; index++) {
            valueToIndexMap.put(nums[index], index);
        }

        for (int firstIndex = 0; firstIndex < nums.length; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < nums.length; secondIndex++) {
                int requiredValue = targetSum - nums[firstIndex] - nums[secondIndex];
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