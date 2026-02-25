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
     * @param <T>   The type of elements to be sorted, must be Comparable.
     * @param array The array to be sorted.
     * @return The sorted array.
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        List<T> unsorted = new ArrayList<>(Arrays.asList(array));
        List<T> sorted = strandSort(unsorted);
        return sorted.toArray(array);
    }

    /**
     * Strand Sort algorithm that sorts a list.
     *
     * @param <T>  The type of elements to be sorted, must be Comparable.
     * @param list The list to be sorted.
     * @return The sorted list.
     */
    private static <T extends Comparable<? super T>> List<T> strandSort(List<T> list) {
        if (list.size() <= 1) {
            return list;
        }

        List<T> result = new ArrayList<>();

        while (!list.isEmpty()) {
            List<T> strand = extractStrand(list);
            result = merge(result, strand);
        }

        return result;
    }

    /**
     * Extracts an increasing subsequence (strand) from the given list.
     *
     * @param <T>  The type of elements to be sorted, must be Comparable.
     * @param list The list from which to extract the strand.
     * @return An increasing subsequence (strand) removed from the original list.
     */
    private static <T extends Comparable<? super T>> List<T> extractStrand(List<T> list) {
        List<T> strand = new ArrayList<>();
        strand.add(list.remove(0));

        int index = 0;
        while (index < list.size()) {
            T lastInStrand = strand.get(strand.size() - 1);
            T current = list.get(index);

            if (lastInStrand.compareTo(current) <= 0) {
                strand.add(list.remove(index));
            } else {
                index++;
            }
        }

        return strand;
    }

    /**
     * Merges two sorted lists into one sorted list.
     *
     * @param <T>   The type of elements to be sorted, must be Comparable.
     * @param left  The first sorted list.
     * @param right The second sorted list.
     * @return The merged sorted list.
     */
    private static <T extends Comparable<? super T>> List<T> merge(List<T> left, List<T> right) {
        List<T> merged = new ArrayList<>(left.size() + right.size());
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            T leftElement = left.get(leftIndex);
            T rightElement = right.get(rightIndex);

            if (leftElement.compareTo(rightElement) <= 0) {
                merged.add(leftElement);
                leftIndex++;
            } else {
                merged.add(rightElement);
                rightIndex++;
            }
        }

        if (leftIndex < left.size()) {
            merged.addAll(left.subList(leftIndex, left.size()));
        }
        if (rightIndex < right.size()) {
            merged.addAll(right.subList(rightIndex, right.size()));
        }

        return merged;
    }
}