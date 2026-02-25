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
        int n = nums.length;

        for (int first = 0; first < n; first++) {
            for (int second = first + 1; second < n; second++) {
                for (int third = second + 1; third < n; third++) {
                    int sum = nums[first] + nums[second] + nums[third];
                    if (sum == target) {
                        List<Integer> triplet =
                            Arrays.asList(nums[first], nums[second], nums[third]);
                        Collections.sort(triplet);
                        triplets.add(triplet);
                    }
                }
            }
        }

        return removeDuplicatesPreserveOrder(triplets);
    }

    public List<List<Integer>> twoPointer(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> triplets = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n - 2; i++) {
            int left = i + 1;
            int right = n - 1;

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

        return removeDuplicatesPreserveOrder(triplets);
    }

    public List<List<Integer>> hashMap(int[] nums, int target) {
        Arrays.sort(nums);
        Set<List<Integer>> triplets = new HashSet<>();
        HashMap<Integer, Integer> valueToIndex = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            valueToIndex.put(nums[i], i);
        }

        int n = nums.length;
        for (int first = 0; first < n; first++) {
            for (int second = first + 1; second < n; second++) {
                int required = target - nums[first] - nums[second];
                Integer thirdIndex = valueToIndex.get(required);

                if (thirdIndex != null && thirdIndex > second) {
                    triplets.add(Arrays.asList(nums[first], nums[second], required));
                }
            }
        }

        return new ArrayList<>(triplets);
    }

    private List<List<Integer>> removeDuplicatesPreserveOrder(List<List<Integer>> triplets) {
        return new ArrayList<>(new LinkedHashSet<>(triplets));
    }
}