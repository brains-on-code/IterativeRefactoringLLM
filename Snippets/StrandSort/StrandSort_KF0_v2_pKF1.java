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
        List<T> unsortedList = new ArrayList<>(Arrays.asList(array));
        List<T> sortedList = strandSort(unsortedList);
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

        List<T> sortedList = new ArrayList<>();
        while (!inputList.isEmpty()) {
            final List<T> currentStrand = new ArrayList<>();
            currentStrand.add(inputList.removeFirst());

            for (int inputIndex = 0; inputIndex < inputList.size();) {
                T lastStrandElement = currentStrand.getLast();
                T currentInputElement = inputList.get(inputIndex);

                if (lastStrandElement.compareTo(currentInputElement) <= 0) {
                    currentStrand.add(inputList.remove(inputIndex));
                } else {
                    inputIndex++;
                }
            }

            sortedList = merge(sortedList, currentStrand);
        }
        return sortedList;
    }

    /**
     * Merges two sorted lists into one sorted list.
     *
     * @param <T> The type of elements to be sorted, must be Comparable.
     * @param leftSortedList The first sorted list.
     * @param rightSortedList The second sorted list.
     * @return The merged sorted list.
     */
    private static <T extends Comparable<? super T>> List<T> merge(List<T> leftSortedList, List<T> rightSortedList) {
        List<T> mergedList = new ArrayList<>();
        int leftPointer = 0;
        int rightPointer = 0;

        while (leftPointer < leftSortedList.size() && rightPointer < rightSortedList.size()) {
            T leftElement = leftSortedList.get(leftPointer);
            T rightElement = rightSortedList.get(rightPointer);

            if (leftElement.compareTo(rightElement) <= 0) {
                mergedList.add(leftElement);
                leftPointer++;
            } else {
                mergedList.add(rightElement);
                rightPointer++;
            }
        }

        mergedList.addAll(leftSortedList.subList(leftPointer, leftSortedList.size()));
        mergedList.addAll(rightSortedList.subList(rightPointer, rightSortedList.size()));
        return mergedList;
    }
}