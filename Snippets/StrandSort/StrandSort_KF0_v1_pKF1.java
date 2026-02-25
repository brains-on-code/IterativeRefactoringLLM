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
     * @param elements The list to be sorted.
     * @return The sorted list.
     */
    private static <T extends Comparable<? super T>> List<T> strandSort(List<T> elements) {
        if (elements.size() <= 1) {
            return elements;
        }

        List<T> sortedResult = new ArrayList<>();
        while (!elements.isEmpty()) {
            final List<T> strand = new ArrayList<>();
            strand.add(elements.removeFirst());

            for (int currentIndex = 0; currentIndex < elements.size();) {
                if (strand.getLast().compareTo(elements.get(currentIndex)) <= 0) {
                    strand.add(elements.remove(currentIndex));
                } else {
                    currentIndex++;
                }
            }

            sortedResult = merge(sortedResult, strand);
        }
        return sortedResult;
    }

    /**
     * Merges two sorted lists into one sorted list.
     *
     * @param <T> The type of elements to be sorted, must be Comparable.
     * @param leftList The first sorted list.
     * @param rightList The second sorted list.
     * @return The merged sorted list.
     */
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