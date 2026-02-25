package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class SubsequenceFinder {

    private SubsequenceFinder() {
        // Utility class; prevent instantiation
    }

    public static <T> List<List<T>> generateAll(List<T> sequence) {
        List<List<T>> subsequences = new ArrayList<>();

        if (sequence.isEmpty()) {
            subsequences.add(new ArrayList<>());
            return subsequences;
        }

        buildSubsequences(sequence, 0, new ArrayList<>(), subsequences);
        return subsequences;
    }

    private static <T> void buildSubsequences(
            List<T> sequence,
            int currentIndex,
            List<T> currentSubsequence,
            List<List<T>> allSubsequences
    ) {
        if (currentIndex == sequence.size()) {
            allSubsequences.add(new ArrayList<>(currentSubsequence));
            return;
        }

        // Option 1: Exclude current element
        buildSubsequences(sequence, currentIndex + 1, currentSubsequence, allSubsequences);

        // Option 2: Include current element
        currentSubsequence.add(sequence.get(currentIndex));
        buildSubsequences(sequence, currentIndex + 1, currentSubsequence, allSubsequences);
        currentSubsequence.remove(currentSubsequence.size() - 1);
    }
}