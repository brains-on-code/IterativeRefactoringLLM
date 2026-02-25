package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating combinations of integers using backtracking.
 */
public final class ArrayCombination {

    private ArrayCombination() {
        // Prevent instantiation
    }

    /**
     * Generates all k-length combinations of integers in the range [0, n).
     *
     * @param n upper bound (exclusive) of the integer range
     * @param k size of each combination
     * @return list of all combinations, each represented as a list of integers
     * @throws IllegalArgumentException if n < 0, k < 0, or k > n
     */
    public static List<List<Integer>> combination(int n, int k) {
        if (n < 0 || k < 0 || k > n) {
            throw new IllegalArgumentException(
                "Invalid input: n and k must be non-negative, and k must not exceed n."
            );
        }

        List<List<Integer>> combinations = new ArrayList<>();
        buildCombinations(combinations, new ArrayList<>(), 0, n, k);
        return combinations;
    }

    /**
     * Recursively builds combinations using backtracking.
     *
     * @param combinations accumulator for complete combinations
     * @param current      current partial combination
     * @param start        next integer to consider
     * @param n            upper bound (exclusive) of the integer range
     * @param k            target size of each combination
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