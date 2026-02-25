package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;


public final class ArrayCombination {
    private ArrayCombination() {
    }


    public static List<List<Integer>> combination(int n, int k) {
        if (n < 0 || k < 0 || k > n) {
            throw new IllegalArgumentException("Invalid input: n must be non-negative, k must be non-negative and less than or equal to n.");
        }

        List<List<Integer>> combinations = new ArrayList<>();
        combine(combinations, new ArrayList<>(), 0, n, k);
        return combinations;
    }


    private static void combine(List<List<Integer>> combinations, List<Integer> current, int start, int n, int k) {
        if (current.size() == k) {
            combinations.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < n; i++) {
            current.add(i);
            combine(combinations, current, i + 1, n, k);
            current.remove(current.size() - 1);
        }
    }
}
