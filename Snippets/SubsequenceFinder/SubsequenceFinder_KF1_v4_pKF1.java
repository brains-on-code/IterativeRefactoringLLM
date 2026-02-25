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
        List<List<T>> powerSet = new ArrayList<>();
        if (inputList.isEmpty()) {
            powerSet.add(new ArrayList<>());
            return powerSet;
        }
        List<T> subsetInProgress = new ArrayList<>();
        buildSubsetsRecursively(inputList, subsetInProgress, 0, powerSet);
        return powerSet;
    }

    /**
     * Recursively generates all subsets of the given list.
     *
     * @param inputList        the original list
     * @param subsetInProgress the subset being built
     * @param currentIndex     the current index in the original list
     * @param powerSet         the collection of all generated subsets
     * @param <T>              the type of elements in the list
     */
    private static <T> void buildSubsetsRecursively(
            List<T> inputList,
            List<T> subsetInProgress,
            final int currentIndex,
            List<List<T>> powerSet
    ) {
        assert currentIndex <= inputList.size();
        if (currentIndex == inputList.size()) {
            powerSet.add(new ArrayList<>(subsetInProgress));
            return;
        }

        // Exclude current element
        buildSubsetsRecursively(inputList, subsetInProgress, currentIndex + 1, powerSet);

        // Include current element
        T element = inputList.get(currentIndex);
        subsetInProgress.add(element);
        buildSubsetsRecursively(inputList, subsetInProgress, currentIndex + 1, powerSet);
        subsetInProgress.remove(subsetInProgress.size() - 1);
    }
}