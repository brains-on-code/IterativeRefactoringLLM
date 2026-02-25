package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;


public final class SubsequenceFinder {
    private SubsequenceFinder() {
    }


    public static <T> List<List<T>> generateAll(List<T> sequence) {
        List<List<T>> allSubSequences = new ArrayList<>();
        if (sequence.isEmpty()) {
            allSubSequences.add(new ArrayList<>());
            return allSubSequences;
        }
        List<T> currentSubsequence = new ArrayList<>();
        backtrack(sequence, currentSubsequence, 0, allSubSequences);
        return allSubSequences;
    }


    private static <T> void backtrack(List<T> sequence, List<T> currentSubsequence, final int index, List<List<T>> allSubSequences) {
        assert index <= sequence.size();
        if (index == sequence.size()) {
            allSubSequences.add(new ArrayList<>(currentSubsequence));
            return;
        }

        backtrack(sequence, currentSubsequence, index + 1, allSubSequences);
        currentSubsequence.add(sequence.get(index));
        backtrack(sequence, currentSubsequence, index + 1, allSubSequences);
        currentSubsequence.removeLast();
    }
}
