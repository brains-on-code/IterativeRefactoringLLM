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
     * @param sequence a list of items on the basis of which we need to generate all subsequences
     * @param <T> the type of elements in the array
     * @return a list of all subsequences
     */
    public static <T> List<List<T>> generateAll(List<T> sequence) {
        List<List<T>> allSubsequences = new ArrayList<>();
        if (sequence.isEmpty()) {
            allSubsequences.add(new ArrayList<>());
            return allSubsequences;
        }
        List<T> currentSubsequence = new ArrayList<>();
        generateSubsequences(sequence, currentSubsequence, 0, allSubsequences);
        return allSubsequences;
    }

    /**
     * Iterate through each branch of states
     * We know that each state has exactly two branching
     * It terminates when it reaches the end of the given sequence
     *
     * @param sequence all elements
     * @param currentSubsequence current subsequence
     * @param currentIndex current index
     * @param allSubsequences contains all sequences
     * @param <T> the type of elements which we generate
     */
    private static <T> void generateSubsequences(
            List<T> sequence,
            List<T> currentSubsequence,
            final int currentIndex,
            List<List<T>> allSubsequences
    ) {
        assert currentIndex <= sequence.size();
        if (currentIndex == sequence.size()) {
            allSubsequences.add(new ArrayList<>(currentSubsequence));
            return;
        }

        generateSubsequences(sequence, currentSubsequence, currentIndex + 1, allSubsequences);
        currentSubsequence.add(sequence.get(currentIndex));
        generateSubsequences(sequence, currentSubsequence, currentIndex + 1, allSubsequences);
        currentSubsequence.removeLast();
    }
}