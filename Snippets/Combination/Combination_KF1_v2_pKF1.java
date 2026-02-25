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

        T[] sortedArray = inputArray.clone();
        Arrays.sort(sortedArray);

        List<TreeSet<T>> combinations = new LinkedList<>();
        generateCombinationsRecursive(sortedArray, combinationLength, 0, new TreeSet<>(), combinations);
        return combinations;
    }

    /**
     * Recursively generates combinations.
     *
     * @param <T>                the type of elements in the array
     * @param sortedArray        the sorted input array
     * @param combinationLength  the desired length of each combination
     * @param startIndex         the current starting index in the array
     * @param currentCombination the current partial combination being built
     * @param allCombinations    the list collecting all generated combinations
     */
    private static <T> void generateCombinationsRecursive(
            T[] sortedArray,
            int combinationLength,
            int startIndex,
            TreeSet<T> currentCombination,
            List<TreeSet<T>> allCombinations
    ) {
        int remainingRequired = combinationLength - currentCombination.size();
        int remainingAvailable = sortedArray.length - startIndex;
        if (remainingRequired > remainingAvailable) {
            return;
        }

        if (currentCombination.size() == combinationLength - 1) {
            for (int i = startIndex; i < sortedArray.length; i++) {
                T element = sortedArray[i];
                currentCombination.add(element);
                allCombinations.add(new TreeSet<>(currentCombination));
                currentCombination.remove(element);
            }
            return;
        }

        for (int i = startIndex; i < sortedArray.length; i++) {
            T element = sortedArray[i];
            currentCombination.add(element);
            generateCombinationsRecursive(sortedArray, combinationLength, i + 1, currentCombination, allCombinations);
            currentCombination.remove(element);
        }
    }
}