package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class ArrayCombination {

    private ArrayCombination() {
        // Prevent instantiation
    }

    /**
     * Generates all k-sized combinations from the integer range [0, n).
     *
     * @param n upper bound (exclusive) of the range; must be non-negative
     * @param k size of each combination; must be in [0, n]
     * @return list of all combinations, each represented as a list of integers
     * @throws IllegalArgumentException if n < 0, k < 0, or k > n
     */
    public static List<List<Integer>> combination(int n, int k) {
        if (n < 0 || k < 0 || k > n) {
            throw new IllegalArgumentException(
                "Invalid input: n must be non-negative, k must be non-negative and less than or equal to n."
            );
        }

        List<List<Integer>> combinations = new ArrayList<>();
        buildCombinations(combinations, new ArrayList<>(), 0, n, k);
        return combinations;
    }

    /**
     * Recursively builds combinations using backtracking.
     *
     * @param combinations accumulator for all complete combinations
     * @param current      current partial combination
     * @param start        next value to consider
     * @param n            upper bound (exclusive) of the range
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