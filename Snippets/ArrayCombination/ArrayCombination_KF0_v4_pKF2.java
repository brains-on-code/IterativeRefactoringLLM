package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates combinations of integers using backtracking.
 */
public final class ArrayCombination {

    private ArrayCombination() {
        // Utility class; prevent instantiation.
    }

    /**
     * Returns all k-length combinations of integers in the range [0, n).
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
        backtrack(combinations, new ArrayList<>(), 0, n, k);
        return combinations;
    }

    /**
     * Builds combinations using backtracking.
     *
     * @param combinations list collecting complete combinations
     * @param current      current partial combination
     * @param start        next integer to consider
     * @param n            upper bound (exclusive) of the integer range
     * @param k            target size of each combination
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