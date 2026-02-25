package com.thealgorithms.method2;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Utility class for generating combinations.
 */
public final class CombinationGenerator {

    private CombinationGenerator() {
    }

    /**
     * Generates all combinations of the specified length from the given array.
     *
     * @param <T>               the type of elements in the input array
     * @param inputArray        the array from which to generate combinations
     * @param combinationLength the desired length of each combination
     * @return a list of combinations, each represented as a {@link TreeSet}
     * @throws IllegalArgumentException if {@code combinationLength} is negative
     */
    public static <T> List<TreeSet<T>> generateCombinations(T[] inputArray, int combinationLength) {
        if (combinationLength < 0) {
            throw new IllegalArgumentException("The combination length cannot be negative.");
        }

        if (combinationLength == 0) {
            return Collections.emptyList();
        }

        T[] sortedInput = inputArray.clone();
        Arrays.sort(sortedInput);

        List<TreeSet<T>> combinations = new LinkedList<>();
        generateCombinationsRecursive(sortedInput, combinationLength, 0, new TreeSet<>(), combinations);
        return combinations;
    }

    /**
     * Recursively generates combinations.
     *
     * @param <T>               the type of elements in the array
     * @param sortedInput       the sorted input array
     * @param combinationLength the desired length of each combination
     * @param startIndex        the current starting index in the array
     * @param currentSelection  the current partial combination being built
     * @param result            the list collecting all generated combinations
     */
    private static <T> void generateCombinationsRecursive(
            T[] sortedInput,
            int combinationLength,
            int startIndex,
            TreeSet<T> currentSelection,
            List<TreeSet<T>> result
    ) {
        int remainingToSelect = combinationLength - currentSelection.size();
        int remainingElements = sortedInput.length - startIndex;

        if (remainingToSelect > remainingElements) {
            return;
        }

        if (currentSelection.size() == combinationLength - 1) {
            for (int index = startIndex; index < sortedInput.length; index++) {
                T element = sortedInput[index];
                currentSelection.add(element);
                result.add(new TreeSet<>(currentSelection));
                currentSelection.remove(element);
            }
            return;
        }

        for (int index = startIndex; index < sortedInput.length; index++) {
            T element = sortedInput[index];
            currentSelection.add(element);
            generateCombinationsRecursive(sortedInput, combinationLength, index + 1, currentSelection, result);
            currentSelection.remove(element);
        }
    }
}