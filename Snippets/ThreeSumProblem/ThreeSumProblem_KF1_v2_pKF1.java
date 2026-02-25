package com.thealgorithms.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ThreeSum {

    public List<List<Integer>> findTripletsBruteForce(int[] numbers, int targetSum) {
        List<List<Integer>> triplets = new ArrayList<>();

        for (int firstIndex = 0; firstIndex < numbers.length; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < numbers.length; secondIndex++) {
                for (int thirdIndex = secondIndex + 1; thirdIndex < numbers.length; thirdIndex++) {
                    int sum = numbers[firstIndex] + numbers[secondIndex] + numbers[thirdIndex];
                    if (sum == targetSum) {
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

    public List<List<Integer>> findTripletsTwoPointers(int[] numbers, int targetSum) {
        Arrays.sort(numbers);
        List<List<Integer>> triplets = new ArrayList<>();

        int currentIndex = 0;
        while (currentIndex < numbers.length - 1) {
            int leftIndex = currentIndex + 1;
            int rightIndex = numbers.length - 1;

            while (leftIndex < rightIndex) {
                int currentSum = numbers[currentIndex] + numbers[leftIndex] + numbers[rightIndex];

                if (currentSum == targetSum) {
                    List<Integer> triplet = new ArrayList<>();
                    triplet.add(numbers[currentIndex]);
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
            currentIndex++;
        }

        Set<List<Integer>> uniqueTriplets = new LinkedHashSet<>(triplets);
        return new ArrayList<>(uniqueTriplets);
    }

    public List<List<Integer>> findTripletsHashMap(int[] numbers, int targetSum) {
        Arrays.sort(numbers);
        Set<List<Integer>> uniqueTriplets = new HashSet<>();
        HashMap<Integer, Integer> valueToLastIndex = new HashMap<>();

        for (int index = 0; index < numbers.length; index++) {
            valueToLastIndex.put(numbers[index], index);
        }

        for (int firstIndex = 0; firstIndex < numbers.length; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < numbers.length; secondIndex++) {
                int requiredValue = targetSum - numbers[firstIndex] - numbers[secondIndex];
                Integer requiredIndex = valueToLastIndex.get(requiredValue);

                if (requiredIndex != null && requiredIndex > secondIndex) {
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