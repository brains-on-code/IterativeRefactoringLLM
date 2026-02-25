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

    private static <T extends Comparable<? super T>> List<T> merge(List<T> leftElements, List<T> rightElements) {
        List<T> mergedElements = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftElements.size() && rightIndex < rightElements.size()) {
            T leftElement = leftElements.get(leftIndex);
            T rightElement = rightElements.get(rightIndex);

            if (leftElement.compareTo(rightElement) <= 0) {
                mergedElements.add(leftElement);
                leftIndex++;
            } else {
                mergedElements.add(rightElement);
                rightIndex++;
            }
        }

        mergedElements.addAll(leftElements.subList(leftIndex, leftElements.size()));
        mergedElements.addAll(rightElements.subList(rightIndex, rightElements.size()));
        return mergedElements;
    }
}