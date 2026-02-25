package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating combinations.
 */
public final class CombinationsGenerator {

    private CombinationsGenerator() {
        // Prevent instantiation
    }

    /**
     * Generates all combinations of size {@code combinationSize} from the range [0, upperBound).
     *
     * @param upperBound      the upper bound (exclusive) of the range of integers to choose from; must be non-negative
     * @param combinationSize the size of each combination; must be non-negative and less than or equal to {@code upperBound}
     * @return a list of all combinations, where each combination is represented as a list of integers
     * @throws IllegalArgumentException if {@code upperBound < 0}, {@code combinationSize < 0}, or {@code combinationSize > upperBound}
     */
    public static List<List<Integer>> generate(int upperBound, int combinationSize) {
        validateInput(upperBound, combinationSize);

        List<List<Integer>> combinations = new ArrayList<>();
        List<Integer> currentCombination = new ArrayList<>();

        generateCombinations(combinations, currentCombination, 0, upperBound, combinationSize);
        return combinations;
    }

    private static void validateInput(int upperBound, int combinationSize) {
        if (upperBound < 0) {
            throw new IllegalArgumentException("upperBound must be non-negative.");
        }
        if (combinationSize < 0) {
            throw new IllegalArgumentException("combinationSize must be non-negative.");
        }
        if (combinationSize > upperBound) {
            throw new IllegalArgumentException("combinationSize must be less than or equal to upperBound.");
        }
    }

    /**
     * Backtracking helper to generate combinations.
     *
     * @param allCombinations the list collecting all combinations
     * @param current         the current combination being built
     * @param start           the starting index for the next element to consider
     * @param upperBound      the upper bound (exclusive) of the range of integers
     * @param combinationSize the target size of each combination
     */
    private static void generateCombinations(
        List<List<Integer>> allCombinations,
        List<Integer> current,
        int start,
        int upperBound,
        int combinationSize
    ) {
        if (current.size() == combinationSize) {
            allCombinations.add(new ArrayList<>(current));
            return;
        }

        int remainingSlots = combinationSize - current.size();
        int lastPossibleStart = upperBound - remainingSlots;

        for (int i = start; i <= lastPossibleStart; i++) {
            current.add(i);
            generateCombinations(allCombinations, current, i + 1, upperBound, combinationSize);
            current.remove(current.size() - 1);
        }
    }
}