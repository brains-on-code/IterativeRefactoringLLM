package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Strand sort implementation using arrays.
 *
 * <p>Strand sort repeatedly extracts increasing subsequences ("strands") from
 * the unsorted list and merges them into a growing sorted list.</p>
 */
public final class StrandSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        List<T> unsorted = new ArrayList<>(Arrays.asList(array));
        List<T> sorted = strandSort(unsorted);
        return sorted.toArray(array);
    }

    /**
     * Sorts the given list using strand sort and returns a new sorted list.
     */
    private static <T extends Comparable<? super T>> List<T> strandSort(List<T> list) {
        if (list.size() <= 1) {
            return list;
        }

        List<T> sorted = new ArrayList<>();

        while (!list.isEmpty()) {
            List<T> strand = extractStrand(list);
            sorted = merge(sorted, strand);
        }

        return sorted;
    }

    /**
     * Extracts a single increasing strand from the front of the list.
     * Elements that belong to the strand are removed from the original list.
     */
    private static <T extends Comparable<? super T>> List<T> extractStrand(List<T> list) {
        List<T> strand = new ArrayList<>();
        strand.add(list.removeFirst());

        int index = 0;
        while (index < list.size()) {
            T lastInStrand = strand.getLast();
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
     * Merges two sorted lists into a new sorted list.
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