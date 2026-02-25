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
     * Brute-force approach.
     *
     * Finds all unique triplets (a, b, c) in {@code nums} such that:
     * <pre>a + b + c == target</pre>
     *
     * Time complexity: O(n^3).
     */
    public List<List<Integer>> method1(int[] nums, int target) {
        List<List<Integer>> triplets = new ArrayList<>();

        for (int first = 0; first < nums.length; first++) {
            for (int second = first + 1; second < nums.length; second++) {
                for (int third = second + 1; third < nums.length; third++) {
                    int sum = nums[first] + nums[second] + nums[third];
                    if (sum == target) {
                        List<Integer> triplet = Arrays.asList(nums[first], nums[second], nums[third]);
                        Collections.sort(triplet);
                        triplets.add(triplet);
                    }
                }
            }
        }

        return new ArrayList<>(new LinkedHashSet<>(triplets));
    }

    /**
     * Two-pointer approach on a sorted array.
     *
     * For each fixed element, uses two pointers to find pairs that complete
     * the triplet to the target sum.
     *
     * Time complexity: O(n^2).
     */
    public List<List<Integer>> method2(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> triplets = new ArrayList<>();

        for (int first = 0; first < nums.length - 1; first++) {
            int left = first + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[first] + nums[left] + nums[right];

                if (sum == target) {
                    triplets.add(Arrays.asList(nums[first], nums[left], nums[right]));
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        Set<List<Integer>> uniqueTriplets = new LinkedHashSet<>(triplets);
        return new ArrayList<>(uniqueTriplets);
    }

    /**
     * HashMap-based approach.
     *
     * For each pair (i, j), looks up the required third value in a map of
     * {@code value -> last index}. Ensures indices are strictly increasing
     * to avoid reuse.
     *
     * Time complexity: O(n^2).
     */
    public List<List<Integer>> method3(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> uniqueTriplets = new HashSet<>();
        HashMap<Integer, Integer> valueToLastIndex = new HashMap<>();

        for (int index = 0; index < nums.length; index++) {
            valueToLastIndex.put(nums[index], index);
        }

        for (int first = 0; first < nums.length; first++) {
            for (int second = first + 1; second < nums.length; second++) {
                int required = target - nums[first] - nums[second];

                Integer thirdIndex = valueToLastIndex.get(required);
                if (thirdIndex != null && thirdIndex > second) {
                    uniqueTriplets.add(Arrays.asList(nums[first], nums[second], required));
                }
            }
        }

        return new ArrayList<>(uniqueTriplets);
    }
}