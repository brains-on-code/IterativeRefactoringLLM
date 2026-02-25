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
     * @param elements the list whose subsets are to be generated
     * @param <T>      the type of elements in the list
     * @return a list containing all subsets of the input list
     */
    public static <T> List<List<T>> generatePowerSet(List<T> elements) {
        List<List<T>> allSubsets = new ArrayList<>();
        if (elements.isEmpty()) {
            allSubsets.add(new ArrayList<>());
            return allSubsets;
        }
        List<T> currentSubset = new ArrayList<>();
        generateSubsetsRecursively(elements, currentSubset, 0, allSubsets);
        return allSubsets;
    }

    /**
     * Recursively generates all subsets of the given list.
     *
     * @param elements      the original list
     * @param currentSubset the subset being built
     * @param index         the current index in the original list
     * @param allSubsets    the collection of all generated subsets
     * @param <T>           the type of elements in the list
     */
    private static <T> void generateSubsetsRecursively(
            List<T> elements,
            List<T> currentSubset,
            final int index,
            List<List<T>> allSubsets
    ) {
        assert index <= elements.size();
        if (index == elements.size()) {
            allSubsets.add(new ArrayList<>(currentSubset));
            return;
        }

        // Exclude current element
        generateSubsetsRecursively(elements, currentSubset, index + 1, allSubsets);

        // Include current element
        T element = elements.get(index);
        currentSubset.add(element);
        generateSubsetsRecursively(elements, currentSubset, index + 1, allSubsets);
        currentSubset.remove(currentSubset.size() - 1);
    }
}