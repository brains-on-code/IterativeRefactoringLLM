package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StrandSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        List<T> unsortedElements = new ArrayList<>(Arrays.asList(array));
        List<T> sortedElements = strandSort(unsortedElements);
        return sortedElements.toArray(array);
    }

    private static <T extends Comparable<? super T>> List<T> strandSort(List<T> remainingElements) {
        if (remainingElements.size() <= 1) {
            return remainingElements;
        }

        List<T> sortedResult = new ArrayList<>();
        while (!remainingElements.isEmpty()) {
            List<T> currentStrand = new ArrayList<>();
            currentStrand.add(remainingElements.removeFirst());

            for (int remainingIndex = 0; remainingIndex < remainingElements.size();) {
                T lastStrandElement = currentStrand.getLast();
                T candidateElement = remainingElements.get(remainingIndex);

                if (lastStrandElement.compareTo(candidateElement) <= 0) {
                    currentStrand.add(remainingElements.remove(remainingIndex));
                } else {
                    remainingIndex++;
                }
            }

            sortedResult = merge(sortedResult, currentStrand);
        }
        return sortedResult;
    }

    private static <T extends Comparable<? super T>> List<T> merge(List<T> leftSortedList, List<T> rightSortedList) {
        List<T> mergedResult = new ArrayList<>();
        int leftPointer = 0;
        int rightPointer = 0;

        while (leftPointer < leftSortedList.size() && rightPointer < rightSortedList.size()) {
            T leftElement = leftSortedList.get(leftPointer);
            T rightElement = rightSortedList.get(rightPointer);

            if (leftElement.compareTo(rightElement) <= 0) {
                mergedResult.add(leftElement);
                leftPointer++;
            } else {
                mergedResult.add(rightElement);
                rightPointer++;
            }
        }

        mergedResult.addAll(leftSortedList.subList(leftPointer, leftSortedList.size()));
        mergedResult.addAll(rightSortedList.subList(rightPointer, rightSortedList.size()));
        return mergedResult;
    }
}