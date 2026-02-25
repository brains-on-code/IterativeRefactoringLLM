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
        validateCombinationSize(combinationSize);

        if (combinationSize == 0) {
            return Collections.emptyList();
        }

        T[] sortedArray = inputArray.clone();
        Arrays.sort(sortedArray);

        List<TreeSet<T>> combinations = new LinkedList<>();
        generateCombinations(sortedArray, combinationSize, 0, new TreeSet<>(), combinations);
        return combinations;
    }

    private static void validateCombinationSize(int combinationSize) {
        if (combinationSize < 0) {
            throw new IllegalArgumentException("The combination length cannot be negative.");
        }
    }

    private static <T> void generateCombinations(
            T[] elements,
            int targetSize,
            int startIndex,
            TreeSet<T> currentCombination,
            List<TreeSet<T>> result
    ) {
        int remainingNeeded = targetSize - currentCombination.size();
        int remainingAvailable = elements.length - startIndex;

        if (remainingNeeded > remainingAvailable) {
            return;
        }

        if (currentCombination.size() == targetSize) {
            result.add(new TreeSet<>(currentCombination));
            return;
        }

        for (int i = startIndex; i < elements.length; i++) {
            T element = elements[i];
            addElementAndRecurse(elements, targetSize, i, currentCombination, result, element);
        }
    }

    private static <T> void addElementAndRecurse(
            T[] elements,
            int targetSize,
            int currentIndex,
            TreeSet<T> currentCombination,
            List<TreeSet<T>> result,
            T element
    ) {
        currentCombination.add(element);
        generateCombinations(elements, targetSize, currentIndex + 1, currentCombination, result);
        currentCombination.remove(element);
    }
}