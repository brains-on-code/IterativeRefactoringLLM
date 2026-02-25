package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * StrandSort class implementing the SortAlgorithm interface using arrays.
 */
public final class StrandSort implements SortAlgorithm {

    /**
     * Sorts the given array using the Strand Sort algorithm.
     *
     * @param <T> The type of elements to be sorted, must be Comparable.
     * @param array The array to be sorted.
     * @return The sorted array.
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        List<T> inputList = new ArrayList<>(Arrays.asList(array));
        List<T> sortedList = strandSort(inputList);
        return sortedList.toArray(array);
    }

    /**
     * Strand Sort algorithm that sorts a list.
     *
     * @param <T> The type of elements to be sorted, must be Comparable.
     * @param inputList The list to be sorted.
     * @return The sorted list.
     */
    private static <T extends Comparable<? super T>> List<T> strandSort(List<T> inputList) {
        if (inputList.size() <= 1) {
            return inputList;
        }

        List<T> result = new ArrayList<>();
        while (!inputList.isEmpty()) {
            final List<T> strand = new ArrayList<>();
            strand.add(inputList.removeFirst());

            for (int inputIndex = 0; inputIndex < inputList.size();) {
                T lastStrandValue = strand.getLast();
                T currentValue = inputList.get(inputIndex);

                if (lastStrandValue.compareTo(currentValue) <= 0) {
                    strand.add(inputList.remove(inputIndex));
                } else {
                    inputIndex++;
                }
            }

            result = merge(result, strand);
        }
        return result;
    }

    /**
     * Merges two sorted lists into one sorted list.
     *
     * @param <T> The type of elements to be sorted, must be Comparable.
     * @param leftList The first sorted list.
     * @param rightList The second sorted list.
     * @return The merged sorted list.
     */
    private static <T extends Comparable<? super T>> List<T> merge(
            List<T> leftList,
            List<T> rightList
    ) {
        List<T> mergedList = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftList.size() && rightIndex < rightList.size()) {
            T leftValue = leftList.get(leftIndex);
            T rightValue = rightList.get(rightIndex);

            if (leftValue.compareTo(rightValue) <= 0) {
                mergedList.add(leftValue);
                leftIndex++;
            } else {
                mergedList.add(rightValue);
                rightIndex++;
            }
        }

        mergedList.addAll(leftList.subList(leftIndex, leftList.size()));
        mergedList.addAll(rightList.subList(rightIndex, rightList.size()));
        return mergedList;
    }
}