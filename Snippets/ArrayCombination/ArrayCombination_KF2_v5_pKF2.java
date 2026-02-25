package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class ArrayCombination {

    private ArrayCombination() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns all k-sized combinations of integers from the range [0, n).
     *
     * @param n upper bound (exclusive); must be >= 0
     * @param k combination size; must be in [0, n]
     * @return list of combinations, each as a list of integers
     * @throws IllegalArgumentException if n < 0, k < 0, or k > n
     */
    public static List<List<Integer>> combination(int n, int k) {
        validateInput(n, k);

        List<List<Integer>> combinations = new ArrayList<>();
        backtrack(combinations, new ArrayList<>(), 0, n, k);
        return combinations;
    }

    private static void validateInput(int n, int k) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative.");
        }
        if (k < 0) {
            throw new IllegalArgumentException("k must be non-negative.");
        }
        if (k > n) {
            throw new IllegalArgumentException("k must be less than or equal to n.");
        }
    }

    /**
     * Uses backtracking to build all combinations.
     *
     * @param combinations accumulator for completed combinations
     * @param current      current partial combination
     * @param start        next value to consider
     * @param n            upper bound (exclusive)
     * @param k            target combination size
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