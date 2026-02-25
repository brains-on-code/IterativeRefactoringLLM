package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StrandSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        List<T> unsortedList = new ArrayList<>(Arrays.asList(array));
        List<T> sortedList = strandSort(unsortedList);
        return sortedList.toArray(array);
    }

    private static <T extends Comparable<? super T>> List<T> strandSort(List<T> inputList) {
        if (inputList.size() <= 1) {
            return inputList;
        }

        List<T> result = new ArrayList<>();
        while (!inputList.isEmpty()) {
            List<T> strand = new ArrayList<>();
            strand.add(inputList.removeFirst());

            for (int i = 0; i < inputList.size();) {
                T lastStrandElement = strand.getLast();
                T currentElement = inputList.get(i);

                if (lastStrandElement.compareTo(currentElement) <= 0) {
                    strand.add(inputList.remove(i));
                } else {
                    i++;
                }
            }

            result = merge(result, strand);
        }
        return result;
    }

    private static <T extends Comparable<? super T>> List<T> merge(List<T> leftList, List<T> rightList) {
        List<T> mergedList = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftList.size() && rightIndex < rightList.size()) {
            T leftElement = leftList.get(leftIndex);
            T rightElement = rightList.get(rightIndex);

            if (leftElement.compareTo(rightElement) <= 0) {
                mergedList.add(leftElement);
                leftIndex++;
            } else {
                mergedList.add(rightElement);
                rightIndex++;
            }
        }

        mergedList.addAll(leftList.subList(leftIndex, leftList.size()));
        mergedList.addAll(rightList.subList(rightIndex, rightList.size()));
        return mergedList;
    }
}