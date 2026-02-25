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

        List<T> sortedResult = new ArrayList<>();
        while (!elements.isEmpty()) {
            List<T> currentRun = new ArrayList<>();
            currentRun.add(elements.removeFirst());

            for (int index = 0; index < elements.size();) {
                T lastRunElement = currentRun.getLast();
                T candidateElement = elements.get(index);

                if (lastRunElement.compareTo(candidateElement) <= 0) {
                    currentRun.add(elements.remove(index));
                } else {
                    index++;
                }
            }

            sortedResult = mergeRuns(sortedResult, currentRun);
        }
        return sortedResult;
    }

    private static <T extends Comparable<? super T>> List<T> mergeRuns(List<T> leftRun, List<T> rightRun) {
        List<T> mergedRun = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftRun.size() && rightIndex < rightRun.size()) {
            T leftElement = leftRun.get(leftIndex);
            T rightElement = rightRun.get(rightIndex);

            if (leftElement.compareTo(rightElement) <= 0) {
                mergedRun.add(leftElement);
                leftIndex++;
            } else {
                mergedRun.add(rightElement);
                rightIndex++;
            }
        }

        mergedRun.addAll(leftRun.subList(leftIndex, leftRun.size()));
        mergedRun.addAll(rightRun.subList(rightIndex, rightRun.size()));
        return mergedRun;
    }
}