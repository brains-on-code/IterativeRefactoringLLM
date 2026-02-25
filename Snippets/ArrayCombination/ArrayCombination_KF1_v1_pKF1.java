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
     * Generates all combinations of size k from the range [0, n).
     *
     * @param n the upper bound (exclusive) of the range of integers to choose from; must be non-negative
     * @param k the size of each combination; must be non-negative and less than or equal to n
     * @return a list of all combinations, where each combination is represented as a list of integers
     * @throws IllegalArgumentException if n or k are negative, or if k > n
     */
    public static List<List<Integer>> generateCombinations(int n, int k) {
        if (n < 0 || k < 0 || k > n) {
            throw new IllegalArgumentException(
                "Invalid input: n must be non-negative, k must be non-negative and less than or equal to n."
            );
        }

        List<List<Integer>> allCombinations = new ArrayList<>();
        backtrack(allCombinations, new ArrayList<>(), 0, n, k);
        return allCombinations;
    }

    /**
     * Backtracking helper to build combinations.
     *
     * @param allCombinations the list collecting all generated combinations
     * @param currentCombination the current combination being built
     * @param start the current starting index for the next element to add
     * @param n the upper bound (exclusive) of the range of integers to choose from
     * @param k the target size of each combination
     */
    private static void backtrack(
        List<List<Integer>> allCombinations,
        List<Integer> currentCombination,
        int start,
        int n,
        int k
    ) {
        if (currentCombination.size() == k) {
            allCombinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = start; i < n; i++) {
            currentCombination.add(i);
            backtrack(allCombinations, currentCombination, i + 1, n, k);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
}