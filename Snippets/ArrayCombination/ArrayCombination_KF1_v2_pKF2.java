package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating combinations of integers.
 */
public final class CombinationsGenerator {

    private CombinationsGenerator() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates all k-sized combinations from the integer range [0, n).
     *
     * @param n the exclusive upper bound of the range; must be non-negative
     * @param k the size of each combination; must be in the range [0, n]
     * @return a list of all combinations, where each combination is a list of integers
     * @throws IllegalArgumentException if {@code n < 0}, {@code k < 0}, or {@code k > n}
     */
    public static List<List<Integer>> generateCombinations(int n, int k) {
        if (n < 0 || k < 0 || k > n) {
            throw new IllegalArgumentException(
                "Invalid input: n must be non-negative, and k must be in the range [0, n]."
            );
        }

        List<List<Integer>> combinations = new ArrayList<>();
        backtrack(combinations, new ArrayList<>(), 0, n, k);
        return combinations;
    }

    /**
     * Recursively builds combinations using backtracking.
     *
     * @param combinations the list collecting all complete combinations
     * @param current      the current partial combination being built
     * @param start        the next value to consider adding to {@code current}
     * @param n            the exclusive upper bound of the range of integers
     * @param k            the target size of each combination
     */
    private static void backtrack(
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
            backtrack(combinations, current, i + 1, n, k);
            current.remove(current.size() - 1);
        }
    }
}