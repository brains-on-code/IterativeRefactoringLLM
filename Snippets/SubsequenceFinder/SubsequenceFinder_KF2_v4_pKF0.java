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

        generateSubsequences(sequence, 0, new ArrayList<>(), subsequences);
        return subsequences;
    }

    private static <T> void generateSubsequences(
            List<T> sequence,
            int index,
            List<T> current,
            List<List<T>> result
    ) {
        if (index == sequence.size()) {
            result.add(new ArrayList<>(current));
            return;
        }

        // Exclude current element
        generateSubsequences(sequence, index + 1, current, result);

        // Include current element
        current.add(sequence.get(index));
        generateSubsequences(sequence, index + 1, current, result);
        current.remove(current.size() - 1);
    }
}