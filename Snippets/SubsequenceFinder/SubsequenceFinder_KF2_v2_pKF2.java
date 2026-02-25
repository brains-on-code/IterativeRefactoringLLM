package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating all subsequences (power set) of a given list.
 */
public final class SubsequenceFinder {

    private SubsequenceFinder() {
        // Utility class; prevent instantiation.
    }

    /**
     * Generates all subsequences (the power set) of the given sequence.
     *
     * @param sequence the input list
     * @param <T>      the type of elements in the list
     * @return a list containing all subsequences of the input list
     */
    public static <T> List<List<T>> generateAll(List<T> sequence) {
        List<List<T>> allSubsequences = new ArrayList<>();

        if (sequence.isEmpty()) {
            allSubsequences.add(new ArrayList<>());
            return allSubsequences;
        }

        backtrack(sequence, new ArrayList<>(), 0, allSubsequences);
        return allSubsequences;
    }

    /**
     * Recursively builds subsequences by deciding for each element whether to include it.
     *
     * @param sequence           the original sequence
     * @param currentSubsequence the subsequence being built
     * @param index              current position in the original sequence
     * @param allSubsequences    collection of all generated subsequences
     * @param <T>                the type of elements in the list
     */
    private static <T> void backtrack(
        List<T> sequence,
        List<T> currentSubsequence,
        int index,
        List<List<T>> allSubsequences
    ) {
        assert index <= sequence.size();

        if (index == sequence.size()) {
            allSubsequences.add(new ArrayList<>(currentSubsequence));
            return;
        }

        // Option 1: skip the current element.
        backtrack(sequence, currentSubsequence, index + 1, allSubsequences);

        // Option 2: include the current element.
        currentSubsequence.add(sequence.get(index));
        backtrack(sequence, currentSubsequence, index + 1, allSubsequences);

        // Undo inclusion for the next branch.
        currentSubsequence.remove(currentSubsequence.size() - 1);
    }
}