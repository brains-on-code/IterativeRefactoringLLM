package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating all subsequences of a given list using backtracking.
 */
public final class SubsequenceFinder {

    private SubsequenceFinder() {
        // Prevent instantiation
    }

    /**
     * Generates all subsequences of the given list using backtracking.
     *
     * @param sequence the input list
     * @param <T>      the type of elements in the list
     * @return a list containing all subsequences of {@code sequence}
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
     * @param sequence          the original list
     * @param currentSubsequence the subsequence built so far
     * @param index             the current position in {@code sequence}
     * @param allSubsequences   the collection of all generated subsequences
     * @param <T>               the type of elements in the list
     */
    private static <T> void backtrack(
        List<T> sequence,
        List<T> currentSubsequence,
        int index,
        List<List<T>> allSubsequences
    ) {
        if (index == sequence.size()) {
            allSubsequences.add(new ArrayList<>(currentSubsequence));
            return;
        }

        // Exclude current element
        backtrack(sequence, currentSubsequence, index + 1, allSubsequences);

        // Include current element
        currentSubsequence.add(sequence.get(index));
        backtrack(sequence, currentSubsequence, index + 1, allSubsequences);
        currentSubsequence.remove(currentSubsequence.size() - 1);
    }
}