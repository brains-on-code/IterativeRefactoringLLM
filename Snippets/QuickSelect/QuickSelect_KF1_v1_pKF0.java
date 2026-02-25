package com.thealgorithms.searches;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Utility class providing selection algorithms.
 */
public final class Class1 {

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
     * @throws NullPointerException     if {@code elements} is null
     * @throws IllegalArgumentException if {@code elements} is empty
     * @throws IndexOutOfBoundsException if {@code index} is negative or not less than list size
     */
    public static <T extends Comparable<T>> T method1(List<T> elements, int index) {
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

    private static <T extends Comparable<T>> int selectIndex(List<T> elements, int index) {
        return selectIndex(elements, 0, elements.size() - 1, index);
    }

    private static <T extends Comparable<T>> int selectIndex(
            List<T> elements,
            int left,
            int right,
            int index
    ) {
        while (true) {
            if (left == right) {
                return left;
            }

            int pivotIndex = choosePivotIndex(elements, left, right);
            pivotIndex = partitionAroundPivot(elements, left, right, pivotIndex, index);

            if (index == pivotIndex) {
                return index;
            } else if (index < pivotIndex) {
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
            int index
    ) {
        T pivotValue = elements.get(pivotIndex);
        Collections.swap(elements, pivotIndex, right);

        int lessThanPivotEnd = left;

        for (int i = left; i < right; i++) {
            if (elements.get(i).compareTo(pivotValue) < 0) {
                Collections.swap(elements, lessThanPivotEnd, i);
                lessThanPivotEnd++;
            }
        }

        int equalToPivotEnd = lessThanPivotEnd;

        for (int i = lessThanPivotEnd; i < right; i++) {
            if (elements.get(i).compareTo(pivotValue) == 0) {
                Collections.swap(elements, equalToPivotEnd, i);
                equalToPivotEnd++;
            }
        }

        Collections.swap(elements, right, equalToPivotEnd);

        return (index < lessThanPivotEnd) ? lessThanPivotEnd : Math.min(index, equalToPivotEnd);
    }

    private static <T extends Comparable<T>> int choosePivotIndex(
            List<T> elements,
            int left,
            int right
    ) {
        if (right - left < 5) {
            return medianOfSmallGroup(elements, left, right);
        }

        for (int groupStart = left; groupStart < right; groupStart += 5) {
            int groupEnd = groupStart + 4;
            if (groupEnd > right) {
                groupEnd = right;
            }

            int medianIndex = medianOfSmallGroup(elements, groupStart, groupEnd);
            int medianTargetIndex = left + (groupStart - left) / 5;
            Collections.swap(elements, medianIndex, medianTargetIndex);
        }

        int medianOfMediansIndex = (right - left) / 10 + left + 1;
        int mediansRight = left + (right - left) / 5;

        return selectIndex(elements, left, mediansRight, medianOfMediansIndex);
    }

    private static <T extends Comparable<T>> int medianOfSmallGroup(
            List<T> elements,
            int left,
            int right
    ) {
        List<T> subList = elements.subList(left, right);
        subList.sort(Comparator.naturalOrder());
        return (left + right) >>> 1;
    }
}