package com.thealgorithms.backtracking;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public final class Combination {

    private Combination() {
        // Utility class; prevent instantiation
    }

    public static <T> List<TreeSet<T>> combination(T[] inputArray, int combinationSize) {
        if (combinationSize < 0) {
            throw new IllegalArgumentException("The combination length cannot be negative.");
        }

        if (combinationSize == 0) {
            return Collections.emptyList();
        }

        T[] sortedArray = inputArray.clone();
        Arrays.sort(sortedArray);

        List<TreeSet<T>> combinations = new LinkedList<>();
        generateCombinations(sortedArray, combinationSize, 0, new TreeSet<>(), combinations);
        return combinations;
    }

    private static <T> void generateCombinations(
            T[] arr,
            int targetSize,
            int startIndex,
            TreeSet<T> currentCombination,
            List<TreeSet<T>> result
    ) {
        int remainingNeeded = targetSize - currentCombination.size();
        int remainingAvailable = arr.length - startIndex;

        if (remainingNeeded > remainingAvailable) {
            return;
        }

        if (currentCombination.size() == targetSize - 1) {
            for (int i = startIndex; i < arr.length; i++) {
                currentCombination.add(arr[i]);
                result.add(new TreeSet<>(currentCombination));
                currentCombination.remove(arr[i]);
            }
            return;
        }

        for (int i = startIndex; i < arr.length; i++) {
            currentCombination.add(arr[i]);
            generateCombinations(arr, targetSize, i + 1, currentCombination, result);
            currentCombination.remove(arr[i]);
        }
    }
}