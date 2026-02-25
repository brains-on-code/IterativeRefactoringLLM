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
     * @param elements a list of items on the basis of which we need to generate all subsequences
     * @param <T> the type of elements in the list
     * @return a list of all subsequences
     */
    public static <T> List<List<T>> generateAll(List<T> elements) {
        List<List<T>> allSubsequences = new ArrayList<>();
        if (elements.isEmpty()) {
            allSubsequences.add(new ArrayList<>());
            return allSubsequences;
        }
        List<T> currentSubsequence = new ArrayList<>();
        generateSubsequences(elements, currentSubsequence, 0, allSubsequences);
        return allSubsequences;
    }

    /**
     * Backtracking helper to generate subsequences.
     *
     * @param elements list of all elements
     * @param currentSubsequence subsequence being built
     * @param currentIndex current index in the input list
     * @param allSubsequences collected subsequences
     * @param <T> the type of elements which we generate
     */
    private static <T> void generateSubsequences(
            List<T> elements,
            List<T> currentSubsequence,
            int currentIndex,
            List<List<T>> allSubsequences
    ) {
        assert currentIndex <= elements.size();
        if (currentIndex == elements.size()) {
            allSubsequences.add(new ArrayList<>(currentSubsequence));
            return;
        }

        generateSubsequences(elements, currentSubsequence, currentIndex + 1, allSubsequences);

        currentSubsequence.add(elements.get(currentIndex));
        generateSubsequences(elements, currentSubsequence, currentIndex + 1, allSubsequences);
        currentSubsequence.remove(currentSubsequence.size() - 1);
    }
}