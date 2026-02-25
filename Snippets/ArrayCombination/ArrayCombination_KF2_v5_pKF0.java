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

    private static void generateCombinations(
        List<List<Integer>> allCombinations,
        List<Integer> currentCombination,
        int startIndex,
        int n,
        int k
    ) {
        if (currentCombination.size() == k) {
            allCombinations.add(new ArrayList<>(currentCombination));
            return;
        }

        int remainingToPick = k - currentCombination.size();
        int maxStart = n - remainingToPick;

        for (int i = startIndex; i <= maxStart; i++) {
            currentCombination.add(i);
            generateCombinations(allCombinations, currentCombination, i + 1, n, k);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
}