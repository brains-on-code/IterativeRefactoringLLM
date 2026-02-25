package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class ArrayCombination {

    private ArrayCombination() {
        // Utility class; prevent instantiation
    }

    public static List<List<Integer>> combination(int n, int k) {
        validateInput(n, k);

        List<List<Integer>> combinations = new ArrayList<>();
        generateCombinations(combinations, new ArrayList<>(), 0, n, k);
        return combinations;
    }

    private static void validateInput(int n, int k) {
        if (n < 0 || k < 0 || k > n) {
            throw new IllegalArgumentException(
                "Invalid input: n must be non-negative, k must be non-negative and less than or equal to n."
            );
        }
    }

    private static void generateCombinations(
        List<List<Integer>> combinations,
        List<Integer> currentCombination,
        int startIndex,
        int n,
        int k
    ) {
        if (currentCombination.size() == k) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int i = startIndex; i < n; i++) {
            currentCombination.add(i);
            generateCombinations(combinations, currentCombination, i + 1, n, k);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
}