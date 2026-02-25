package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for generating all subsets (the power set) of a given list.
 */
public final class PowerSetGenerator {

    private PowerSetGenerator() {
        // Prevent instantiation
    }

    /**
     * Generates all subsets (the power set) of the given list.
     *
     * @param <T>  the type of elements in the list
     * @param list the input list
     * @return a list containing all subsets of the input list
     */
    public static <T> List<List<T>> generatePowerSet(List<T> list) {
        if (list == null || list.isEmpty()) {
            return Collections.singletonList(new ArrayList<>());
        }

        List<List<T>> allSubsets = new ArrayList<>();
        generateSubsets(list, 0, new ArrayList<>(), allSubsets);
        return allSubsets;
    }

    /**
     * Recursively generates all subsets of the given list.
     *
     * @param <T>           the type of elements in the list
     * @param list          the original input list
     * @param index         the current index in the original list
     * @param currentSubset the subset being built
     * @param allSubsets    the collection of all generated subsets
     */
    private static <T> void generateSubsets(
        List<T> list,
        int index,
        List<T> currentSubset,
        List<List<T>> allSubsets
    ) {
        if (index == list.size()) {
            allSubsets.add(new ArrayList<>(currentSubset));
            return;
        }

        // Exclude current element
        generateSubsets(list, index + 1, currentSubset, allSubsets);

        // Include current element
        currentSubset.add(list.get(index));
        generateSubsets(list, index + 1, currentSubset, allSubsets);

        // Backtrack
        currentSubset.remove(currentSubset.size() - 1);
    }
}