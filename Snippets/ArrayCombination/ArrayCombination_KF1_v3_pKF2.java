package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates all k-sized combinations of integers from the range [0, n).
 */
public final class CombinationsGenerator {

    private CombinationsGenerator() {
        // Prevent instantiation
    }

    /**
     * Returns all k-sized combinations from the integer range [0, n).
     *
     * @param n the exclusive upper bound of the range; must be non-negative
     * @param k the size of each combination; must be in the range [0, n]
     * @return a list of all combinations, where each combination is a list of integers
     * @throws IllegalArgumentException if {@code n < 0}, {@code k < 0}, or {@code k > n}
     */
    public static List<List<Integer>> generateCombinations(int n, int k) {
        validateInput(n, k);

        List<List<Integer>> combinations = new ArrayList<>();
        buildCombinations(combinations, new ArrayList<>(), 0, n, k);
        return combinations;
    }

    private static void validateInput(int n, int k) {
        if (n < 0 || k < 0 || k > n) {
            throw new IllegalArgumentException(
                "Invalid input: n must be non-negative, and k must be in the range [0, n]."
            );
        }
    }

    /**
     * Builds combinations using backtracking.
     *
     * @param combinations the list collecting all complete combinations
     * @param current      the current partial combination
     * @param start        the next value to consider adding to {@code current}
     * @param n            the exclusive upper bound of the range of integers
     * @param k            the target size of each combination
     */
    private static void buildCombinations(
        List<List<Integer>> combinations,
        List<Integer> current,
        int start,
        int n,
        int k
    ) {
        if (current.size() == k) {
            combinations.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < n; i++) {
            current.add(i);
            buildCombinations(combinations, current, i + 1, n, k);
            current.remove(current.size() - 1);
        }
    }
}