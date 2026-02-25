package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating combinations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Generates all combinations of size {@code k} from the range [0, n).
     *
     * @param n the upper bound (exclusive) of the range of integers to choose from; must be non-negative
     * @param k the size of each combination; must be non-negative and less than or equal to {@code n}
     * @return a list of all combinations, where each combination is represented as a list of integers
     * @throws IllegalArgumentException if {@code n < 0}, {@code k < 0}, or {@code k > n}
     */
    public static List<List<Integer>> method1(int n, int k) {
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
     * @param result   the list collecting all combinations
     * @param current  the current partial combination
     * @param start    the next value to consider adding
     * @param n        the upper bound (exclusive) of the range of integers
     * @param k        the target size of each combination
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