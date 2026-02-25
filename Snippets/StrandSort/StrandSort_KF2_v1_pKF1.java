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

        List<T> mergedResult = new ArrayList<>();
        while (!remainingElements.isEmpty()) {
            final List<T> currentStrand = new ArrayList<>();
            currentStrand.add(remainingElements.removeFirst());

            for (int index = 0; index < remainingElements.size();) {
                if (currentStrand.getLast().compareTo(remainingElements.get(index)) <= 0) {
                    currentStrand.add(remainingElements.remove(index));
                } else {
                    index++;
                }
            }

            mergedResult = merge(mergedResult, currentStrand);
        }
        return mergedResult;
    }

    private static <T extends Comparable<? super T>> List<T> merge(List<T> leftList, List<T> rightList) {
        List<T> mergedList = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftList.size() && rightIndex < rightList.size()) {
            if (leftList.get(leftIndex).compareTo(rightList.get(rightIndex)) <= 0) {
                mergedList.add(leftList.get(leftIndex));
                leftIndex++;
            } else {
                mergedList.add(rightList.get(rightIndex));
                rightIndex++;
            }
        }

        mergedList.addAll(leftList.subList(leftIndex, leftList.size()));
        mergedList.addAll(rightList.subList(rightIndex, rightList.size()));
        return mergedList;
    }
}