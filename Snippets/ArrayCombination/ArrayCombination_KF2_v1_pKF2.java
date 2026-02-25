package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class ArrayCombination {

    private ArrayCombination() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates all combinations of size {@code k} from the range [0, n).
     *
     * @param n upper bound (exclusive) of the range of integers to choose from; must be non-negative
     * @param k size of each combination; must be non-negative and not greater than {@code n}
     * @return a list of all combinations, where each combination is represented as a list of integers
     * @throws IllegalArgumentException if {@code n < 0}, {@code k < 0}, or {@code k > n}
     */
    public static List<List<Integer>> combination(int n, int k) {
        if (n < 0 || k < 0 || k > n) {
            throw new IllegalArgumentException(
                "Invalid input: n must be non-negative, k must be non-negative and less than or equal to n."
            );
        }

        List<List<Integer>> combinations = new ArrayList<>();
        backtrack(combinations, new ArrayList<>(), 0, n, k);
        return combinations;
    }

    /**
     * Backtracking helper to build combinations.
     *
     * @param combinations accumulator for all valid combinations
     * @param current      current partial combination being built
     * @param start        next value to consider adding to the combination
     * @param n            upper bound (exclusive) of the range of integers
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