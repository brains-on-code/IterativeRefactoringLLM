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
        int leftBound,
        int rightBound,
        int targetIndex
    ) {
        int left = leftBound;
        int right = rightBound;

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
        int leftBound,
        int rightBound,
        int pivotIndex,
        int targetIndex
    ) {
        T pivotValue = elements.get(pivotIndex);
        Collections.swap(elements, pivotIndex, rightBound);

        int lessThanPivotEnd = leftBound;

        for (int currentIndex = leftBound; currentIndex < rightBound; currentIndex++) {
            if (elements.get(currentIndex).compareTo(pivotValue) < 0) {
                Collections.swap(elements, lessThanPivotEnd, currentIndex);
                lessThanPivotEnd++;
            }
        }

        int equalToPivotEnd = lessThanPivotEnd;

        for (int currentIndex = lessThanPivotEnd; currentIndex < rightBound; currentIndex++) {
            if (elements.get(currentIndex).compareTo(pivotValue) == 0) {
                Collections.swap(elements, equalToPivotEnd, currentIndex);
                equalToPivotEnd++;
            }
        }

        Collections.swap(elements, rightBound, equalToPivotEnd);

        if (targetIndex < lessThanPivotEnd) {
            return lessThanPivotEnd;
        }

        return Math.min(targetIndex, equalToPivotEnd);
    }

    private static <T extends Comparable<T>> int choosePivotIndex(
        List<T> elements,
        int leftBound,
        int rightBound
    ) {
        if (rightBound - leftBound < 5) {
            return medianOfFive(elements, leftBound, rightBound);
        }

        for (int groupStart = leftBound; groupStart < rightBound; groupStart += 5) {
            int groupEnd = groupStart + 4;
            if (groupEnd > rightBound) {
                groupEnd = rightBound;
            }

            int groupMedianIndex = medianOfFive(elements, groupStart, groupEnd);
            int medianPlacementIndex = leftBound + (groupStart - leftBound) / 5;
            Collections.swap(elements, groupMedianIndex, medianPlacementIndex);
        }

        int medianOfMediansIndex = (rightBound - leftBound) / 10 + leftBound + 1;
        int lastMedianIndex = leftBound + (rightBound - leftBound) / 5;

        return selectIndex(elements, leftBound, lastMedianIndex, medianOfMediansIndex);
    }

    private static <T extends Comparable<T>> int medianOfFive(
        List<T> elements,
        int leftBound,
        int rightBound
    ) {
        List<T> subList = elements.subList(leftBound, rightBound);
        subList.sort(Comparator.naturalOrder());
        return (leftBound + rightBound) >>> 1;
    }
}