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
     * @param inputElements a list of items on the basis of which we need to generate all subsequences
     * @param <T> the type of elements in the list
     * @return a list of all subsequences
     */
    public static <T> List<List<T>> generateAll(List<T> inputElements) {
        List<List<T>> subsequences = new ArrayList<>();
        if (inputElements.isEmpty()) {
            subsequences.add(new ArrayList<>());
            return subsequences;
        }
        List<T> currentSequence = new ArrayList<>();
        backtrackSubsequences(inputElements, currentSequence, 0, subsequences);
        return subsequences;
    }

    /**
     * Backtracking helper to generate subsequences.
     *
     * @param inputElements list of all elements
     * @param currentSequence subsequence being built
     * @param position current index in the input list
     * @param subsequences collected subsequences
     * @param <T> the type of elements which we generate
     */
    private static <T> void backtrackSubsequences(
            List<T> inputElements,
            List<T> currentSequence,
            int position,
            List<List<T>> subsequences
    ) {
        assert position <= inputElements.size();
        if (position == inputElements.size()) {
            subsequences.add(new ArrayList<>(currentSequence));
            return;
        }

        backtrackSubsequences(inputElements, currentSequence, position + 1, subsequences);

        currentSequence.add(inputElements.get(position));
        backtrackSubsequences(inputElements, currentSequence, position + 1, subsequences);
        currentSequence.remove(currentSequence.size() - 1);
    }
}