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

    /**
     * Brute-force O(n^3) solution.
     * Tries every triplet (i, j, k) and collects those that sum to target.
     * Removes duplicates at the end.
     */
    public List<List<Integer>> bruteForce(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == target) {
                        List<Integer> triplet = new ArrayList<>();
                        triplet.add(nums[i]);
                        triplet.add(nums[j]);
                        triplet.add(nums[k]);
                        Collections.sort(triplet);
                        result.add(triplet);
                    }
                }
            }
        }

        // Remove duplicate triplets while preserving insertion order
        return new ArrayList<>(new LinkedHashSet<>(result));
    }

    /**
     * Two-pointer O(n^2) solution.
     * Sorts the array, then for each index i, uses two pointers (start, end)
     * to find pairs that, together with nums[i], sum to target.
     * Removes duplicates at the end.
     */
    public List<List<Integer>> twoPointer(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 1; i++) {
            int start = i + 1;
            int end = nums.length - 1;

            while (start < end) {
                int sum = nums[i] + nums[start] + nums[end];

                if (sum == target) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(nums[i]);
                    triplet.add(nums[start]);
                    triplet.add(nums[end]);
                    result.add(triplet);
                    start++;
                    end--;
                } else if (sum < target) {
                    start++;
                } else {
                    end--;
                }
            }
        }

        // Remove duplicate triplets while preserving insertion order
        Set<List<Integer>> uniqueTriplets = new LinkedHashSet<>(result);
        return new ArrayList<>(uniqueTriplets);
    }

    /**
     * HashMap-based O(n^2) solution.
     * Sorts the array, then for each pair (i, j), looks up the required third
     * value in a map of value -> last index. Ensures index ordering to avoid reuse.
     * Uses a Set to avoid duplicate triplets.
     */
    public List<List<Integer>> hashMap(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> uniqueTriplets = new HashSet<>();
        HashMap<Integer, Integer> valueToIndex = new HashMap<>();

        // Map each value to its last occurrence index
        for (int i = 0; i < nums.length; i++) {
            valueToIndex.put(nums[i], i);
        }

        // For each pair (i, j), check if the required third value exists
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int needed = target - nums[i] - nums[j];
                Integer kIndex = valueToIndex.get(needed);

                if (kIndex != null && kIndex > j) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(nums[i]);
                    triplet.add(nums[j]);
                    triplet.add(needed);
                    uniqueTriplets.add(triplet);
                }
            }
        }

        return new ArrayList<>(uniqueTriplets);
    }
}