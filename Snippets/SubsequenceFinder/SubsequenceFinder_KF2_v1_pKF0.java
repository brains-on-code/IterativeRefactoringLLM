package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class SubsequenceFinder {

    private SubsequenceFinder() {
        // Utility class; prevent instantiation
    }

    public static <T> List<List<T>> generateAll(List<T> sequence) {
        List<List<T>> allSubsequences = new ArrayList<>();

        if (sequence.isEmpty()) {
            allSubsequences.add(new ArrayList<>());
            return allSubsequences;
        }

        backtrack(sequence, new ArrayList<>(), 0, allSubsequences);
        return allSubsequences;
    }

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