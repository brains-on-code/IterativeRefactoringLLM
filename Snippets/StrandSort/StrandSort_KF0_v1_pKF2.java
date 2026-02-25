package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Strand sort implementation using arrays.
 */
public final class StrandSort implements SortAlgorithm {

    /**
     * Sorts the given array using the strand sort algorithm.
     *
     * @param <T>   element type, must be {@link Comparable}
     * @param array array to sort
     * @return sorted array (same instance as input)
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        List<T> unsorted = new ArrayList<>(Arrays.asList(array));
        List<T> sorted = strandSort(unsorted);
        return sorted.toArray(array);
    }

    /**
     * Performs strand sort on the given list.
     *
     * @param <T>  element type, must be {@link Comparable}
     * @param list list to sort (will be mutated)
     * @return new sorted list
     */
    private static <T extends Comparable<? super T>> List<T> strandSort(List<T> list) {
        if (list.size() <= 1) {
            return list;
        }

        List<T> result = new ArrayList<>();

        while (!list.isEmpty()) {
            // Build one increasing subsequence ("strand") from the remaining elements
            List<T> strand = new ArrayList<>();
            strand.add(list.removeFirst());

            for (int i = 0; i < list.size(); ) {
                T lastInStrand = strand.getLast();
                T current = list.get(i);

                if (lastInStrand.compareTo(current) <= 0) {
                    strand.add(list.remove(i));
                } else {
                    i++;
                }
            }

            // Merge the new strand into the accumulated result
            result = merge(result, strand);
        }

        return result;
    }

    /**
     * Merges two sorted lists into a single sorted list.
     *
     * @param <T>   element type, must be {@link Comparable}
     * @param left  first sorted list
     * @param right second sorted list
     * @return merged sorted list
     */
    private static <T extends Comparable<? super T>> List<T> merge(List<T> left, List<T> right) {
        List<T> result = new ArrayList<>(left.size() + right.size());
        int i = 0;
        int j = 0;

        // Merge while both lists have remaining elements
        while (i < left.size() && j < right.size()) {
            T leftElem = left.get(i);
            T rightElem = right.get(j);

            if (leftElem.compareTo(rightElem) <= 0) {
                result.add(leftElem);
                i++;
            } else {
                result.add(rightElem);
                j++;
            }
        }

        // Append any remaining elements
        if (i < left.size()) {
            result.addAll(left.subList(i, left.size()));
        }
        if (j < right.size()) {
            result.addAll(right.subList(j, right.size()));
        }

        return result;
    }
}