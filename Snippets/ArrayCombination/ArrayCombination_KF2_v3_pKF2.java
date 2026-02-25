package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class ArrayCombination {

    private ArrayCombination() {}

    /**
     * Returns all k-sized combinations of integers from the range [0, n).
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

        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), 0, n, k);
        return result;
    }

    /**
     * Backtracking helper to build combinations.
     *
     * @param result  collected combinations
     * @param current current partial combination
     * @param start   next value to consider
     * @param n       upper bound (exclusive)
     * @param k       target combination size
     */
    private static void backtrack(
        List<List<Integer>> result,
        List<Integer> current,
        int start,
        int n,
        int k
    ) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < n; i++) {
            current.add(i);
            backtrack(result, current, i + 1, n, k);
            current.remove(current.size() - 1);
        }
    }
}