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

        List<List<Integer>> combinations = new ArrayList<>();
        generateCombinations(combinations, new ArrayList<>(), 0, n, k);
        return combinations;
    }

    /**
     * Backtracking helper to generate combinations.
     *
     * @param result       the list collecting all combinations
     * @param current      the current combination being built
     * @param start        the starting index for the next element to consider
     * @param n            the upper bound (exclusive) of the range of integers
     * @param combinationSize the target size of each combination
     */
    private static void generateCombinations(
        List<List<Integer>> result,
        List<Integer> current,
        int start,
        int n,
        int combinationSize
    ) {
        if (current.size() == combinationSize) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < n; i++) {
            current.add(i);
            generateCombinations(result, current, i + 1, n, combinationSize);
            current.remove(current.size() - 1);
        }
    }
}