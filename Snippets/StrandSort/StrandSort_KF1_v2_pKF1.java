package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Natural merge sort implementation.
 */
public final class NaturalMergeSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        List<T> elements = new ArrayList<>(Arrays.asList(array));
        List<T> sortedElements = naturalMergeSort(elements);
        return sortedElements.toArray(array);
    }

    private static <T extends Comparable<? super T>> List<T> naturalMergeSort(List<T> elements) {
        if (elements.size() <= 1) {
            return elements;
        }

        List<T> sortedRuns = new ArrayList<>();
        while (!elements.isEmpty()) {
            final List<T> currentRun = new ArrayList<>();
            currentRun.add(elements.removeFirst());
            for (int index = 0; index < elements.size();) {
                if (currentRun.getLast().compareTo(elements.get(index)) <= 0) {
                    currentRun.add(elements.remove(index));
                } else {
                    index++;
                }
            }
            sortedRuns = mergeRuns(sortedRuns, currentRun);
        }
        return sortedRuns;
    }

    private static <T extends Comparable<? super T>> List<T> mergeRuns(List<T> leftRun, List<T> rightRun) {
        List<T> merged = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < leftRun.size() && rightIndex < rightRun.size()) {
            if (leftRun.get(leftIndex).compareTo(rightRun.get(rightIndex)) <= 0) {
                merged.add(leftRun.get(leftIndex));
                leftIndex++;
            } else {
                merged.add(rightRun.get(rightIndex));
                rightIndex++;
            }
        }
        merged.addAll(leftRun.subList(leftIndex, leftRun.size()));
        merged.addAll(rightRun.subList(rightIndex, rightRun.size()));
        return merged;
    }
}