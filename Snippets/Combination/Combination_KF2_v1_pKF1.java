package com.thealgorithms.backtracking;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public final class Combination {

    private Combination() {}

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
        T[] sortedArray,
        int combinationSize,
        int startIndex,
        TreeSet<T> currentCombination,
        List<TreeSet<T>> combinations
    ) {
        if (startIndex + combinationSize - currentCombination.size() > sortedArray.length) {
            return;
        }

        if (currentCombination.size() == combinationSize - 1) {
            for (int i = startIndex; i < sortedArray.length; i++) {
                currentCombination.add(sortedArray[i]);
                combinations.add(new TreeSet<>(currentCombination));
                currentCombination.remove(sortedArray[i]);
            }
            return;
        }

        for (int i = startIndex; i < sortedArray.length; i++) {
            currentCombination.add(sortedArray[i]);
            generateCombinations(sortedArray, combinationSize, i + 1, currentCombination, combinations);
            currentCombination.remove(sortedArray[i]);
        }
    }
}