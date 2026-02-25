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
     *
     * Tries all triplets (i, j, k) with i < j < k and checks whether
     * nums[i] + nums[j] + nums[k] == target.
     *
     * Each matching triplet is sorted and collected; duplicates are removed
     * at the end using a LinkedHashSet to preserve insertion order.
     */
    public List<List<Integer>> bruteForce(int[] nums, int target) {
        List<List<Integer>> triplets = new ArrayList<>();

        for (int first = 0; first < nums.length; first++) {
            for (int second = first + 1; second < nums.length; second++) {
                for (int third = second + 1; third < nums.length; third++) {
                    int sum = nums[first] + nums[second] + nums[third];
                    if (sum == target) {
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
     *
     * Steps:
     * 1. Sort the array.
     * 2. For each index i, use two pointers (left, right) on the subarray
     *    (i + 1 .. end) to find pairs such that
     *    nums[i] + nums[left] + nums[right] == target.
     * 3. Collect all matching triplets and remove duplicates at the end
     *    using a LinkedHashSet.
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
     *
     * Steps:
     * 1. Sort the array.
     * 2. Build a map from value to its last index in the array.
     * 3. For each pair of indices (first, second) with first < second,
     *    compute the required third value:
     *        required = target - nums[first] - nums[second]
     *    and look it up in the map.
     * 4. Accept the triplet only if the index of the required value is
     *    greater than 'second' to avoid reusing elements.
     * 5. Use a Set to avoid duplicate triplets.
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