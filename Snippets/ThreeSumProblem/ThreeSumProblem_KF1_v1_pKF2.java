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

    /**
     * Brute-force approach: find all unique triplets (a, b, c) in nums such that
     * a + b + c == target. Time complexity: O(n^3).
     */
    public List<List<Integer>> method1(int[] nums, int target) {
        List<List<Integer>> triplets = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == target) {
                        List<Integer> triplet = new ArrayList<>();
                        triplet.add(nums[i]);
                        triplet.add(nums[j]);
                        triplet.add(nums[k]);
                        Collections.sort(triplet);
                        triplets.add(triplet);
                    }
                }
            }
        }

        // Remove duplicate triplets while preserving insertion order
        return new ArrayList<>(new LinkedHashSet<>(triplets));
    }

    /**
     * Two-pointer approach on a sorted array: for each fixed element, use
     * two pointers to find pairs that complete the triplet to the target sum.
     * Time complexity: O(n^2).
     */
    public List<List<Integer>> method2(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> triplets = new ArrayList<>();

        for (int i = 0; i < nums.length - 1; i++) {
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
        }

        // Remove duplicate triplets while preserving insertion order
        Set<List<Integer>> uniqueTriplets = new LinkedHashSet<>(triplets);
        return new ArrayList<>(uniqueTriplets);
    }

    /**
     * HashMap-based approach: for each pair (i, j), look up the required third
     * value in a map of value -> last index. Ensures indices are strictly
     * increasing to avoid reuse. Time complexity: O(n^2).
     */
    public List<List<Integer>> method3(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> uniqueTriplets = new HashSet<>();
        HashMap<Integer, Integer> valueToIndex = new HashMap<>();

        // Map each value to its last occurrence index
        for (int i = 0; i < nums.length; i++) {
            valueToIndex.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int required = target - nums[i] - nums[j];

                // Ensure the third element exists and comes after index j
                if (valueToIndex.containsKey(required) && valueToIndex.get(required) > j) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(nums[i]);
                    triplet.add(nums[j]);
                    triplet.add(required);
                    uniqueTriplets.add(triplet);
                }
            }
        }

        return new ArrayList<>(uniqueTriplets);
    }
}