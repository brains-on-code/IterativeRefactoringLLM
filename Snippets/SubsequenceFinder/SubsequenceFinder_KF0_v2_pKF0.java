package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates all subsequences for a given list of elements using backtracking.
 */
public final class SubsequenceFinder {

    private SubsequenceFinder() {
        // Utility class; prevent instantiation
    }

    /**
     * Finds all subsequences of the given list using backtracking.
     *
     * @param sequence a list of items for which to generate all subsequences
     * @param <T>      the type of elements in the list
     * @return a list containing all subsequences
     */
    public static <T> List<List<T>> generateAll(List<T> sequence) {
        List<List<T>> allSubsequences = new ArrayList<>();

        if (sequence == null || sequence.isEmpty()) {
            allSubsequences.add(new ArrayList<>());
            return allSubsequences;
        }

        backtrack(sequence, 0, new ArrayList<>(), allSubsequences);
        return allSubsequences;
    }

    /**
     * Recursively explores all subsequences by deciding for each element whether
     * to include it or not.
     *
     * @param sequence        the original list of elements
     * @param index           the current index in the sequence
     * @param current         the subsequence being built
     * @param allSubsequences the collection of all generated subsequences
     * @param <T>             the type of elements in the list
     */
    private static <T> void backtrack(
        List<T> sequence,
        int index,
        List<T> current,
        List<List<T>> allSubsequences
    ) {
        if (index >= sequence.size()) {
            allSubsequences.add(new ArrayList<>(current));
            return;
        }

        T element = sequence.get(index);

        // Exclude current element
        backtrack(sequence, index + 1, current, allSubsequences);

        // Include current element
        current.add(element);
        backtrack(sequence, index + 1, current, allSubsequences);
        current.remove(current.size() - 1);
    }
}