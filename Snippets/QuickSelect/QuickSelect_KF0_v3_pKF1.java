package com.thealgorithms.searches;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * An implementation of the Quickselect algorithm as described
 * <a href="https://en.wikipedia.org/wiki/Median_of_medians">here</a>.
 */
public final class QuickSelect {

    private QuickSelect() {
    }

    /**
     * Selects the {@code n}-th smallest element of {@code elements}, i.e. the element that would
     * be at index n if the list was sorted.
     * <p>
     * Calling this function might change the order of elements in {@code elements}.
     *
     * @param elements the list of elements
     * @param index    the index (0-based) of the desired element in sorted order
     * @param <T>      the type of list elements
     * @return the n-th smallest element in the list
     * @throws IndexOutOfBoundsException if index is less than 0 or greater or equal to
     *                                   the number of elements in the list
     * @throws IllegalArgumentException  if the list is empty
     * @throws NullPointerException      if {@code elements} is null
     */
    public static <T extends Comparable<T>> T select(List<T> elements, int index) {
        Objects.requireNonNull(elements, "The list of elements must not be null.");

        if (elements.isEmpty()) {
            throw new IllegalArgumentException("The list of elements must not be empty.");
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("The index must not be negative.");
        }

        if (index >= elements.size()) {
            throw new IndexOutOfBoundsException("The index must be less than the number of elements.");
        }

        int selectedIndex = selectIndex(elements, index);
        return elements.get(selectedIndex);
    }

    private static <T extends Comparable<T>> int selectIndex(List<T> elements, int targetIndex) {
        return selectIndex(elements, 0, elements.size() - 1, targetIndex);
    }

    private static <T extends Comparable<T>> int selectIndex(
        List<T> elements,
        int left,
        int right,
        int targetIndex
    ) {
        while (true) {
            if (left == right) {
                return left;
            }

            int pivotIndex = choosePivotIndex(elements, left, right);
            pivotIndex = partitionAroundPivot(elements, left, right, pivotIndex, targetIndex);

            if (targetIndex == pivotIndex) {
                return targetIndex;
            } else if (targetIndex < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    private static <T extends Comparable<T>> int partitionAroundPivot(
        List<T> elements,
        int left,
        int right,
        int pivotIndex,
        int targetIndex
    ) {
        T pivotValue = elements.get(pivotIndex);
        Collections.swap(elements, pivotIndex, right);

        int lessThanPivotBoundary = left;

        for (int current = left; current < right; current++) {
            if (elements.get(current).compareTo(pivotValue) < 0) {
                Collections.swap(elements, lessThanPivotBoundary, current);
                lessThanPivotBoundary++;
            }
        }

        int equalToPivotBoundary = lessThanPivotBoundary;

        for (int current = lessThanPivotBoundary; current < right; current++) {
            if (elements.get(current).compareTo(pivotValue) == 0) {
                Collections.swap(elements, equalToPivotBoundary, current);
                equalToPivotBoundary++;
            }
        }

        Collections.swap(elements, right, equalToPivotBoundary);

        if (targetIndex < lessThanPivotBoundary) {
            return lessThanPivotBoundary;
        }

        return Math.min(targetIndex, equalToPivotBoundary);
    }

    private static <T extends Comparable<T>> int choosePivotIndex(
        List<T> elements,
        int left,
        int right
    ) {
        if (right - left < 5) {
            return medianOfFive(elements, left, right);
        }

        for (int groupStart = left; groupStart < right; groupStart += 5) {
            int groupEnd = groupStart + 4;
            if (groupEnd > right) {
                groupEnd = right;
            }

            int groupMedianIndex = medianOfFive(elements, groupStart, groupEnd);
            int medianPlacementIndex = left + (groupStart - left) / 5;
            Collections.swap(elements, groupMedianIndex, medianPlacementIndex);
        }

        int medianOfMediansIndex = (right - left) / 10 + left + 1;
        int lastMedianIndex = left + (right - left) / 5;

        return selectIndex(elements, left, lastMedianIndex, medianOfMediansIndex);
    }

    private static <T extends Comparable<T>> int medianOfFive(
        List<T> elements,
        int left,
        int right
    ) {
        List<T> subList = elements.subList(left, right);
        subList.sort(Comparator.naturalOrder());
        return (left + right) >>> 1;
    }
}