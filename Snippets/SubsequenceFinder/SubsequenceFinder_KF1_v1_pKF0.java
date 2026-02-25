package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating all subsets (the power set) of a given list.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates all subsets (the power set) of the given list.
     *
     * @param <T>  the type of elements in the list
     * @param list the input list
     * @return a list containing all subsets of the input list
     */
    public static <T> List<List<T>> method1(List<T> list) {
        List<List<T>> allSubsets = new ArrayList<>();
        if (list.isEmpty()) {
            allSubsets.add(new ArrayList<>());
            return allSubsets;
        }

        List<T> currentSubset = new ArrayList<>();
        generateSubsets(list, currentSubset, 0, allSubsets);
        return allSubsets;
    }

    /**
     * Recursively generates all subsets of the given list.
     *
     * @param <T>           the type of elements in the list
     * @param list          the original input list
     * @param currentSubset the subset being built
     * @param index         the current index in the original list
     * @param allSubsets    the collection of all generated subsets
     */
    private static <T> void generateSubsets(
        List<T> list,
        List<T> currentSubset,
        int index,
        List<List<T>> allSubsets
    ) {
        assert index <= list.size();

        if (index == list.size()) {
            allSubsets.add(new ArrayList<>(currentSubset));
            return;
        }

        // Exclude current element
        generateSubsets(list, currentSubset, index + 1, allSubsets);

        // Include current element
        currentSubset.add(list.get(index));
        generateSubsets(list, currentSubset, index + 1, allSubsets);
        currentSubset.remove(currentSubset.size() - 1);
    }
}