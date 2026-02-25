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

        T[] sortedInput = inputArray.clone();
        Arrays.sort(sortedInput);

        List<TreeSet<T>> combinations = new LinkedList<>();
        generateCombinations(sortedInput, combinationLength, 0, new TreeSet<>(), combinations);
        return combinations;
    }

    /**
     * Backtrack all possible combinations of a given array.
     *
     * @param sortedInput the sorted array.
     * @param combinationLength length of the combination
     * @param startIndex the starting index.
     * @param currentCombination set that tracks current combination
     * @param combinations the list containing all combinations.
     * @param <T> the type of elements in the array.
     */
    private static <T> void generateCombinations(
            T[] sortedInput,
            int combinationLength,
            int startIndex,
            TreeSet<T> currentCombination,
            List<TreeSet<T>> combinations
    ) {
        int remainingSlots = combinationLength - currentCombination.size();
        int remainingElements = sortedInput.length - startIndex;
        if (remainingSlots > remainingElements) {
            return;
        }

        if (currentCombination.size() == combinationLength - 1) {
            for (int index = startIndex; index < sortedInput.length; index++) {
                T element = sortedInput[index];
                currentCombination.add(element);
                combinations.add(new TreeSet<>(currentCombination));
                currentCombination.remove(element);
            }
            return;
        }

        for (int index = startIndex; index < sortedInput.length; index++) {
            T element = sortedInput[index];
            currentCombination.add(element);
            generateCombinations(sortedInput, combinationLength, index + 1, currentCombination, combinations);
            currentCombination.remove(element);
        }
    }
}