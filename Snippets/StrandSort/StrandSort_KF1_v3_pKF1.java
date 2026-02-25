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
        List<T> inputList = new ArrayList<>(Arrays.asList(array));
        List<T> sortedList = naturalMergeSort(inputList);
        return sortedList.toArray(array);
    }

    private static <T extends Comparable<? super T>> List<T> naturalMergeSort(List<T> inputList) {
        if (inputList.size() <= 1) {
            return inputList;
        }

        List<T> mergedRuns = new ArrayList<>();
        while (!inputList.isEmpty()) {
            final List<T> currentRun = new ArrayList<>();
            currentRun.add(inputList.removeFirst());

            for (int currentIndex = 0; currentIndex < inputList.size();) {
                T lastInRun = currentRun.getLast();
                T currentElement = inputList.get(currentIndex);

                if (lastInRun.compareTo(currentElement) <= 0) {
                    currentRun.add(inputList.remove(currentIndex));
                } else {
                    currentIndex++;
                }
            }

            mergedRuns = mergeRuns(mergedRuns, currentRun);
        }
        return mergedRuns;
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