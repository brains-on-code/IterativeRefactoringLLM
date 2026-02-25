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
        List<T> unsortedElements = new ArrayList<>(Arrays.asList(array));
        List<T> sortedElements = strandSort(unsortedElements);
        return sortedElements.toArray(array);
    }

    /**
     * Strand Sort algorithm that sorts a list.
     *
     * @param <T> The type of elements to be sorted, must be Comparable.
     * @param unsortedElements The list to be sorted.
     * @return The sorted list.
     */
    private static <T extends Comparable<? super T>> List<T> strandSort(List<T> unsortedElements) {
        if (unsortedElements.size() <= 1) {
            return unsortedElements;
        }

        List<T> sortedElements = new ArrayList<>();
        while (!unsortedElements.isEmpty()) {
            final List<T> strand = new ArrayList<>();
            strand.add(unsortedElements.removeFirst());

            for (int unsortedIndex = 0; unsortedIndex < unsortedElements.size();) {
                T lastStrandElement = strand.getLast();
                T currentUnsortedElement = unsortedElements.get(unsortedIndex);

                if (lastStrandElement.compareTo(currentUnsortedElement) <= 0) {
                    strand.add(unsortedElements.remove(unsortedIndex));
                } else {
                    unsortedIndex++;
                }
            }

            sortedElements = merge(sortedElements, strand);
        }
        return sortedElements;
    }

    /**
     * Merges two sorted lists into one sorted list.
     *
     * @param <T> The type of elements to be sorted, must be Comparable.
     * @param leftSortedElements The first sorted list.
     * @param rightSortedElements The second sorted list.
     * @return The merged sorted list.
     */
    private static <T extends Comparable<? super T>> List<T> merge(
            List<T> leftSortedElements,
            List<T> rightSortedElements
    ) {
        List<T> mergedElements = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < leftSortedElements.size() && rightIndex < rightSortedElements.size()) {
            T leftElement = leftSortedElements.get(leftIndex);
            T rightElement = rightSortedElements.get(rightIndex);

            if (leftElement.compareTo(rightElement) <= 0) {
                mergedElements.add(leftElement);
                leftIndex++;
            } else {
                mergedElements.add(rightElement);
                rightIndex++;
            }
        }

        mergedElements.addAll(leftSortedElements.subList(leftIndex, leftSortedElements.size()));
        mergedElements.addAll(rightSortedElements.subList(rightIndex, rightSortedElements.size()));
        return mergedElements;
    }
}