package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

public final class SubsequenceFinder {

    private SubsequenceFinder() {
    }

    public static <T> List<List<T>> generateAll(List<T> elements) {
        List<List<T>> allSubsequences = new ArrayList<>();
        if (elements.isEmpty()) {
            allSubsequences.add(new ArrayList<>());
            return allSubsequences;
        }
        List<T> currentSubsequence = new ArrayList<>();
        generateSubsequences(elements, currentSubsequence, 0, allSubsequences);
        return allSubsequences;
    }

    private static <T> void generateSubsequences(
        List<T> elements,
        List<T> currentSubsequence,
        int index,
        List<List<T>> allSubsequences
    ) {
        assert index <= elements.size();

        if (index == elements.size()) {
            allSubsequences.add(new ArrayList<>(currentSubsequence));
            return;
        }

        generateSubsequences(elements, currentSubsequence, index + 1, allSubsequences);

        currentSubsequence.add(elements.get(index));
        generateSubsequences(elements, currentSubsequence, index + 1, allSubsequences);
        currentSubsequence.remove(currentSubsequence.size() - 1);
    }
}