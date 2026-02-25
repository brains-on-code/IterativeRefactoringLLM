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
        generateCombinations(combinations, new ArrayList<>(), 0, upperBound, combinationSize);
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
     * @param result          the list collecting all combinations
     * @param current         the current combination being built
     * @param start           the starting index for the next element to consider
     * @param upperBound      the upper bound (exclusive) of the range of integers
     * @param combinationSize the target size of each combination
     */
    private static void generateCombinations(
        List<List<Integer>> result,
        List<Integer> current,
        int start,
        int upperBound,
        int combinationSize
    ) {
        if (current.size() == combinationSize) {
            result.add(new ArrayList<>(current));
            return;
        }

        int remainingSlots = combinationSize - current.size();
        int maxStart = upperBound - remainingSlots;

        for (int i = start; i <= maxStart; i++) {
            current.add(i);
            generateCombinations(result, current, i + 1, upperBound, combinationSize);
            current.remove(current.size() - 1);
        }
    }
}