package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class ArrayCombination {

    private ArrayCombination() {}

    /**
     * Generates all k-sized combinations of integers from the range [0, n).
     *
     * @param n upper bound (exclusive); must be >= 0
     * @param k combination size; must be in [0, n]
     * @return list of combinations, each as a list of integers
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
     * @param combinations collected combinations
     * @param current      current partial combination
     * @param start        next value to consider
     * @param n            upper bound (exclusive)
     * @param k            target combination size
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