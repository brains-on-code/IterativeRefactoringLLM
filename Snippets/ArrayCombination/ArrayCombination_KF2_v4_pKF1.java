package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class ArrayCombination {

    private ArrayCombination() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates all combinations of size {@code combinationSize} from the range [0, upperBound).
     *
     * @param upperBound      the exclusive upper bound of the range (0 to upperBound - 1)
     * @param combinationSize the size of each combination
     * @return a list of all combinations
     * @throws IllegalArgumentException if inputs are invalid
     */
    public static List<List<Integer>> combination(int upperBound, int combinationSize) {
        if (upperBound < 0 || combinationSize < 0 || combinationSize > upperBound) {
            throw new IllegalArgumentException(
                "Invalid input: upperBound must be non-negative, " +
                "combinationSize must be non-negative and less than or equal to upperBound."
            );
        }

        List<List<Integer>> combinations = new ArrayList<>();
        generateCombinations(combinations, new ArrayList<>(), 0, upperBound, combinationSize);
        return combinations;
    }

    private static void generateCombinations(
        List<List<Integer>> combinations,
        List<Integer> partialCombination,
        int nextStart,
        int upperBound,
        int targetSize
    ) {
        if (partialCombination.size() == targetSize) {
            combinations.add(new ArrayList<>(partialCombination));
            return;
        }

        for (int candidate = nextStart; candidate < upperBound; candidate++) {
            partialCombination.add(candidate);
            generateCombinations(combinations, partialCombination, candidate + 1, upperBound, targetSize);
            partialCombination.remove(partialCombination.size() - 1);
        }
    }
}