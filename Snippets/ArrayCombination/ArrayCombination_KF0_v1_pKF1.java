package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods to find all combinations of integers from 0 to n-1
 * of a specified length k using backtracking.
 */
public final class ArrayCombination {
    private ArrayCombination() {
    }

    /**
     * Generates all possible combinations of length combinationLength from the integers 0 to totalElements - 1.
     *
     * @param totalElements    The total number of elements (0 to totalElements - 1).
     * @param combinationLength The desired length of each combination.
     * @return A list containing all combinations of the specified length.
     * @throws IllegalArgumentException if totalElements or combinationLength are negative, or if combinationLength is greater than totalElements.
     */
    public static List<List<Integer>> combination(int totalElements, int combinationLength) {
        if (totalElements < 0 || combinationLength < 0 || combinationLength > totalElements) {
            throw new IllegalArgumentException(
                "Invalid input: totalElements must be non-negative, combinationLength must be non-negative and less than or equal to totalElements."
            );
        }

        List<List<Integer>> combinations = new ArrayList<>();
        generateCombinations(combinations, new ArrayList<>(), 0, totalElements, combinationLength);
        return combinations;
    }

    /**
     * A helper method that uses backtracking to find combinations.
     *
     * @param combinations      The list to store all valid combinations found.
     * @param currentCombination The current combination being built.
     * @param startIndex        The starting index for the current recursion.
     * @param totalElements     The total number of elements (0 to totalElements - 1).
     * @param combinationLength The desired length of each combination.
     */
    private static void generateCombinations(
        List<List<Integer>> combinations,
        List<Integer> currentCombination,
        int startIndex,
        int totalElements,
        int combinationLength
    ) {
        if (currentCombination.size() == combinationLength) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (int element = startIndex; element < totalElements; element++) {
            currentCombination.add(element);
            generateCombinations(combinations, currentCombination, element + 1, totalElements, combinationLength);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
}