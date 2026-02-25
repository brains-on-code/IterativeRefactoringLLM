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