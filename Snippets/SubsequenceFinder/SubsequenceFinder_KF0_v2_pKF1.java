package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Class generates all subsequences for a given list of elements using backtracking
 */
public final class SubsequenceFinder {
    private SubsequenceFinder() {
    }

    /**
     * Find all subsequences of given list using backtracking
     *
     * @param inputList a list of items on the basis of which we need to generate all subsequences
     * @param <T> the type of elements in the array
     * @return a list of all subsequences
     */
    public static <T> List<List<T>> generateAll(List<T> inputList) {
        List<List<T>> subsequences = new ArrayList<>();
        if (inputList.isEmpty()) {
            subsequences.add(new ArrayList<>());
            return subsequences;
        }
        List<T> currentSubsequence = new ArrayList<>();
        backtrackSubsequences(inputList, currentSubsequence, 0, subsequences);
        return subsequences;
    }

    /**
     * Backtracking helper to generate subsequences.
     *
     * @param inputList list of all elements
     * @param currentSubsequence subsequence being built
     * @param index current index in the input list
     * @param subsequences collected subsequences
     * @param <T> the type of elements which we generate
     */
    private static <T> void backtrackSubsequences(
            List<T> inputList,
            List<T> currentSubsequence,
            final int index,
            List<List<T>> subsequences
    ) {
        assert index <= inputList.size();
        if (index == inputList.size()) {
            subsequences.add(new ArrayList<>(currentSubsequence));
            return;
        }

        backtrackSubsequences(inputList, currentSubsequence, index + 1, subsequences);

        currentSubsequence.add(inputList.get(index));
        backtrackSubsequences(inputList, currentSubsequence, index + 1, subsequences);
        currentSubsequence.remove(currentSubsequence.size() - 1);
    }
}