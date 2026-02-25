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

        T[] sortedElements = inputArray.clone();
        Arrays.sort(sortedElements);

        List<TreeSet<T>> combinations = new LinkedList<>();
        buildCombinations(sortedElements, combinationLength, 0, new TreeSet<>(), combinations);
        return combinations;
    }

    /**
     * Recursively generates combinations.
     *
     * @param <T>                the type of elements in the array
     * @param sortedElements     the sorted input array
     * @param combinationLength  the desired length of each combination
     * @param startIndex         the current starting index in the array
     * @param currentCombination the current partial combination being built
     * @param allCombinations    the list collecting all generated combinations
     */
    private static <T> void buildCombinations(
            T[] sortedElements,
            int combinationLength,
            int startIndex,
            TreeSet<T> currentCombination,
            List<TreeSet<T>> allCombinations
    ) {
        int remainingToSelect = combinationLength - currentCombination.size();
        int remainingElements = sortedElements.length - startIndex;

        if (remainingToSelect > remainingElements) {
            return;
        }

        if (currentCombination.size() == combinationLength - 1) {
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
            buildCombinations(sortedElements, combinationLength, index + 1, currentCombination, allCombinations);
            currentCombination.remove(element);
        }
    }
}