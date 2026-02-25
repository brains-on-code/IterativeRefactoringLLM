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
        generateSubsetsRecursive(elements, currentSubset, 0, allSubsets);
        return allSubsets;
    }

    /**
     * Recursively generates all subsets of the given list.
     *
     * @param elements      the original list
     * @param currentSubset the subset being built
     * @param currentIndex  the current index in the original list
     * @param allSubsets    the collection of all generated subsets
     * @param <T>           the type of elements in the list
     */
    private static <T> void generateSubsetsRecursive(
            List<T> elements,
            List<T> currentSubset,
            final int currentIndex,
            List<List<T>> allSubsets
    ) {
        assert currentIndex <= elements.size();
        if (currentIndex == elements.size()) {
            allSubsets.add(new ArrayList<>(currentSubset));
            return;
        }

        // Exclude current element
        generateSubsetsRecursive(elements, currentSubset, currentIndex + 1, allSubsets);

        // Include current element
        T currentElement = elements.get(currentIndex);
        currentSubset.add(currentElement);
        generateSubsetsRecursive(elements, currentSubset, currentIndex + 1, allSubsets);
        currentSubset.remove(currentSubset.size() - 1);
    }
}