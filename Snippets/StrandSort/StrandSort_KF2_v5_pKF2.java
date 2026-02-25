package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Strand sort implementation.
 *
 * <p>Strand sort repeatedly extracts increasing subsequences (strands) from the
 * unsorted list and merges them into a growing sorted list.</p>
 */
public final class StrandSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        List<T> unsorted = new LinkedList<>(Arrays.asList(array));
        List<T> sorted = strandSort(unsorted);
        return sorted.toArray(array);
    }

    /**
     * Sorts the given list using strand sort.
     *
     * <p>Algorithm:
     * <ol>
     *   <li>While the input list is not empty, extract a non-decreasing strand.</li>
     *   <li>Merge that strand into the result list, which remains sorted.</li>
     * </ol>
     *
     * @param list the list to sort (modified and consumed)
     * @param <T>  element type, must be comparable
     * @return a new list containing the sorted elements
     */
    private static <T extends Comparable<? super T>> List<T> strandSort(List<T> list) {
        if (list.size() <= 1) {
            return list;
        }

        List<T> result = new ArrayList<>();

        while (!list.isEmpty()) {
            List<T> strand = buildStrand(list);
            result = merge(result, strand);
        }

        return result;
    }

    /**
     * Extracts a single non-decreasing strand from the given list.
     *
     * <p>The strand is built by:
     * <ol>
     *   <li>Starting with the first element of {@code source}.</li>
     *   <li>Scanning remaining elements from left to right.</li>
     *   <li>Whenever an element is greater than or equal to the last element
     *       in the strand, it is removed from {@code source} and appended to
     *       the strand.</li>
     * </ol>
     *
     * @param source the list from which to extract the strand (modified in place)
     * @param <T>    element type
     * @return a non-decreasing strand of elements
     */
    private static <T extends Comparable<? super T>> List<T> buildStrand(List<T> source) {
        List<T> strand = new ArrayList<>();
        strand.add(source.remove(0));

        int index = 0;
        while (index < source.size()) {
            T lastInStrand = strand.get(strand.size() - 1);
            T current = source.get(index);

            if (lastInStrand.compareTo(current) <= 0) {
                strand.add(source.remove(index));
            } else {
                index++;
            }
        }

        return strand;
    }

    /**
     * Merges two sorted lists into a new sorted list.
     *
     * @param left  first sorted list
     * @param right second sorted list
     * @param <T>   element type
     * @return a new list containing all elements from {@code left} and {@code right}, sorted
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