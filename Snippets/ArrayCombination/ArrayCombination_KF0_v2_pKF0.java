package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to generate all combinations of integers from 0 to n-1
 * of a specified length k using backtracking.
 */
public final class ArrayCombination {

    private ArrayCombination() {
        // Prevent instantiation
    }

    /**
     * Generates all possible combinations of length k from the integers 0 to n-1.
     *
     * @param n the total number of elements (0 to n-1)
     * @param k the desired length of each combination
     * @return a list containing all combinations of length k
     * @throws IllegalArgumentException if n or k are negative, or if k is greater than n
     */
    public static List<List<Integer>> combination(int n, int k) {
        validateInput(n, k);

        List<List<Integer>> combinations = new ArrayList<>();
        List<Integer> currentCombination = new ArrayList<>();

        generateCombinations(combinations, currentCombination, 0, n, k);
        return combinations;
    }

    private static void validateInput(int n, int k) {
        if (n < 0 || k < 0 || k > n) {
            throw new IllegalArgumentException(
                "Invalid input: n must be non-negative, k must be non-negative and less than or equal to n."
            );
        }
    }

    /**
     * Uses backtracking to build all valid combinations.
     *
     * @param combinations       the list to store all valid combinations found
     * @param currentCombination the current combination being built
     * @param startIndex         the starting index for the current recursion
     * @param n                  the total number of elements (0 to n-1)
     * @param k                  the desired length of each combination
     */
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