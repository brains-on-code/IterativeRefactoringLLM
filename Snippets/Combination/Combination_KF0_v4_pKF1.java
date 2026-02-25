package com.thealgorithms.backtracking;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Finds all combinations of a given array.
 * @author Alan Piao
 */
public final class Combination {

    private Combination() {
    }

    /**
     * Find all combinations of given array using backtracking.
     *
     * @param inputArray the array.
     * @param combinationLength length of combination
     * @param <T> the type of elements in the array.
     * @return a list of all combinations of length combinationLength. If combinationLength == 0, return empty list.
     */
    public static <T> List<TreeSet<T>> combination(T[] inputArray, int combinationLength) {
        if (combinationLength < 0) {
            throw new IllegalArgumentException("The combination length cannot be negative.");
        }

        if (combinationLength == 0) {
            return Collections.emptyList();
        }

        T[] sortedElements = inputArray.clone();
        Arrays.sort(sortedElements);

        List<TreeSet<T>> allCombinations = new LinkedList<>();
        generateCombinations(sortedElements, combinationLength, 0, new TreeSet<>(), allCombinations);
        return allCombinations;
    }

    /**
     * Backtrack all possible combinations of a given array.
     *
     * @param sortedElements the sorted array.
     * @param targetSize length of the combination
     * @param startIndex the starting index.
     * @param currentCombination set that tracks current combination
     * @param allCombinations the list containing all combinations.
     * @param <T> the type of elements in the array.
     */
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