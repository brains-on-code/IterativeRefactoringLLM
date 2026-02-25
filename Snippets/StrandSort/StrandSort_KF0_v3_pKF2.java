package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Strand sort implementation using arrays.
 */
public final class StrandSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        List<T> unsorted = new ArrayList<>(Arrays.asList(array));
        List<T> sorted = strandSort(unsorted);
        return sorted.toArray(array);
    }

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

    private static <T extends Comparable<? super T>> List<T> buildStrand(List<T> list) {
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

    private static <T extends Comparable<? super T>> List<T> merge(List<T> left, List<T> right) {
        List<T> result = new ArrayList<>(left.size() + right.size());
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            T leftElement = left.get(leftIndex);
            T rightElement = right.get(rightIndex);

            if (leftElement.compareTo(rightElement) <= 0) {
                result.add(leftElement);
                leftIndex++;
            } else {
                result.add(rightElement);
                rightIndex++;
            }
        }

        if (leftIndex < left.size()) {
            result.addAll(left.subList(leftIndex, left.size()));
        }
        if (rightIndex < right.size()) {
            result.addAll(right.subList(rightIndex, right.size()));
        }

        return result;
    }
}