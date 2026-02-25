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

        List<List<Integer>> allCombinations = new ArrayList<>();
        backtrackCombinations(allCombinations, new ArrayList<>(), 0, elementCount, combinationSize);
        return allCombinations;
    }

    /**
     * A helper method that uses backtracking to find combinations.
     *
     * @param allCombinations   The list to store all valid combinations found.
     * @param currentCombination The current combination being built.
     * @param startIndex         The starting index for the current recursion.
     * @param elementCount       The total number of elements (0 to elementCount - 1).
     * @param combinationSize    The desired length of each combination.
     */
    private static void backtrackCombinations(
        List<List<Integer>> allCombinations,
        List<Integer> currentCombination,
        int startIndex,
        int elementCount,
        int combinationSize
    ) {
        if (currentCombination.size() == combinationSize) {
            allCombinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int elementIndex = startIndex; elementIndex < elementCount; elementIndex++) {
            currentCombination.add(elementIndex);
            backtrackCombinations(allCombinations, currentCombination, elementIndex + 1, elementCount, combinationSize);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
}