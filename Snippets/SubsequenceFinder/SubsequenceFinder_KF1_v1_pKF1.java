package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating all subsets (the power set) of a given list.
 */
public final class PowerSetGenerator {

    private PowerSetGenerator() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates the power set of the given list.
     *
     * @param inputList the list whose subsets are to be generated
     * @param <T>       the type of elements in the list
     * @return a list containing all subsets of the input list
     */
    public static <T> List<List<T>> generatePowerSet(List<T> inputList) {
        List<List<T>> allSubsets = new ArrayList<>();
        if (inputList.isEmpty()) {
            allSubsets.add(new ArrayList<>());
            return allSubsets;
        }
        List<T> currentSubset = new ArrayList<>();
        generateSubsetsRecursive(inputList, currentSubset, 0, allSubsets);
        return allSubsets;
    }

    /**
     * Recursively generates all subsets of the given list.
     *
     * @param inputList   the original list
     * @param currentSubset the subset being built
     * @param index       the current index in the original list
     * @param allSubsets  the collection of all generated subsets
     * @param <T>         the type of elements in the list
     */
    private static <T> void generateSubsetsRecursive(
            List<T> inputList,
            List<T> currentSubset,
            final int index,
            List<List<T>> allSubsets
    ) {
        assert index <= inputList.size();
        if (index == inputList.size()) {
            allSubsets.add(new ArrayList<>(currentSubset));
            return;
        }

        // Exclude current element
        generateSubsetsRecursive(inputList, currentSubset, index + 1, allSubsets);

        // Include current element
        currentSubset.add(inputList.get(index));
        generateSubsetsRecursive(inputList, currentSubset, index + 1, allSubsets);
        currentSubset.removeLast();
    }
}