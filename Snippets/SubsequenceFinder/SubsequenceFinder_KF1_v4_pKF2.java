package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating all subsets (the power set) of a given list.
 */
public final class PowerSetGenerator {

    private PowerSetGenerator() {
        // Prevent instantiation
    }

    /**
     * Generates the power set of the given list.
     *
     * <p>The power set of a list is the set of all possible subsets,
     * including the empty set and the list itself.</p>
     *
     * @param <T>  the type of elements in the input list
     * @param list the input list
     * @return a list containing all subsets of the input list
     */
    public static <T> List<List<T>> generatePowerSet(List<T> list) {
        List<List<T>> result = new ArrayList<>();

        if (list.isEmpty()) {
            result.add(new ArrayList<>());
            return result;
        }

        generateSubsets(list, new ArrayList<>(), 0, result);
        return result;
    }

    /**
     * Recursively builds all subsets of {@code list} using backtracking.
     *
     * @param <T>           the type of elements in the list
     * @param list          the original input list
     * @param currentSubset the subset being built
     * @param index         the current index in {@code list}
     * @param result        the collection of all generated subsets
     */
    private static <T> void generateSubsets(
        List<T> list,
        List<T> currentSubset,
        int index,
        List<List<T>> result
    ) {
        assert index <= list.size();

        if (index == list.size()) {
            result.add(new ArrayList<>(currentSubset));
            return;
        }

        // Case 1: exclude the current element
        generateSubsets(list, currentSubset, index + 1, result);

        // Case 2: include the current element
        currentSubset.add(list.get(index));
        generateSubsets(list, currentSubset, index + 1, result);

        // Undo inclusion for backtracking
        currentSubset.remove(currentSubset.size() - 1);
    }
}