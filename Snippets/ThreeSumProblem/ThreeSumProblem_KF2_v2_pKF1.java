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

    public List<List<Integer>> bruteForce(int[] numbers, int targetSum) {
        List<List<Integer>> triplets = new ArrayList<>();

        for (int firstIndex = 0; firstIndex < numbers.length; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < numbers.length; secondIndex++) {
                for (int thirdIndex = secondIndex + 1; thirdIndex < numbers.length; thirdIndex++) {
                    int currentSum =
                            numbers[firstIndex] + numbers[secondIndex] + numbers[thirdIndex];
                    if (currentSum == targetSum) {
                        List<Integer> triplet = new ArrayList<>();
                        triplet.add(numbers[firstIndex]);
                        triplet.add(numbers[secondIndex]);
                        triplet.add(numbers[thirdIndex]);
                        Collections.sort(triplet);
                        triplets.add(triplet);
                    }
                }
            }
        }
        return new ArrayList<>(new LinkedHashSet<>(triplets));
    }

    public List<List<Integer>> twoPointer(int[] numbers, int targetSum) {
        Arrays.sort(numbers);
        List<List<Integer>> triplets = new ArrayList<>();

        for (int baseIndex = 0; baseIndex < numbers.length - 1; baseIndex++) {
            int leftIndex = baseIndex + 1;
            int rightIndex = numbers.length - 1;

            while (leftIndex < rightIndex) {
                int currentSum =
                        numbers[baseIndex] + numbers[leftIndex] + numbers[rightIndex];

                if (currentSum == targetSum) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(numbers[baseIndex]);
                    triplet.add(numbers[leftIndex]);
                    triplet.add(numbers[rightIndex]);
                    triplets.add(triplet);
                    leftIndex++;
                    rightIndex--;
                } else if (currentSum < targetSum) {
                    leftIndex++;
                } else {
                    rightIndex--;
                }
            }
        }

        Set<List<Integer>> uniqueTriplets = new LinkedHashSet<>(triplets);
        return new ArrayList<>(uniqueTriplets);
    }

    public List<List<Integer>> hashMap(int[] numbers, int targetSum) {
        Arrays.sort(numbers);
        Set<List<Integer>> uniqueTriplets = new HashSet<>();
        HashMap<Integer, Integer> valueToLastIndex = new HashMap<>();

        for (int index = 0; index < numbers.length; index++) {
            valueToLastIndex.put(numbers[index], index);
        }

        for (int firstIndex = 0; firstIndex < numbers.length; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < numbers.length; secondIndex++) {
                int requiredValue = targetSum - numbers[firstIndex] - numbers[secondIndex];
                Integer thirdIndex = valueToLastIndex.get(requiredValue);

                if (thirdIndex != null && thirdIndex > secondIndex) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(numbers[firstIndex]);
                    triplet.add(numbers[secondIndex]);
                    triplet.add(requiredValue);
                    uniqueTriplets.add(triplet);
                }
            }
        }
        return new ArrayList<>(uniqueTriplets);
    }
}