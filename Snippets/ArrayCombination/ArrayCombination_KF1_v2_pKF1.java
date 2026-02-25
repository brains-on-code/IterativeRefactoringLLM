package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating combinations.
 */
public final class CombinationsGenerator {

    private CombinationsGenerator() {
    }

    /**
     * Generates all combinations of size combinationSize from the range [0, upperBound).
     *
     * @param upperBound      the upper bound (exclusive) of the range of integers to choose from; must be non-negative
     * @param combinationSize the size of each combination; must be non-negative and less than or equal to upperBound
     * @return a list of all combinations, where each combination is represented as a list of integers
     * @throws IllegalArgumentException if upperBound or combinationSize are negative, or if combinationSize > upperBound
     */
    public static List<List<Integer>> generateCombinations(int upperBound, int combinationSize) {
        if (upperBound < 0 || combinationSize < 0 || combinationSize > upperBound) {
            throw new IllegalArgumentException(
                "Invalid input: upperBound must be non-negative, combinationSize must be non-negative and less than or equal to upperBound."
            );
        }

        List<List<Integer>> combinations = new ArrayList<>();
        buildCombinations(combinations, new ArrayList<>(), 0, upperBound, combinationSize);
        return combinations;
    }

    /**
     * Backtracking helper to build combinations.
     *
     * @param combinations       the list collecting all generated combinations
     * @param currentCombination the current combination being built
     * @param nextStartValue     the current starting value for the next element to add
     * @param upperBound         the upper bound (exclusive) of the range of integers to choose from
     * @param combinationSize    the target size of each combination
     */
    private static void buildCombinations(
        List<List<Integer>> combinations,
        List<Integer> currentCombination,
        int nextStartValue,
        int upperBound,
        int combinationSize
    ) {
        if (currentCombination.size() == combinationSize) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int value = nextStartValue; value < upperBound; value++) {
            currentCombination.add(value);
            buildCombinations(combinations, currentCombination, value + 1, upperBound, combinationSize);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
}