package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods to find all combinations of integers from 0 to n-1
 * of a specified length k using backtracking.
 */
public final class ArrayCombination {

    private ArrayCombination() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates all possible combinations of length combinationSize from the integers 0 to elementCount - 1.
     *
     * @param elementCount    The total number of elements (0 to elementCount - 1).
     * @param combinationSize The desired length of each combination.
     * @return A list containing all combinations of the specified length.
     * @throws IllegalArgumentException if elementCount or combinationSize are negative,
     *                                  or if combinationSize is greater than elementCount.
     */
    public static List<List<Integer>> generateCombinations(int elementCount, int combinationSize) {
        if (elementCount < 0 || combinationSize < 0 || combinationSize > elementCount) {
            throw new IllegalArgumentException(
                "Invalid input: elementCount must be non-negative, combinationSize must be non-negative and less than or equal to elementCount."
            );
        }

        List<List<Integer>> combinations = new ArrayList<>();
        buildCombinations(combinations, new ArrayList<>(), 0, elementCount, combinationSize);
        return combinations;
    }

    /**
     * A helper method that uses backtracking to find combinations.
     *
     * @param combinations       The list to store all valid combinations found.
     * @param currentCombination The current combination being built.
     * @param startIndex         The starting index for the current recursion.
     * @param elementCount       The total number of elements (0 to elementCount - 1).
     * @param combinationSize    The desired length of each combination.
     */
    private static void buildCombinations(
        List<List<Integer>> combinations,
        List<Integer> currentCombination,
        int startIndex,
        int elementCount,
        int combinationSize
    ) {
        if (currentCombination.size() == combinationSize) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int currentElement = startIndex; currentElement < elementCount; currentElement++) {
            currentCombination.add(currentElement);
            buildCombinations(combinations, currentCombination, currentElement + 1, elementCount, combinationSize);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
}