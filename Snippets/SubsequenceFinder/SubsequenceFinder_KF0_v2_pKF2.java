package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating all subsequences of a given list using backtracking.
 */
public final class SubsequenceFinder {

    private SubsequenceFinder() {
        // Utility class; prevent instantiation
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

        List<T> current = new ArrayList<>();
        backtrack(sequence, current, 0, allSubsequences);
        return allSubsequences;
    }

    /**
     * Recursively builds subsequences by deciding for each element whether to include it.
     *
     * @param sequence the original list
     * @param current  the subsequence built so far
     * @param index    the current position in {@code sequence}
     * @param result   the collection of all generated subsequences
     * @param <T>      the type of elements in the list
     */
    private static <T> void backtrack(
        List<T> sequence,
        List<T> current,
        int index,
        List<List<T>> result
    ) {
        if (index == sequence.size()) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Choice 1: exclude the current element
        backtrack(sequence, current, index + 1, result);

        // Choice 2: include the current element
        current.add(sequence.get(index));
        backtrack(sequence, current, index + 1, result);

        // Backtrack: undo the inclusion
        current.remove(current.size() - 1);
    }
}