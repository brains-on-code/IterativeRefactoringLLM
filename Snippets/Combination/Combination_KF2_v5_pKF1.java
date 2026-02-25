package com.thealgorithms.backtracking;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public final class Combination {

    private Combination() {}

    public static <T> List<TreeSet<T>> combination(T[] inputElements, int combinationSize) {
        if (combinationSize < 0) {
            throw new IllegalArgumentException("The combination length cannot be negative.");
        }

        if (combinationSize == 0) {
            return Collections.emptyList();
        }

        T[] sortedElements = inputElements.clone();
        Arrays.sort(sortedElements);

        List<TreeSet<T>> combinations = new LinkedList<>();
        generateCombinations(sortedElements, combinationSize, 0, new TreeSet<>(), combinations);
        return combinations;
    }

    private static <T> void generateCombinations(
        T[] sortedElements,
        int combinationSize,
        int currentIndex,
        TreeSet<T> currentCombination,
        List<TreeSet<T>> allCombinations
    ) {
        int remainingSlots = combinationSize - currentCombination.size();
        int remainingElements = sortedElements.length - currentIndex;

        if (remainingSlots > remainingElements) {
            return;
        }

        if (currentCombination.size() == combinationSize - 1) {
            for (int index = currentIndex; index < sortedElements.length; index++) {
                T element = sortedElements[index];
                currentCombination.add(element);
                allCombinations.add(new TreeSet<>(currentCombination));
                currentCombination.remove(element);
            }
            return;
        }

        for (int index = currentIndex; index < sortedElements.length; index++) {
            T element = sortedElements[index];
            currentCombination.add(element);
            generateCombinations(sortedElements, combinationSize, index + 1, currentCombination, allCombinations);
            currentCombination.remove(element);
        }
    }
}