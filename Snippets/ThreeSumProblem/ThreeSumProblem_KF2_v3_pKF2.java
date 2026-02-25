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
     * Brute-force solution with O(n^3) time complexity.
     *
     * Tries every triplet (i, j, k) and collects those whose sum equals {@code target}.
     * Each found triplet is sorted so that duplicates can be removed easily.
     * Duplicate triplets are removed at the end while preserving insertion order.
     *
     * @param nums   input array of integers
     * @param target target sum for the triplets
     * @return list of unique triplets that sum to {@code target}
     */
    public List<List<Integer>> bruteForce(int[] nums, int target) {
        List<List<Integer>> triplets = new ArrayList<>();

        for (int first = 0; first < nums.length; first++) {
            for (int second = first + 1; second < nums.length; second++) {
                for (int third = second + 1; third < nums.length; third++) {
                    if (nums[first] + nums[second] + nums[third] == target) {
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

    /**
     * Two-pointer solution with O(n^2) time complexity.
     *
     * The array is first sorted. For each index {@code i}, two pointers
     * ({@code left}, {@code right}) are used to find pairs such that
     * {@code nums[i] + nums[left] + nums[right] == target}.
     * Duplicate triplets are removed at the end while preserving insertion order.
     *
     * @param nums   input array of integers
     * @param target target sum for the triplets
     * @return list of unique triplets that sum to {@code target}
     */
    public List<List<Integer>> twoPointer(int[] nums, int target) {
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

        Set<List<Integer>> uniqueTriplets = new LinkedHashSet<>(triplets);
        return new ArrayList<>(uniqueTriplets);
    }

    /**
     * HashMap-based solution with O(n^2) time complexity.
     *
     * The array is first sorted. A map from value to its last index is built.
     * For each pair of indices (i, j), the required third value is computed as
     * {@code target - nums[i] - nums[j]}. If this value exists in the map at an
     * index greater than {@code j}, a valid triplet is formed.
     * A {@link Set} is used to avoid duplicate triplets.
     *
     * @param nums   input array of integers
     * @param target target sum for the triplets
     * @return list of unique triplets that sum to {@code target}
     */
    public List<List<Integer>> hashMap(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> uniqueTriplets = new HashSet<>();
        HashMap<Integer, Integer> valueToLastIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            valueToLastIndex.put(nums[i], i);
        }

        for (int first = 0; first < nums.length; first++) {
            for (int second = first + 1; second < nums.length; second++) {
                int needed = target - nums[first] - nums[second];
                Integer thirdIndex = valueToLastIndex.get(needed);

                if (thirdIndex != null && thirdIndex > second) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(nums[first]);
                    triplet.add(nums[second]);
                    triplet.add(needed);
                    uniqueTriplets.add(triplet);
                }
            }
        }

        return new ArrayList<>(uniqueTriplets);
    }
}