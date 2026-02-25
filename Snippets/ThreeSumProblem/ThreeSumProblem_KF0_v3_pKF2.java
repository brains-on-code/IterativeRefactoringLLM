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
     * Tries all triplets and deduplicates by sorting each triplet
     * and inserting into a LinkedHashSet to preserve insertion order.
     */
    public List<List<Integer>> bruteForce(int[] nums, int target) {
        List<List<Integer>> triplets = new ArrayList<>();

        for (int first = 0; first < nums.length; first++) {
            for (int second = first + 1; second < nums.length; second++) {
                for (int third = second + 1; third < nums.length; third++) {
                    if (nums[first] + nums[second] + nums[third] == target) {
                        List<Integer> triplet = Arrays.asList(
                            nums[first],
                            nums[second],
                            nums[third]
                        );
                        List<Integer> sortedTriplet = new ArrayList<>(triplet);
                        Collections.sort(sortedTriplet);
                        triplets.add(sortedTriplet);
                    }
                }
            }
        }

        return new ArrayList<>(new LinkedHashSet<>(triplets));
    }

    /**
     * Two-pointer O(n^2) solution.
     * Sorts the array, then for each fixed index i, uses a two-pointer
     * scan on the subarray (i+1 .. end) to find matching pairs.
     * Deduplicates triplets at the end via a LinkedHashSet.
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
                    triplets.add(Arrays.asList(nums[i], nums[left], nums[right]));
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
     * HashMap-based O(n^2) solution.
     * Sorts the array, then for each pair (first, second),
     * looks up the required third value in a precomputed value-to-index map.
     * Ensures index of the third element is greater than 'second' to avoid reuse.
     */
    public List<List<Integer>> hashMap(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> triplets = new HashSet<>();
        HashMap<Integer, Integer> valueToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            valueToIndex.put(nums[i], i);
        }

        for (int first = 0; first < nums.length; first++) {
            for (int second = first + 1; second < nums.length; second++) {
                int required = target - nums[first] - nums[second];
                Integer index = valueToIndex.get(required);

                if (index != null && index > second) {
                    triplets.add(Arrays.asList(nums[first], nums[second], required));
                }
            }
        }

        return new ArrayList<>(triplets);
    }
}