package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class SubsequenceFinder {

    private SubsequenceFinder() {
    }

    public static <T> List<List<T>> generateAll(List<T> inputList) {
        List<List<T>> subsequences = new ArrayList<>();
        if (inputList.isEmpty()) {
            subsequences.add(new ArrayList<>());
            return subsequences;
        }
        List<T> currentSubsequence = new ArrayList<>();
        generateSubsequences(inputList, currentSubsequence, 0, subsequences);
        return subsequences;
    }

    private static <T> void generateSubsequences(
        List<T> inputList,
        List<T> currentSubsequence,
        int currentIndex,
        List<List<T>> subsequences
    ) {
        assert currentIndex <= inputList.size();

        if (currentIndex == inputList.size()) {
            subsequences.add(new ArrayList<>(currentSubsequence));
            return;
        }

        generateSubsequences(inputList, currentSubsequence, currentIndex + 1, subsequences);

        currentSubsequence.add(inputList.get(currentIndex));
        generateSubsequences(inputList, currentSubsequence, currentIndex + 1, subsequences);
        currentSubsequence.removeLast();
    }
}