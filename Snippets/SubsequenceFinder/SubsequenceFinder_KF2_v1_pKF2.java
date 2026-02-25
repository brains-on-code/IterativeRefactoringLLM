package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating all subsequences of a given sequence.
 */
public final class SubsequenceFinder {

    private SubsequenceFinder() {
        // Prevent instantiation
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

        List<T> currentSubsequence = new ArrayList<>();
        backtrack(sequence, currentSubsequence, 0, allSubsequences);
        return allSubsequences;
    }

    /**
     * Recursively builds subsequences by deciding for each element whether to include it.
     *
     * @param sequence          the original sequence
     * @param currentSubsequence the subsequence being built
     * @param index             current position in the original sequence
     * @param allSubsequences   collection of all generated subsequences
     * @param <T>               the type of elements in the list
     */
    private static <T> void backtrack(
        List<T> sequence,
        List<T> currentSubsequence,
        int index,
        List<List<T>> allSubsequences
    ) {
        assert index <= sequence.size();

        // Base case: reached the end of the sequence
        if (index == sequence.size()) {
            allSubsequences.add(new ArrayList<>(currentSubsequence));
            return;
        }

        // Case 1: exclude the current element
        backtrack(sequence, currentSubsequence, index + 1, allSubsequences);

        // Case 2: include the current element
        currentSubsequence.add(sequence.get(index));
        backtrack(sequence, currentSubsequence, index + 1, allSubsequences);

        // Backtrack: remove the last added element
        currentSubsequence.remove(currentSubsequence.size() - 1);
    }
}