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

        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), 0, n, k);
        return result;
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
     * @param result       the list to store all valid combinations found
     * @param current      the current combination being built
     * @param start        the starting index for the current recursion
     * @param n            the total number of elements (0 to n-1)
     * @param k            the desired length of each combination
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

        int remainingToPick = k - current.size();
        int maxStart = n - remainingToPick;

        for (int i = start; i <= maxStart; i++) {
            current.add(i);
            backtrack(result, current, i + 1, n, k);
            current.remove(current.size() - 1);
        }
    }
}