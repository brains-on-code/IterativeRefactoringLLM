package com.thealgorithms.searches;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Utility class providing selection algorithms.
 */
public final class Class1 {

    private static final String NULL_LIST_MESSAGE = "The list of elements must not be null.";
    private static final String EMPTY_LIST_MESSAGE = "The list of elements must not be empty.";
    private static final String NEGATIVE_INDEX_MESSAGE = "The index must not be negative.";
    private static final String INDEX_OUT_OF_BOUNDS_MESSAGE =
            "The index must be less than the number of elements.";

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the element at the given index in the list as if the list were sorted,
     * without fully sorting the list (order statistic selection).
     *
     * @param elements the list of elements
     * @param index    the zero-based index of the desired element in sorted order
     * @param <T>      the element type, which must be comparable
     * @return the element that would appear at position {@code index} in the sorted list
     * @throws NullPointerException      if {@code elements} is null
     * @throws IllegalArgumentException  if {@code elements} is empty
     * @throws IndexOutOfBoundsException if {@code index} is negative or not less than list size
     */
    public static <T extends Comparable<T>> T method1(List<T> elements, int index) {
        validateInput(elements, index);
        int selectedIndex = selectIndex(elements, index);
        return elements.get(selectedIndex);
    }

    private static <T extends Comparable<T>> void validateInput(List<T> elements, int index) {
        Objects.requireNonNull(elements, NULL_LIST_MESSAGE);

        if (elements.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_LIST_MESSAGE);
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException(NEGATIVE_INDEX_MESSAGE);
        }

        if (index >= elements.size()) {
            throw new IndexOutOfBoundsException(INDEX_OUT_OF_BOUNDS_MESSAGE);
        }
    }

    private static <T extends Comparable<T>> int selectIndex(List<T> elements, int index) {
        return selectIndex(elements, 0, elements.size() - 1, index);
    }

    private static <T extends Comparable<T>> int selectIndex(
            List<T> elements,
            int left,
            int right,
            int index
    ) {
        int low = left;
        int high = right;

        while (true) {
            if (low == high) {
                return low;
            }

            int pivotIndex = choosePivotIndex(elements, low, high);
            pivotIndex = partitionAroundPivot(elements, low, high, pivotIndex, index);

            if (index == pivotIndex) {
                return index;
            }

            if (index < pivotIndex) {
                high = pivotIndex - 1;
            } else {
                low = pivotIndex + 1;
            }
        }
    }

    private static <T extends Comparable<T>> int partitionAroundPivot(
            List<T> elements,
            int left,
            int right,
            int pivotIndex,
            int index
    ) {
        T pivotValue = elements.get(pivotIndex);
        Collections.swap(elements, pivotIndex, right);

        int lessThanPivotEnd = partitionLessThanPivot(elements, left, right, pivotValue);
        int equalToPivotEnd = partitionEqualToPivot(elements, lessThanPivotEnd, right, pivotValue);

        Collections.swap(elements, right, equalToPivotEnd);

        return (index < lessThanPivotEnd) ? lessThanPivotEnd : Math.min(index, equalToPivotEnd);
    }

    private static <T extends Comparable<T>> int partitionLessThanPivot(
            List<T> elements,
            int left,
            int right,
            T pivotValue
    ) {
        int lessThanPivotEnd = left;

        for (int i = left; i < right; i++) {
            if (elements.get(i).compareTo(pivotValue) < 0) {
                Collections.swap(elements, lessThanPivotEnd, i);
                lessThanPivotEnd++;
            }
        }

        return lessThanPivotEnd;
    }

    private static <T extends Comparable<T>> int partitionEqualToPivot(
            List<T> elements,
            int start,
            int right,
            T pivotValue
    ) {
        int equalToPivotEnd = start;

        for (int i = start; i < right; i++) {
            if (elements.get(i).compareTo(pivotValue) == 0) {
                Collections.swap(elements, equalToPivotEnd, i);
                equalToPivotEnd++;
            }
        }

        return equalToPivotEnd;
    }

    private static <T extends Comparable<T>> int choosePivotIndex(
            List<T> elements,
            int left,
            int right
    ) {
        if (right - left < 5) {
            return medianOfSmallGroup(elements, left, right);
        }

        int medianTarget = left;
        for (int groupStart = left; groupStart <= right; groupStart += 5) {
            int groupEnd = Math.min(groupStart + 4, right);
            int medianIndex = medianOfSmallGroup(elements, groupStart, groupEnd);
            Collections.swap(elements, medianIndex, medianTarget);
            medianTarget++;
        }

        int mediansRight = medianTarget - 1;
        int medianOfMediansIndex = (mediansRight - left) / 2 + left;

        return selectIndex(elements, left, mediansRight, medianOfMediansIndex);
    }

    private static <T extends Comparable<T>> int medianOfSmallGroup(
            List<T> elements,
            int left,
            int right
    ) {
        List<T> subList = elements.subList(left, right + 1);
        subList.sort(Comparator.naturalOrder());
        return (left + right) >>> 1;
    }
}