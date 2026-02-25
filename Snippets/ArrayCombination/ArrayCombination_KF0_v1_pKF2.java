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
     * Generates all possible combinations of length {@code k} from the integers {@code 0} to {@code n - 1}.
     *
     * @param n the exclusive upper bound of the integer range (0 to n - 1)
     * @param k the size of each combination
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
     * Backtracking helper that builds combinations incrementally.
     *
     * @param combinations accumulator for all complete combinations
     * @param current      the combination currently being constructed
     * @param start        the next integer to consider adding
     * @param n            the exclusive upper bound of the integer range (0 to n - 1)
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