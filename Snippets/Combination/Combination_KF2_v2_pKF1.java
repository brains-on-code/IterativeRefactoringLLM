package com.thealgorithms.backtracking;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public final class Combination {

    private Combination() {}

    public static <T> List<TreeSet<T>> combination(T[] elements, int combinationSize) {
        if (combinationSize < 0) {
            throw new IllegalArgumentException("The combination length cannot be negative.");
        }

        if (combinationSize == 0) {
            return Collections.emptyList();
        }

        T[] sortedElements = elements.clone();
        Arrays.sort(sortedElements);

        List<TreeSet<T>> allCombinations = new LinkedList<>();
        generateCombinations(sortedElements, combinationSize, 0, new TreeSet<>(), allCombinations);
        return allCombinations;
    }

    private static <T> void generateCombinations(
        T[] sortedElements,
        int targetSize,
        int startIndex,
        TreeSet<T> currentCombination,
        List<TreeSet<T>> allCombinations
    ) {
        int remainingSlots = targetSize - currentCombination.size();
        int remainingElements = sortedElements.length - startIndex;
        if (remainingSlots > remainingElements) {
            return;
        }

        if (currentCombination.size() == targetSize - 1) {
            for (int index = startIndex; index < sortedElements.length; index++) {
                T element = sortedElements[index];
                currentCombination.add(element);
                allCombinations.add(new TreeSet<>(currentCombination));
                currentCombination.remove(element);
            }
            return;
        }

        for (int index = startIndex; index < sortedElements.length; index++) {
            T element = sortedElements[index];
            currentCombination.add(element);
            generateCombinations(sortedElements, targetSize, index + 1, currentCombination, allCombinations);
            currentCombination.remove(element);
        }
    }
}